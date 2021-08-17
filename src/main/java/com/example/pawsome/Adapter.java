package com.example.pawsome;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.airbnb.lottie.LottieAnimationView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder> {
    LayoutInflater inflater;
    private ArrayList<Card> CardList;
    private OnItemClickListener Listener;


    public interface OnItemClickListener {
        void onItemCLick(int position);
        void onFavClick(int position);
        void onShareCLick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener){
        Listener=listener;
    }

    public Adapter(Context context,ArrayList<Card> cardlist){
        this.inflater=LayoutInflater.from(context);
        this.CardList =cardlist;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= inflater.inflate(R.layout.card,parent,false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Card currentcard= CardList.get(position);

        String imageurl=currentcard.getUrl();
        String Dogname= currentcard.getBreedInfos().get(0).getName();

        holder.DogName.setText(Dogname);
        Picasso.get().load(imageurl).into(holder.Image);
    }

    @Override
    public int getItemCount() {
        return CardList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder  {
        public ImageView Image;
        public TextView DogName;
        public ViewHolder(View itemView){
            super(itemView);
            Image =itemView.findViewById(R.id.dog_image);
            DogName =itemView.findViewById(R.id.tv_name);
            LottieAnimationView favbtn=itemView.findViewById(R.id.like);
            ImageButton sharebtn=itemView.findViewById(R.id.share_btn);
            final Boolean[] isAnimated = {false};



            favbtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (!isAnimated[0]){
                        favbtn.setSpeed(3f);
                        isAnimated[0] =true;
                        favbtn.playAnimation();
                    } else {
                        favbtn.setSpeed(-2F);
                        isAnimated[0] =false;
                        favbtn.playAnimation();
                    }

                    if(Listener!=null){
                        int position=getAdapterPosition();
                        if(position!=RecyclerView.NO_POSITION){
                            Listener.onFavClick(position);
                        }
                    }
                }
            });

            sharebtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(Listener!=null){
                        int position=getAdapterPosition();
                        if(position!=RecyclerView.NO_POSITION){
                            Listener.onShareCLick(position);
                        }
                    }
                }
            });


            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(Listener!=null){
                        int position=getAdapterPosition();
                        if(position!=RecyclerView.NO_POSITION){
                            Listener.onItemCLick(position);
                        }
                    }
                }
            });

        }




    }
}
