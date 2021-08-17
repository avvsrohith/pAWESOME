package com.example.pawsome;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityOptionsCompat;
import androidx.core.util.Pair;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.pawsome.Database.Fav_Entity;
import com.example.pawsome.Database.Fav_ViewModel;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Favourites extends AppCompatActivity implements Fav_Adapter.OnItemClickListener{
    RecyclerView recyclerView;
    List<Fav_Entity> FavList;
    LinearLayoutManager manager;
    Fav_Adapter adapter;
    public static Fav_ViewModel fav_viewModel;
    public static Fav_Entity ClickedFav;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.Theme_SplashScreen);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favourites);

        TextView msg_tv=findViewById(R.id.msg_tv);

        fav_viewModel = ViewModelProviders.of(this).get(Fav_ViewModel.class);
        fav_viewModel.getAllFavs().observe(this, new Observer<List<Fav_Entity>>() {
            @Override
            public void onChanged(List<Fav_Entity> fav_entities) {
                FavList=fav_entities;
                msg_tv.setVisibility(fav_entities.isEmpty() ? View.VISIBLE: View.GONE);
                PutInRecyclerview(fav_entities);
            }
        });



        recyclerView=findViewById(R.id.fav_recyclerview);
        BottomNavigationView bottomNavigationView=findViewById(R.id.bottomtabbar);
        bottomNavigationView.setSelectedItemId(R.id.favourites);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()){
                    case R.id.upload:
                        startActivity(new Intent(getApplicationContext(),Upload.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.favourites:
                        return true;
                    case R.id.home:
                        startActivity(new Intent(getApplicationContext(),MainActivity.class));
                        overridePendingTransition(0,0);
                        return true;
                }
                return false;
            }
        });

        manager=new LinearLayoutManager(this);

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                fav_viewModel.deleteFromFavs(adapter.getFavAt(viewHolder.getAdapterPosition()));
                Toast.makeText(Favourites.this,"Removed from favourites", Toast.LENGTH_SHORT).show();
            }
        }).attachToRecyclerView(recyclerView);


    }





    private void PutInRecyclerview(List<Fav_Entity> favList) {
        adapter=new Fav_Adapter(this,favList);
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(adapter);
        adapter.setOnItemClickListener(Favourites.this);
    }


    @Override
    public void onItemCLick(int position) {
        Intent intent=new Intent(this,Fav_Information.class);
        ClickedFav=FavList.get(position);
        RecyclerView.ViewHolder rv_view = recyclerView.findViewHolderForAdapterPosition(position);
        ImageView dogimage = rv_view.itemView.findViewById(R.id.dog_image);
        TextView name=rv_view.itemView.findViewById(R.id.tv_name);
        Pair<View,String> p1=Pair.create((View)dogimage,"Pic_tsn");
        Pair<View,String> p2=Pair.create((View)name,"name_tsn");
        ActivityOptionsCompat optionsCompat=ActivityOptionsCompat.makeSceneTransitionAnimation(this,p1,p2);
        getWindow().setExitTransition(null);
        startActivity(intent,optionsCompat.toBundle());

    }

    @Override
    public void onShareCLick(int position) {
        shareimage(FavList.get(position).getUrl());
    }

    @Override
    public void onRemoveClick(int position) {
        fav_viewModel.deleteFromFavs(FavList.get(position));
    }

    public void shareimage(String url) {
        Picasso.get().load(url).into(new Target() {
            @Override public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                Intent i = new Intent(Intent.ACTION_SEND);
                i.setType("image/*");
                i.putExtra(Intent.EXTRA_STREAM, getLocalBitmapUri(bitmap));
                startActivity(Intent.createChooser(i, "Share Image"));
            }

            @Override
            public void onBitmapFailed(Exception e, Drawable errorDrawable) {

            }
            @Override public void onPrepareLoad(Drawable placeHolderDrawable) { }
        });
    }

    public Uri getLocalBitmapUri(Bitmap bmp) {
        Uri bmpUri = null;
        try {
            File file =  new File(getExternalFilesDir(Environment.DIRECTORY_PICTURES), "share_image_" + System.currentTimeMillis() + ".jpg");
            FileOutputStream out = new FileOutputStream(file);
            bmp.compress(Bitmap.CompressFormat.PNG, 90, out);
            out.close();
            bmpUri = Uri.fromFile(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bmpUri;
    }

}