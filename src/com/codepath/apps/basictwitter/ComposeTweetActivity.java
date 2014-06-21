package com.codepath.apps.basictwitter;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.codepath.apps.basictwitter.models.Tweet;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONObject;

public class ComposeTweetActivity extends Activity {
    private TwitterClient client;
    private static final int TWITTER_LETTER_COUNT_LIMIT = 140;
    EditText etCompose;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compose_tweet);
        client = TwitterApplication.getRestClient();
        etCompose = (EditText) findViewById(R.id.etCompose);
        etCompose.addTextChangedListener(new TextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {
                int letterCount = etCompose.getText().length();
                int letterRemaining = TWITTER_LETTER_COUNT_LIMIT - letterCount;
                TextView etWordCount = (TextView) findViewById(R.id.tvWordCount);
                etWordCount.setText(String.valueOf(letterRemaining));
            }
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

        });

    }

    public void createTweet(View v) {

        client.tweet(etCompose.getText().toString(), new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(JSONObject jsonObject) {
                Toast.makeText(ComposeTweetActivity.this, "tweeting is successful", Toast.LENGTH_SHORT).show();
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
                Log.d("debug", "Tweeter sucks " + s);
            }
        });
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.compose_tweet, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
