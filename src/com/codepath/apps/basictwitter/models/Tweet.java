package com.codepath.apps.basictwitter.models;

import android.text.format.DateUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;

/**
 * Created by tina on 6/19/14.
 */
public class Tweet {
    private String body;
    private long tid;
    private String createdAt;
    private User user;

    public static Tweet fromJSON(JSONObject jsonObject) {
        Tweet tweet = new Tweet();
        // Extract values from the json to populate the member variables
        try {
            tweet.body = jsonObject.getString("text");
            tweet.tid = jsonObject.getLong("id");
            tweet.createdAt = getRelativeTimeAgo(jsonObject.getString("created_at"));
            tweet.user = User.fromJSON(jsonObject.getJSONObject("user"));
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
        return tweet;
    }

    public static ArrayList<Tweet> fromJSONArray(JSONArray jsonArray) {
        ArrayList<Tweet> tweets = new ArrayList<Tweet>(jsonArray.length());
        JSONObject tweetJson = null;
        for (int i = 0; i < jsonArray.length(); i++) {
            try {
                tweetJson = jsonArray.getJSONObject(i);
            } catch (Exception e) {
                e.printStackTrace();
                continue;
            }

            Tweet tweet = Tweet.fromJSON(tweetJson);
            if (tweet != null) {
                tweets.add(tweet);
            }
        }
        return tweets;
    }

    @Override
    public String toString() {
        return getBody() + " - " + getUser().getScreenName();
    }

    // getRelativeTimeAgo("Mon Apr 01 21:16:23 +0000 2014");
    public static String getRelativeTimeAgo(String rawJsonDate) {
        String twitterFormat = "EEE MMM dd HH:mm:ss ZZZZZ yyyy";
        SimpleDateFormat sf = new SimpleDateFormat(twitterFormat, Locale.getDefault());
        sf.setLenient(true);

        String relativeDate = "";
        try {
            long dateMillis = sf.parse(rawJsonDate).getTime();
            relativeDate = DateUtils.getRelativeTimeSpanString(dateMillis,
                    System.currentTimeMillis(), DateUtils.SECOND_IN_MILLIS, DateUtils.LENGTH_SHORTEST).toString();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return relativeDate;
    }

    public String getBody() {
        return body;
    }

    public long getTid() {
        return tid;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public User getUser() {
        return user;
    }

}
