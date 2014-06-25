package com.basictwitter.apps.basictwitter.activities;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.ImageView;

import com.codepath.apps.basictwitter.R;
import com.nostra13.universalimageloader.core.ImageLoader;

public class ImageDisplayActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_display);
        String imageUrl = (String) getIntent().getStringExtra("url");
        ImageView ivImage = (ImageView) findViewById(R.id.ivFullImage);
        if (imageUrl == null) {
            throw new AssertionError("url passed into ImageDisplayActivity cannot be null");
        } else {
            ivImage.setImageResource(Color.TRANSPARENT);
            ImageLoader imageLoader = ImageLoader.getInstance();
            imageLoader.displayImage(imageUrl + ":large", ivImage);
        }
    }
}
