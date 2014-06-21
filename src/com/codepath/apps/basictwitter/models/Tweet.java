package com.codepath.apps.basictwitter.models;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Delete;
import com.activeandroid.query.Select;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/*
 * This is a temporary, sample model that demonstrates the basic structure
 * of a SQLite persisted Model object. Check out the ActiveAndroid wiki for more details:
 * https://github.com/pardom/ActiveAndroid/wiki/Creating-your-database-model
 * 
 */
@Table(name = "Tweets")
public class Tweet extends Model implements Serializable {
    @Column(name = "tid", unique = true, onUniqueConflict = Column.ConflictAction.REPLACE)
    public long tid;
	// Define table fields
	@Column(name = "Body")
	private String body;
    @Column(name = "CreatedAt")
    private String createdAt;
    @Column(name = "User")
    private User user;
	
	public Tweet() {
		super();
	}
	
	// Parse model from JSON
	public Tweet(JSONObject jsonObject){
		super();

		try {
            User user = User.getOrCreateUserWithJson(jsonObject.getJSONObject("user"));
            this.body = jsonObject.getString("text");
            this.tid = jsonObject.getLong("id");
            this.createdAt = jsonObject.getString("created_at");
            this.user = user;
            this.save();
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

    // Record Finders
	public static Tweet byId(long id) {
	   return new Select().from(Tweet.class).where("id = ?", id).executeSingle();
	}
	
	public static List<Tweet> getAllTweets() {
        return new Select().from(Tweet.class).orderBy("tid DESC").execute();
	}

    public static void deleteAll() {
        new Delete().from(Tweet.class).execute();
    }

}

