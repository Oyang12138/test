package com.example.myadapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class PlayAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    @NonNull

    private final Context mContext;
    private final OnItemClickListener mListener;

    public PlayAdapter(Context context, OnItemClickListener listener) {
        this.mContext = context;
        this.mListener = listener;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new LinearViewHolder(LayoutInflater.from(mContext).inflate(R.layout.course_item,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ((LinearViewHolder)holder).textView.setText("对话翻译，畅想沟通");
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
        return 10;
    }

    class LinearViewHolder extends RecyclerView.ViewHolder{

        private final TextView textView;

        public LinearViewHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.title);
        }
    }

    public interface OnItemClickListener{
        void onClick(int pos);
    }
}
