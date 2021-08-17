package com.example.pawsome;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityOptionsCompat;
import androidx.core.util.Pair;
import androidx.core.widget.NestedScrollView;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import android.widget.ProgressBar;
import android.widget.SearchView;
import android.widget.TextView;

import com.airbnb.lottie.LottieAnimationView;
import com.example.pawsome.Database.Fav_Entity;
import com.example.pawsome.Database.Fav_ViewModel;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements Adapter.OnItemClickListener {

    RecyclerView recyclerView;
    ArrayList<Card> CardList;
    LinearLayoutManager manager;
    ProgressBar progressBar;
    NestedScrollView nestedScrollView;
    public static Card ClickedCard;
    public static Fav_ViewModel viewModel;
    private int page=1,limit=10;
    SwipeRefreshLayout swipeRefreshLayout;
    List<Fav_Entity> FavList;



    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.Theme_SplashScreen);
        super.onCreate(savedInstanceState);
        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());
        setTheme(R.style.Theme_SplashScreen);
        setContentView(R.layout.activity_main);
        recyclerView=findViewById(R.id.recycler_view);
        swipeRefreshLayout=findViewById(R.id.swipeRefresh);
        CardList=new ArrayList<>();
        progressBar=findViewById(R.id.progress_bar);
        nestedScrollView=findViewById(R.id.nestedScrollview);
        viewModel= ViewModelProviders.of(this).get(Fav_ViewModel.class);
        viewModel.getAllFavs().observe(this, new Observer<List<Fav_Entity>>() {
            @Override
            public void onChanged(List<Fav_Entity> fav_entities) {
                FavList=fav_entities;
            }
        });



        BottomNavigationView bottomNavigationView=findViewById(R.id.bottomtabbar);
        bottomNavigationView.setSelectedItemId(R.id.home);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()){
                    case R.id.upload:
                        startActivity(new Intent(getApplicationContext(),Upload.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.favourites:
                        startActivity(new Intent(getApplicationContext(),Favourites.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.home:
                        getData(1,10);
                        return true;
                }
                return false;
            }
        });

        manager=new LinearLayoutManager(this);
        if(CardList.isEmpty())
        getData(1,10);

        nestedScrollView.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
            @Override
            public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                if(scrollY==v.getChildAt(0).getMeasuredHeight()-v.getMeasuredHeight()){
                    page++;
                    progressBar.setVisibility(View.VISIBLE);
                    getData(page,limit);

                }
            }
        });

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                CardList.clear();
                getData(1,10);
                swipeRefreshLayout.setRefreshing(false);
            }
        });



    }

    private void getData(int page,int limit){
        API_utilities.getAPIinterface().getDogs(page,limit)
        .enqueue(new Callback<List<Card>>() {
            @Override
            public void onResponse(Call<List<Card>> call, Response<List<Card>> response) {
                if(response.body()!=null) {
                    List<Card> Dogs = response.body();

                    for (Card dog : Dogs) {
                        if (dog.getBreedInfos().isEmpty()) {
                            continue;
                        }
                        CardList.add(dog);
                    }
                }
                PutInRecyclerview(CardList);
                progressBar.setVisibility(View.GONE);



            }

            @Override
            public void onFailure(Call<List<Card>> call, Throwable t) {

            }
        });
    }



    private void searchData(String query) {
        API_utilities.getAPIinterface().searchdogs(query)
                .enqueue(new Callback<List<BreedInfo>>() {
                    @Override
                    public void onResponse(Call<List<BreedInfo>> call, Response<List<BreedInfo>> response) {
                        List<BreedInfo> breedInfos =response.body();
                        for(BreedInfo breedInfo : breedInfos){
                            getDataById(breedInfo.getBreedId(),10);
                        }
                    }

                    @Override
                    public void onFailure(Call<List<BreedInfo>> call, Throwable t) {

                    }
                });
    }

    private void getDataById(int breedid,int limit){
        API_utilities.getAPIinterface().getbyid(breedid,limit)
                .enqueue(new Callback<List<Card>>() {
                    @Override
                    public void onResponse(Call<List<Card>> call, Response<List<Card>> response) {
                        if(response.body()!=null) {
                            List<Card> Dogs = response.body();
                            for (Card dog : Dogs) {
                                if (dog.getBreedInfos().isEmpty()) {
                                    continue;
                                }
                                CardList.add(dog);
                            }
                        }
                        PutInRecyclerview(CardList);
                    }

                    @Override
                    public void onFailure(Call<List<Card>> call, Throwable t) {

                    }
                });
    }




        private void PutInRecyclerview(ArrayList<Card> cardList) {
        Adapter adapter=new Adapter(this,cardList);
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(adapter);
        adapter.setOnItemClickListener(MainActivity.this);
    }




    @Override
    public void onItemCLick(int position) {
        Intent intent=new Intent(this,Information.class);
        ClickedCard=CardList.get(position);
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
    public void onFavClick(int position) {
        Card clickedcard=CardList.get(position);
        BreedInfo breedInfo=clickedcard.getBreedInfos().get(0);
        Fav_Entity fav_entity=new Fav_Entity(clickedcard.getUrl(),clickedcard.getImageId(),breedInfo.getName(),breedInfo.getLife_span(),breedInfo.getTemperament(),breedInfo.getBreedId());
            viewModel.addToFavs(fav_entity);


    }

    @Override
    public void onShareCLick(int position) {
        shareimage(CardList.get(position).getUrl());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.search_menu,menu);
        MenuItem menuItem=menu.findItem(R.id.action_search);
        SearchView searchView=(SearchView) menuItem.getActionView();
        searchView.setQueryHint("Search by breed");

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                CardList.clear();
                searchData(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {

                return false;
            }
        });
        return super.onCreateOptionsMenu(menu);
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