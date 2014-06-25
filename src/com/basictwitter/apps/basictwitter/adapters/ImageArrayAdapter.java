package com.basictwitter.apps.basictwitter.adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import com.codepath.apps.basictwitter.R;
import com.basictwitter.apps.basictwitter.activities.ImageDisplayActivity;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;

/**
 * Created by tina on 6/21/14.
 */
public class ImageArrayAdapter extends ArrayAdapter<String> {
    // View lookup cache
    private static class ViewHolder {
        String url;
        ImageView imageView;
    }

    public ImageArrayAdapter(Context context, List<String> imageUrls) {
        super(context, R.layout.image_item, imageUrls);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final String newUrl = this.getItem(position);
        ViewHolder viewHolder;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.image_item, parent, false);
            convertView.setTag(viewHolder);
            viewHolder.url = newUrl;
            viewHolder.imageView = (ImageView) convertView.findViewById(R.id.image);
            viewHolder.imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(getContext(), ImageDisplayActivity.class);
                    intent.putExtra("url", newUrl);
                    getContext().startActivity(intent);
                }
            });
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
            // only erase image bitmap if we want to display a different image
            if (!viewHolder.url.equals(newUrl)) {
                viewHolder.imageView.setImageBitmap(null);
                viewHolder.url = newUrl;
            }
        }

        viewHolder.imageView.setImageResource(Color.TRANSPARENT);
        ImageLoader imageLoader = ImageLoader.getInstance();
        // load with small thumbnails. "thumb" type is too small
        imageLoader.displayImage(newUrl + ":small", viewHolder.imageView);

        return convertView;
    }
}

