package com.codepath.apps.basictwitter;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.AndroidCharacter;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.codepath.apps.basictwitter.models.Tweet;
import com.codepath.apps.basictwitter.models.User;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.nostra13.universalimageloader.core.ImageLoader;

import org.json.JSONObject;

public class ComposeTweetActivity extends Activity {
    private com.codepath.apps.basictwitter.TwitterClient client;
    private static final int TWITTER_LETTER_COUNT_LIMIT = 140;
    EditText etCompose;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compose_tweet);
        client = com.codepath.apps.basictwitter.TwitterApplication.getRestClient();
        etCompose = (EditText) findViewById(R.id.etCompose);
        etCompose.addTextChangedListener(new TextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {
                int letterCount = etCompose.getText().length();
                int letterRemaining = TWITTER_LETTER_COUNT_LIMIT - letterCount;
                TextView etWordCount = (TextView) findViewById(R.id.tvWordCount);
                etWordCount.setText(String.valueOf(letterRemaining));
                if (letterRemaining >= 0) {
                    etWordCount.setTextColor(getResources().getColor(R.color.gray_color));
                } else {
                    etWordCount.setTextColor(getResources().getColor(R.color.error_color));
                }
            }
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}
        });
        client.getMeUser(new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(JSONObject jsonObject) {
                User meUser = User.getOrCreateUserWithJson(jsonObject);
                ImageView ivProfileImage = (ImageView) findViewById(R.id.ivProfile);
                ivProfileImage.setImageResource(Color.TRANSPARENT);
                ImageLoader imageLoader = ImageLoader.getInstance();
                imageLoader.displayImage(meUser.getProfileImageUrl(), ivProfileImage);
                TextView etName = (TextView) findViewById(R.id.tvName);
                TextView etScreenName = (TextView) findViewById(R.id.tvScreenName);
                etName.setText(meUser.getName());
                etScreenName.setText("@" + meUser.getScreenName());
            }
            @Override
            public void onFailure(Throwable throwable, String s) {
                Log.d("debug", throwable.toString());
                Log.d("debug", s.toString());
            }

            @Override
            protected void handleFailureMessage(Throwable t, String s) {
                Toast.makeText(ComposeTweetActivity.this, "Failure! " + s, Toast.LENGTH_LONG).show();
                Log.d("debug", "Tweeter possibly rate limited us " + s);
            }
        });
    }

    public void createTweet(View v) {

        client.tweet(etCompose.getText().toString(), new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(JSONObject jsonObject) {
                Tweet newTweet = new Tweet(jsonObject);
                Intent data = new Intent();
                data.putExtra("newTweet", newTweet);
                setResult(RESULT_OK, data); // set result code and bundle data for response
                ComposeTweetActivity.this.finish();
            }

            @Override
            public void onFailure(Throwable throwable, String s) {
                Log.d("debug", throwable.toString());
                Log.d("debug", s.toString());
            }

            @Override
            protected void handleFailureMessage(Throwable t, String s) {
                Toast.makeText(ComposeTweetActivity.this, "Failure! " + s, Toast.LENGTH_LONG).show();
                Log.d("debug", "Tweeter possibly rate limited us " + s);
            }
        });
    }
}
