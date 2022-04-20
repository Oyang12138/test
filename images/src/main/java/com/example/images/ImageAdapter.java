package com.example.images;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ImageAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    @NonNull

    private String path;
    private final Context mContext;
    private final ArrayList<Beans> fileList;
    private final ImageAdapter.OnItemClickListener mListener;

    public ImageAdapter(@NonNull String path, Context mContext, ArrayList<Beans> fileList, ImageAdapter.OnItemClickListener mListener) {
        this.path = path;
        this.mContext = mContext;
        this.fileList = fileList;
        this.mListener = mListener;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ImageAdapter.LinearViewHolder(LayoutInflater.from(mContext).inflate(R.layout.image_once,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        try {
//            ((ImageAdapter.LinearViewHolder)holder).imageView.setBackground(Background.zoomDrawable(Drawable.createFromPath(fileList.get(position).getFile().getPath()),2500,4000));
            Log.i("sizeTag","s:" + fileList.size());
            Log.i("movieT","t:" + path);
            if(fileList.size() != 0){
                Log.i("sizeTag","s:" + fileList.size());
                ((ImageAdapter.LinearViewHolder)holder).imageView.setImageBitmap(BitmapFactory.decodeFile(fileList.get(fileList.size() - 1 - position).getFile().getPath()));
            }else{
                Log.i("sizeTag","s:" + fileList.size());
                Log.i("movieTag","t:" + path);
                ((ImageAdapter.LinearViewHolder)holder).imageView.setImageBitmap(GetFiles.getMovie(path).get(GetFiles.getMovie(path).size() - 1 - position));
            }

        }catch (Exception e){
            e.printStackTrace();
        }

        holder.itemView.setOnClickListener((View.OnClickListener) v->{
            mListener.onClick(position);
        });
    }

    @Override
    public int getItemViewType(int position){
        if (position % 2 == 0 ){
            return 0;
        }else{
            return 1;
        }
    }

    @Override
    public int getItemCount() {
        return fileList.size();
    }

    static class LinearViewHolder extends RecyclerView.ViewHolder{

        private final ImageView imageView;

        public LinearViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.image_once);
        }
    }
    public interface OnItemClickListener{
        void onClick(int pos);
    }
}
