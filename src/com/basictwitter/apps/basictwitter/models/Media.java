package com.basictwitter.apps.basictwitter.models;

import com.activeandroid.Model;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

/**
 * Created by tina on 6/21/14.
 */
public class Media extends Model implements Serializable {
    private long mid;
    private String mediaUrl;
    private String displayUrl;

    public long getMid() {
        return mid;
    }
    public String getMediaUrl() {
        return mediaUrl;
    }
    public String getDisplayUrl() {
        return displayUrl;
    }

    // Parse model from JSON
    public Media(JSONObject jsonObject){
        try {
            this.mediaUrl = jsonObject.getString("media_url");
            this.mid = jsonObject.getLong("id");
            this.displayUrl = jsonObject.getString("display_url");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}

