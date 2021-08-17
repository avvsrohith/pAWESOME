package com.example.pawsome;

import static com.example.pawsome.MainActivity.ClickedCard;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

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

public class Information extends AppCompatActivity {

    ImageView DogImage;
    Fav_ViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_information);

        DogImage=findViewById(R.id.dogpic);
        TextView DogName=findViewById(R.id.name);
        TextView Lifespan=findViewById(R.id.lifespan);
        TextView Temperament=findViewById(R.id.tv_temperament);
        ImageButton sharebtn=findViewById(R.id.share);
        Button favBtn=findViewById(R.id.favbtn);
        BottomNavigationView bottomNavigationView=findViewById(R.id.bottomtabbar);
        Fav_ViewModel viewModel= ViewModelProviders.of(this).get(Fav_ViewModel.class);




        Picasso.get().load(ClickedCard.getUrl()).into(DogImage);
        DogName.setText(ClickedCard.getBreedInfos().get(0).getName());
        Lifespan.setText("Life Span: "+ClickedCard.getBreedInfos().get(0).getLife_span());
        Temperament.setText("Temperament: "+ClickedCard.getBreedInfos().get(0).getTemperament());


        sharebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                shareimage(ClickedCard.getUrl());
            }
        });

        favBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BreedInfo breedInfo=ClickedCard.getBreedInfos().get(0);
                Fav_Entity fav_entity=new Fav_Entity(ClickedCard.getUrl(),ClickedCard.getImageId(),breedInfo.getName(),breedInfo.getLife_span(),breedInfo.getTemperament(),breedInfo.getBreedId());
                viewModel.addToFavs(fav_entity);
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