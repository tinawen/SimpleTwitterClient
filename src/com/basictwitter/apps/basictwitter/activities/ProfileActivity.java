package com.basictwitter.apps.basictwitter.activities;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.basictwitter.apps.basictwitter.helpers.TwitterApplication;
import com.basictwitter.apps.basictwitter.models.User;
import com.codepath.apps.basictwitter.R;
import com.codepath.apps.basictwitter.fragments.TweetsListFragment;
import com.codepath.apps.basictwitter.fragments.UserTimelineFragment;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.nostra13.universalimageloader.core.ImageLoader;

import org.json.JSONObject;

public class ProfileActivity extends FragmentActivity implements TweetsListFragment.OnProgressChangedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
        setContentView(R.layout.activity_profile);
        User user = (User) getIntent().getSerializableExtra("user");
        if (user != null) {
            setupWithUser(user);
        }
        else {
            TwitterApplication.getRestClient().getMeUser(new JsonHttpResponseHandler() {
                @Override
                public void onSuccess(JSONObject jsonObject) {
                    User u = new User(jsonObject);
                    setupWithUser(u);
                }
                @Override
                public void onFailure(Throwable throwable, String s) {
                    Log.d("debug", throwable.toString());
                    Log.d("debug", s.toString());
                    Toast.makeText(ProfileActivity.this, "Failure! " + s, Toast.LENGTH_LONG).show();
                }

                @Override
                protected void handleFailureMessage(Throwable t, String s) {
                    Toast.makeText(ProfileActivity.this, "Failure! " + s, Toast.LENGTH_LONG).show();
                }
            });
        }


    }

    @Override
    public void onStartLoading() {
        setProgressBarIndeterminateVisibility(true);
    }

    @Override
    public void onFinishLoading() {
        setProgressBarIndeterminateVisibility(false);
    }

    private void setupWithUser(User user) {
        getActionBar().setTitle("@" + user.getScreenName());
        populateProfileHeader(user);
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        UserTimelineFragment fragment = UserTimelineFragment.newInstance(user.getUid());
        ft.replace(R.id.your_placeholder, fragment);
        ft.commit();
    }

    private void populateProfileHeader(User u) {
        TextView tvName = (TextView) findViewById(R.id.tvName);
        TextView tvTagline = (TextView) findViewById(R.id.tvTagline);
        TextView tvFollowers = (TextView) findViewById(R.id.tvFollowers);
        TextView tvFollowing = (TextView) findViewById(R.id.tvFollowing);
        ImageView ivProfileImage = (ImageView) findViewById(R.id.ivProfileImage);
        tvName.setText(u.getName());
        tvTagline.setText(u.getTagline());
        tvFollowers.setText(u.getNumFollowers() + " followers");
        tvFollowing.setText(u.getNumFollowings() + " followings");
        ivProfileImage.setImageResource(Color.TRANSPARENT);
        ImageLoader imageLoader = ImageLoader.getInstance();
        imageLoader.displayImage(u.getProfileImageUrl(), ivProfileImage);
    }
}
