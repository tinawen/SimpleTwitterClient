package com.codepath.apps.basictwitter.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.basictwitter.apps.basictwitter.adapters.TweetArrayAdapter;
import com.basictwitter.apps.basictwitter.helpers.EndlessScrollListener;
import com.basictwitter.apps.basictwitter.helpers.TwitterApplication;
import com.basictwitter.apps.basictwitter.helpers.TwitterClient;
import com.basictwitter.apps.basictwitter.models.Tweet;
import com.codepath.apps.basictwitter.R;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;

import java.util.ArrayList;

import uk.co.senab.actionbarpulltorefresh.library.ActionBarPullToRefresh;
import uk.co.senab.actionbarpulltorefresh.library.PullToRefreshLayout;
import uk.co.senab.actionbarpulltorefresh.library.listeners.OnRefreshListener;

abstract public class TweetsListFragment extends Fragment implements
        OnRefreshListener {
    private ArrayList<Tweet> tweets;
    private ArrayAdapter<Tweet> aTweets;
    private ListView lvTweets;
    private PullToRefreshLayout mPullToRefreshLayout;
    private TwitterClient client;
    private Boolean isLoading;
    private OnProgressChangedListener listener;

    public interface OnProgressChangedListener {
        public void onStartLoading();
        public void onFinishLoading();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        isLoading = false;
        client = TwitterApplication.getRestClient();
        tweets = new ArrayList<Tweet>();
        aTweets = new TweetArrayAdapter(getActivity(), tweets);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tweets_list, container, false);
        mPullToRefreshLayout = (PullToRefreshLayout) view.findViewById(R.id.ptr_layout);
        ActionBarPullToRefresh.from(getActivity()).allChildrenArePullable().listener(this).setup(mPullToRefreshLayout);
        populateTimeline();
        lvTweets = (ListView) view.findViewById(R.id.lvTweets);
        lvTweets.setOnScrollListener(new EndlessScrollListener() {
            @Override
            public void onLoadMore(int page, int totalItemsCount) {
                long lastTweetId = tweets.get(totalItemsCount - 1).getTid();
                fetchMorePosts(lastTweetId);
            }
        });

        lvTweets.setAdapter(aTweets);
        return view;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if (activity instanceof OnProgressChangedListener) {
            listener = (OnProgressChangedListener) activity;
        } else {
            throw new ClassCastException(activity.toString()
                    + " must implement TweetsListFragment.OnProgressChangedListener");
        }
    }

    public void addAll(ArrayList<Tweet> tweets) {
        aTweets.addAll(tweets);
    }

    public void clear() {
        aTweets.clear();
    }

    public void insertFirst(Tweet tweet) {
        aTweets.insert(tweet, 0);
    }

    @Override
    public void onRefreshStarted(View view) {
        populateTimeline();
        mPullToRefreshLayout.setRefreshComplete();
    }

    public void populateTimeline() {
        fetchMorePosts(true, Long.MAX_VALUE);
    }

    abstract public void sendClientRequest(TwitterClient client, long max_id, JsonHttpResponseHandler handler);

    public void fetchMorePosts(long max_id) {
        fetchMorePosts(false, max_id);
    }

    private void fetchMorePosts(final boolean reset, long max_id) {
        if (isLoading) {
            return;
        }
        isLoading = true;
        listener.onStartLoading();
        this.sendClientRequest(client, max_id, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(JSONArray jsonArray) {
                listener.onFinishLoading();
                if (reset) {
                    clear();
                }
                addAll(Tweet.fromJSONArray(jsonArray));
                isLoading = false;
            }

            @Override
            public void onFailure(Throwable throwable, String s) {
                listener.onFinishLoading();
                Log.d("debug", throwable.toString());
                Log.d("debug", s.toString());
                Toast.makeText(getActivity(), "Failure! " + s, Toast.LENGTH_LONG).show();
                isLoading = false;
            }

            @Override
            protected void handleFailureMessage(Throwable t, String s) {
                listener.onFinishLoading();
                Toast.makeText(getActivity(), "Failure! " + s, Toast.LENGTH_LONG).show();
                isLoading = false;
            }
        });
    }
}
