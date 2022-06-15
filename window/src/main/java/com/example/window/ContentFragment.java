package com.example.window;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class ContentFragment extends Fragment {
    private TextView content;
    private String string;
    private int color;

    public ContentFragment(String s, int color) {
        this.string = s;
        this.color = color;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment, container, false);
        view.setBackgroundColor(getResources().getColor(color));
        content = view.findViewById(R.id.content);
        content.setText(string);
        return view;
    }
}