package com.example.window;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class HorizontalAdapter extends RecyclerView.Adapter<HorizontalAdapter.HorizontalHolder> {
    private List<Integer> backgrounds;
    private Context context;

    public HorizontalAdapter(Context context) {
        this.context = context;
        if (backgrounds == null) {
            backgrounds = new ArrayList<>();
            backgrounds.add(android.R.color.holo_blue_bright);
            backgrounds.add(android.R.color.holo_red_dark);
            backgrounds.add(android.R.color.holo_green_dark);
            backgrounds.add(android.R.color.holo_orange_light);
            backgrounds.add(android.R.color.holo_purple);
        }
    }

    @NonNull
    @Override
    public HorizontalHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new HorizontalHolder(LayoutInflater.from(context).inflate(R.layout.horizontal_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull HorizontalHolder holder, int position) {
        holder.textView.setText((position + 1) + "/" + backgrounds.size());
        holder.textView.setBackgroundResource(backgrounds.get(position));
    }

    @Override
    public int getItemCount() {
        if (backgrounds != null){
            return backgrounds.size();
        }
        return 0;
    }

    public class HorizontalHolder extends RecyclerView.ViewHolder {
        public LinearLayout linearLayout;
        public TextView textView;

        public HorizontalHolder(@NonNull View itemView) {
            super(itemView);
            linearLayout = itemView.findViewById(R.id.linear);
            textView = itemView.findViewById(R.id.text);
        }
    }
}
