package com.codepath.apps.basictwitter.models;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Delete;
import com.activeandroid.query.Select;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by tina on 6/21/14.
 */
@Table(name = "Medias")
public class Media extends Model implements Serializable {
    @Column(name = "mid", unique = true)
    public long mid;
    @Column(name = "MediaUrl")
    private String mediaUrl;
    @Column(name = "DisplayUrl")
    private String displayUrl;
    @Column(name = "TweetId")
    private long tweetId;

    public Media() {
        super();
    }

    public long getMid() {
        return mid;
    }

    public String getMediaUrl() {
        return mediaUrl;
    }

    public String getDisplayUrl() {
        return displayUrl;
    }

    public long getTweetId() { return tweetId; }

    // Parse model from JSON
    public Media(long tweetId, JSONObject jsonObject){
        super();

        this.tweetId = tweetId;
        try {
            this.mediaUrl = jsonObject.getString("media_url");
            this.mid = jsonObject.getLong("id");
            this.displayUrl = jsonObject.getString("display_url");
            this.save();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public static Media getOne(long id) {
        return new Select().from(Media.class).where("mid = ?", id).executeSingle();
    }

    public static List<String> getMediaUrlsForTweetId(long tweetId) {
        List<Media> all = new Select().from(Media.class).execute();
        List<Media> medias = new Select().from(Media.class).where("TweetId = ?", tweetId).execute();
        ArrayList<String> imageUrls = new ArrayList<String>();
        for (Media media : medias) {
            imageUrls.add(media.getMediaUrl());
        }
        return imageUrls;
    }

    public static Media getOrCreateMediaWithJson(long tweetId, JSONObject mediaJson) {
        Media mediaEntity = null;
        try {
            mediaEntity = Media.getOne(mediaJson.getLong("id"));
            if (mediaEntity == null) {
                mediaEntity = new Media(tweetId, mediaJson);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return mediaEntity;
    }

    public static void deleteAll() {
        new Delete().from(Media.class).execute();
    }
}

