package com.basictwitter.apps.basictwitter.models;

import com.activeandroid.Model;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;

public class Tweet extends Model implements Serializable {
    private long tid;
    private String body;
    private String createdAt;
    private User user;
    private ArrayList<Media> media;
	
	// Parse model from JSON
	public Tweet(JSONObject jsonObject){
		try {
            JSONObject entitiesJson = jsonObject.getJSONObject("entities");
            long tweetId = jsonObject.getLong("id");
            User user = new User(jsonObject.getJSONObject("user"));
            this.user = user;
            ArrayList<Media> medias = new ArrayList<Media>();
            if (entitiesJson.has("media")) {
                JSONArray mediaJsonArray = entitiesJson.getJSONArray("media");
                for (int i = 0; i < mediaJsonArray.length(); i++) {
                    medias.add(new Media(mediaJsonArray.getJSONObject(i)));
                }
            }
            this.media = medias;
            this.body = jsonObject.getString("text");
            this.tid = tweetId;
            this.createdAt = jsonObject.getString("created_at");
		} catch (JSONException e) {
			e.printStackTrace();
		}
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

            Tweet tweet = new Tweet(tweetJson);
            if (tweet != null) {
                tweets.add(tweet);
            }
        }
        return tweets;
    }
	// Getters
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

    public ArrayList<Media> getMedia() { return media; }
}

