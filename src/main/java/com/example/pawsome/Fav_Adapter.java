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
import com.example.pawsome.Database.Fav_Entity;
import com.squareup.picasso.Picasso;

import java.util.List;

public class Fav_Adapter extends RecyclerView.Adapter<Fav_Adapter.ViewHolder> {
    LayoutInflater inflater;
    private List<Fav_Entity> FavList;
    private OnItemClickListener Listener;

    public interface OnItemClickListener {
        void onItemCLick(int position);
        void onShareCLick(int position);
        void onRemoveClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener){
        Listener=listener;
    }

    public Fav_Adapter(Context context,List<Fav_Entity> favlist){
        this.inflater=LayoutInflater.from(context);
        this.FavList =favlist;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= inflater.inflate(R.layout.card,parent,false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Fav_Entity fav_entity=FavList.get(position);

        String imageurl=fav_entity.getUrl();
        String Dogname= fav_entity.getName();

        holder.DogName.setText(Dogname);
        Picasso.get().load(imageurl).into(holder.Image);
    }

    @Override
    public int getItemCount() {
        return FavList.size();
    }


    public Fav_Entity getFavAt(int position){
        return FavList.get(position);
    }

    public class ViewHolder extends RecyclerView.ViewHolder  {
        public ImageView Image;
        public TextView DogName;
        public ViewHolder(View itemView){
            super(itemView);
            Image =itemView.findViewById(R.id.dog_image);
            DogName =itemView.findViewById(R.id.tv_name);
            LottieAnimationView remove=itemView.findViewById(R.id.like);
            ImageButton sharebtn=itemView.findViewById(R.id.share_btn);
            remove.setProgress(1f);


            remove.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if(Listener!=null){
                        int position=getAdapterPosition();
                        if(position!=RecyclerView.NO_POSITION){
                            Listener.onRemoveClick(position);
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
