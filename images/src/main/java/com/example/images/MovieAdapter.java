package com.example.images;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MovieAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    @NonNull

    private static String path;
    private final Context mContext;
    private final ArrayList<Bitmap> fileList;
    private final MovieAdapter.OnItemClickListener mListener;

    public MovieAdapter(@NonNull String path, Context mContext, ArrayList<Bitmap> fileList, MovieAdapter.OnItemClickListener mListener) {
        this.path = path;
        this.mContext = mContext;
        this.fileList = fileList;
        this.mListener = mListener;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MovieAdapter.LinearViewHolder(LayoutInflater.from(mContext).inflate(R.layout.movie_once,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        try {
//            ((ImageAdapter.LinearViewHolder)holder).imageView.setBackground(Background.zoomDrawable(Drawable.createFromPath(fileList.get(position).getFile().getPath()),2500,4000));
            ArrayList<Bitmap> m = GetFiles.getMovie(path);
            Bitmap b = MovieAdapter.changeSize(m.size() - 1 - position);

            ((MovieAdapter.LinearViewHolder)holder).imageView.setImageBitmap(b);

            ((LinearViewHolder)holder).ready.post(new Runnable() {
                @Override
                public void run() {
                    int w = ((LinearViewHolder)holder).ready.getWidth();
                    int h = ((LinearViewHolder)holder).ready.getHeight();
                    LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(w, h);
                    int top = - (b.getHeight() / 2 + w);
                    int left = b.getWidth() + 10;
                    lp.setMargins(left, top, 0, 0);
                    ((LinearViewHolder)holder).ready.setLayoutParams(lp);
                }
            });
        }catch (Exception e){
            Log.i("ERROR!!!!",".....");
            e.printStackTrace();
        }

        holder.itemView.setOnClickListener((View.OnClickListener) v->{
            mListener.onClick(position);
        });
    }

    @Override
    public int getItemCount() {
        return fileList.size();
    }

    @Override
    public int getItemViewType(int position){
        if (position % 2 == 0 ){
            return 0;
        }else{
            return 1;
        }
    }

    static class LinearViewHolder extends RecyclerView.ViewHolder{

        private final ImageView imageView;
        private ImageView ready;

        public LinearViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.movie_once);
            ready = itemView.findViewById(R.id.ready);
        }
    }
    public interface OnItemClickListener{
        void onClick(int pos);
    }

    private static Bitmap changeSize(int pos){
        ArrayList<Bitmap> bitmaps = GetFiles.getMovie(path);
        Bitmap bitmap = bitmaps.get(pos);

        int width = bitmap.getWidth();
        int height = bitmap.getHeight();

        //设置想要的大小
        int newWidth = 512;
        int newHeight = height * (newWidth / width);

        //计算压缩的比率
        float scaleWidth = ((float) newWidth) / width;
        float scaleHeight = ((float) newHeight) / height;

        //获取想要缩放的matrix
        Matrix matrix = new Matrix();
        matrix.postScale(scaleWidth,scaleHeight);

        //获取新的bitmap
        bitmap=Bitmap.createBitmap(bitmap,0,0,width,height,matrix,true);

        return bitmap;
    }
}
