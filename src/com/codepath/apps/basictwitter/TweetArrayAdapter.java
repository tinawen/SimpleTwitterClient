package com.codepath.apps.basictwitter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.codepath.apps.basictwitter.models.Tweet;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;

/**
 * Created by tina on 6/19/14.
 */
public class TweetArrayAdapter extends ArrayAdapter<Tweet> {
        public TweetArrayAdapter(Context context, List<Tweet> tweets) {
            super(context, 0, tweets);
        }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Tweet tweet = getItem(position);

        View v;
        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(getContext());
            v = inflater.inflate(R.layout.tweet_item, parent, false);
        } else {
            v = convertView;
        }

        // find the views within template
        ImageView ivProfileImage = (ImageView) v.findViewById(R.id.ivProfileImge);
        TextView tvUsername = (TextView) v.findViewById(R.id.tvUserName);
        TextView tvBody = (TextView) v.findViewById(R.id.tvBody);
        ivProfileImage.setImageResource(Color.TRANSPARENT);
        ImageLoader imageLoader = ImageLoader.getInstance();
        imageLoader.displayImage(tweet.getUser().getProfileImageUrl(), ivProfileImage);
        tvUsername.setText(tweet.getUser().getScreenName());
        tvBody.setText(tweet.getBody());
        return v;
    }
 }
