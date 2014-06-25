package com.basictwitter.apps.basictwitter.models;

import com.activeandroid.Model;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

/**
 * Created by tina on 6/20/14.
 */
public class User extends Model implements Serializable {
    private long uid;
    private String name;
    private String screenName;
    private String profileImageUrl;
    private String tagline;
    private int numFollowers;
    private int numFollowings;


    public String getName() {
        return name;
    }

    public long getUid() {
        return uid;
    }

    public String getScreenName() {
        return screenName;
    }

    public String getProfileImageUrl() {
        return profileImageUrl;
    }

    public String getTagline() {
        return tagline;
    }

    public int getNumFollowers() {
        return numFollowers;
    }

    public int getNumFollowings() {
        return numFollowings;
    }
    // Parse model from JSON
    public User(JSONObject jsonObject){
        try {
            this.name = jsonObject.getString("name");
            this.uid = jsonObject.getLong("id");
            this.screenName = jsonObject.getString("screen_name");
            this.profileImageUrl = jsonObject.getString("profile_image_url");
            this.tagline = jsonObject.getString("description");
            this.numFollowers = jsonObject.getInt("followers_count");
            this.numFollowings = jsonObject.getInt("friends_count");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}