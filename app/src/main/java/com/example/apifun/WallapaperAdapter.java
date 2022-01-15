package com.example.apifun;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class WallapaperAdapter extends RecyclerView.Adapter<WallapaperAdapter.WallpaperViewHolder> {

    private Context context;
    private ArrayList<ArrayList<Wallpaper>> wallpapers;
    private OnItemClickListener onItemClickListener;

    public interface OnItemClickListener{
        void onItemClick1(int position);
        void onItemClick2(int position);
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public WallapaperAdapter(Context context, ArrayList<ArrayList<Wallpaper>> wallpapers){
        this.context = context;
        this.wallpapers = wallpapers;
    }

    @NonNull
    @Override
    public WallpaperViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.wallpaper_set_item,parent,false);
        return new WallpaperViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull WallpaperViewHolder holder, int position) {
        ArrayList<Wallpaper> wallpaper = wallpapers.get(position);
        Wallpaper wallpaper1 = wallpaper.get(0);
        Wallpaper wallpaper2 = wallpaper.get(1);
        Log.i("aunu", "onBindViewHolder: "+wallpaper1.getFullWallpaper_url());
        Log.i("aunu", "onBindViewHolder: "+wallpaper2.getFullWallpaper_url());
        Picasso.get().load(wallpaper1.getFullWallpaper_url()).into(holder.imageView1);
        Picasso.get().load(wallpaper2.getFullWallpaper_url()).into(holder.imageView2);
    }

    @Override
    public int getItemCount() {
        return wallpapers.size();
    }

    public class WallpaperViewHolder extends RecyclerView.ViewHolder{
        private ImageView imageView1, imageView2;
        public WallpaperViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView1 = itemView.findViewById(R.id.image);
            imageView2 = itemView.findViewById(R.id.image2);

            imageView1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(onItemClickListener!=null){
                        int position = getAdapterPosition();
                        if(position!=RecyclerView.NO_POSITION){
                            onItemClickListener.onItemClick1(position);
                        }
                    }
                }
            });

            imageView2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(onItemClickListener!=null){
                        int position = getAdapterPosition();
                        if(position!=RecyclerView.NO_POSITION){
                            onItemClickListener.onItemClick2(position);
                        }
                    }
                }
            });

        }
    }
}
