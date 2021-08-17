package com.example.pawsome;

import static com.example.pawsome.Favourites.ClickedFav;
import static com.example.pawsome.MainActivity.ClickedCard;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
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

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Fav_Information extends AppCompatActivity {

    ImageView DogImage;
    TextView DogName,Lifespan,Temperament;
    Card fav;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fav_information);

        DogImage=findViewById(R.id.dogpic);
         DogName=findViewById(R.id.name);
        Lifespan=findViewById(R.id.lifespan);
        Temperament=findViewById(R.id.tv_temperament);
        ImageButton sharebtn=findViewById(R.id.share_btn);
        LottieAnimationView LikeBtn=findViewById(R.id.like);
        BottomNavigationView bottomNavigationView=findViewById(R.id.bottomtabbar);
        LikeBtn.setProgress(1f);

        Fav_ViewModel viewModel= ViewModelProviders.of(this).get(Fav_ViewModel.class);

        Picasso.get().load(ClickedFav.getUrl()).into(DogImage);
        DogName.setText(ClickedFav.getName());
        Lifespan.setText("Life Span: "+ClickedFav.getLife_span());
        Temperament.setText("Temperament: "+ClickedFav.getTemperament());





        sharebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                shareimage(ClickedFav.getUrl());
            }
        });

        LikeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LikeBtn.setSpeed(-1F);
                LikeBtn.playAnimation();
                viewModel.deleteFromFavs(ClickedFav);

            }
        });
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
            File file =  new File(getExternalFilesDir(Environment.DIRECTORY_PICTURES), "share_image_" + System.currentTimeMillis() + ".png");
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



