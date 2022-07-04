package com.example.window;

import android.app.Activity;
import android.view.TextureView;

public class CameraHelper {
    private Activity activity;
    private TextureView textureView;

    public CameraHelper(Activity activity,TextureView textureView) {
        this.activity = activity;
        this.textureView = textureView;
    }
}
