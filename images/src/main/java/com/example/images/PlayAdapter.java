package com.example.images;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Matrix;
import android.graphics.PixelFormat;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class PlayAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    @NonNull

    private final Context mContext;
    private final ArrayList<Beans> fileList;
    private final String path;
    private final OnItemClickListener mListener;

    public PlayAdapter(String path,@NonNull Context context,ArrayList<Beans> fileList,OnItemClickListener listener) {
        this.path = path;
        this.mContext = context;
        this.fileList = fileList;
        this.mListener = listener;
    }
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new LinearViewHolder(LayoutInflater.from(mContext).inflate(R.layout.image_item,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ((LinearViewHolder)holder).textView.setText(fileList.get(position).getFileName());
        Log.i("tag2" , fileList.get(position).getFileName());
        Log.i("tag2" , "" + fileList.get(position).getFileCount());
        ((LinearViewHolder)holder).countView.setText(" " + fileList.get(position).getFileCount());
        Log.i("tag2" , "position:" + position + "; fileList.size():" + fileList.size());
        try {
//            ((LinearViewHolder)holder).imageView.setBackground(Background.zoomDrawable(Drawable.createFromPath(fileList.get(position).getFile().getPath()),90,90));
            if (fileList.get(position).getFile() != null){
                ((LinearViewHolder)holder).imageView.setImageBitmap(BitmapFactory.decodeFile(fileList.get(position).getFile().getPath()));
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    ((LinearViewHolder)holder).fore.setForeground(new Drawable() {
                        @Override
                        public void draw(@NonNull Canvas canvas) {

                        }

                        @Override
                        public void setAlpha(int i) {

                        }

                        @Override
                        public void setColorFilter(@Nullable ColorFilter colorFilter) {

                        }

                        @Override
                        public int getOpacity() {
                            return PixelFormat.TRANSPARENT;
                        }
                    });
                }
            }else{
                String path = fileList.get(position).getPath();
                Log.i("mPath","path:" + path);
                ArrayList<Bitmap> movie = GetFiles.getMovie(path);
                Log.i("movie","movie:" + movie);
                ((LinearViewHolder)holder).imageView.setImageBitmap(movie.get(movie.size() - 1));
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

        private final TextView textView;
        private final TextView countView;
        private final ImageView imageView;
        private ImageView fore;

        public LinearViewHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.title);
            countView = itemView.findViewById(R.id.count);
            imageView = itemView.findViewById(R.id.imageView);
            fore = itemView.findViewById(R.id.fore);
        }
    }
    public interface OnItemClickListener{
        void onClick(int pos);
    }


}

