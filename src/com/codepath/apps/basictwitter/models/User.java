package com.codepath.apps.basictwitter.models;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Delete;
import com.activeandroid.query.Select;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.List;

/**
 * Created by tina on 6/20/14.
 */
@Table(name = "Users")
public class User extends Model implements Serializable {
    @Column(name = "uid", unique = true)
    public long uid;
    @Column(name = "Name")
    private String name;
    @Column(name = "ScreenName")
    private String screenName;
    @Column(name = "ProfileImageUrl")
    private String profileImageUrl;

    // Make sure to have a default constructor for every ActiveAndroid model
    public User(){
        super();
    }

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

    // Parse model from JSON
    public User(JSONObject jsonObject){
        super();

        try {
            this.name = jsonObject.getString("name");
            this.uid = jsonObject.getLong("id");
            this.screenName = jsonObject.getString("screen_name");
            this.profileImageUrl = jsonObject.getString("profile_image_url");
            this.save();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public static User getOne(long id) {
        return new Select().from(User.class).where("uid = ?", id).executeSingle();
    }
    public static void deleteAll() {
        new Delete().from(User.class).execute();
    }

    public static List<Tweet> getAllUsers() {
        return new Select().from(User.class).orderBy("uid DESC").execute();
    }

    public static User getOrCreateUserWithJson(JSONObject userJson) {
        User user = null;
        try {
            user = User.getOne(userJson.getLong("id"));
            if (user == null) {
                user = new User(userJson);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        
        return user;
    }
}