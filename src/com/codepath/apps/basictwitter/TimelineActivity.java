package com.codepath.apps.basictwitter;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.codepath.apps.basictwitter.models.Tweet;
import com.codepath.apps.basictwitter.models.User;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.List;

public class TimelineActivity extends Activity {
    private TwitterClient client;
    private ArrayList<Tweet> tweets;
    private ArrayAdapter<Tweet> aTweets;
    private ListView lvTweets;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timeline);
        lvTweets = (ListView) findViewById(R.id.lvTweets);
        lvTweets.setOnScrollListener(new EndlessScrollListener() {
            @Override
            public void onLoadMore(int page, int totalItemsCount) {
                long lastTweetId = tweets.get(totalItemsCount - 1).getTid();
                fetchMorePosts(lastTweetId);
            }
        });
        tweets = new ArrayList<Tweet>();
        aTweets = new TweetArrayAdapter(this, tweets);
        lvTweets.setAdapter(aTweets);
        aTweets.addAll(Tweet.getAllTweets());
        Log.d("debug", "initializing tweets as " + aTweets.getCount());
        Log.d("debug", "all users table responds with " + User.getAllUsers());

        client = TwitterApplication.getRestClient();
        populateTimeline();
    }

    public void populateTimeline() {
        fetchMorePosts(true, Long.MAX_VALUE);
    }

    public void fetchMorePosts(long max_id) {
        fetchMorePosts(false, max_id);
    }

    private void fetchMorePosts(final boolean reset, long max_id) {
        client.getHomeTimeline(max_id, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(JSONArray jsonArray) {
                Log.d("debug", "jSON came back");
                if (reset) {
                    Log.d("debug", "all users are " + User.getAllUsers());
                    Log.d("debug", "all tweets are " + Tweet.getAllTweets());
                    aTweets.clear();
                    Tweet.deleteAll();
                    User.deleteAll();
                }
                aTweets.addAll(Tweet.fromJSONArray(jsonArray));
                Log.d("debug", "total count is " + aTweets.getCount());
                List tweets = Tweet.getAllTweets();
                List users = User.getAllUsers();
            }

            @Override
            public void onFailure(Throwable throwable, String s) {
                Log.d("debug", throwable.toString());
                Log.d("debug", s.toString());
            }

            @Override
            protected void handleFailureMessage(Throwable t, String s) {
                Toast.makeText(TimelineActivity.this, "Failure! " + s, Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.timeline, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.miCompose) {
            composeMessage();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private final int REQUEST_CODE = 10;
    private void composeMessage() {
        Intent intent = new Intent(getApplicationContext(), ComposeTweetActivity.class);
        startActivityForResult(intent, REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // REQUEST_CODE is defined above
        if (resultCode == RESULT_OK && requestCode == REQUEST_CODE) {
            Tweet newTweet = (Tweet) data.getSerializableExtra("newTweet");
            aTweets.insert(newTweet, 0);
            populateTimeline();
        }
    }
}
