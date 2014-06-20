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
    private static class ViewHolder {
        ImageView profileImage;
        TextView username;
        TextView screenName;
        TextView body;
        TextView timeStamp;
    }

    public TweetArrayAdapter(Context context, List<Tweet> tweets) {
        super(context, 0, tweets);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Tweet tweet = getItem(position);

        ViewHolder viewHolder;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.tweet_item, parent, false);
            viewHolder.profileImage = (ImageView) convertView.findViewById(R.id.ivProfileImge);
            viewHolder.username = (TextView) convertView.findViewById(R.id.tvUserName);
            viewHolder.screenName = (TextView) convertView.findViewById(R.id.tvScreenName);
            viewHolder.body = (TextView) convertView.findViewById(R.id.tvBody);
            viewHolder.timeStamp = (TextView) convertView.findViewById(R.id.tvTimeStamp);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.profileImage.setImageResource(Color.TRANSPARENT);
        ImageLoader imageLoader = ImageLoader.getInstance();
        imageLoader.displayImage(tweet.getUser().getProfileImageUrl(), viewHolder.profileImage);
        viewHolder.username.setText(tweet.getUser().getName());
        viewHolder.screenName.setText("@" + tweet.getUser().getScreenName());
        viewHolder.body.setText(tweet.getBody());
        viewHolder.timeStamp.setText(tweet.getCreatedAt());
        return convertView;
    }
 }
