package com.codepath.apps.basictwitter.fragments;

import android.os.Bundle;

import com.basictwitter.apps.basictwitter.helpers.TwitterClient;
import com.loopj.android.http.JsonHttpResponseHandler;

public class UserTimelineFragment extends TweetsListFragment {
    private long user_id;

    public static UserTimelineFragment newInstance(long user_id) {
        UserTimelineFragment fragment = new UserTimelineFragment();
        Bundle args = new Bundle();
        args.putLong("user_id", user_id);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        user_id = getArguments().getLong("user_id", 0);
    }


    public void sendClientRequest(TwitterClient client, long max_id, JsonHttpResponseHandler handler) {
        client.getUserTimeline(max_id, user_id, handler);
    }

}
