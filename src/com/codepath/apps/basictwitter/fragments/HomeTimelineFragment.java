package com.codepath.apps.basictwitter.fragments;

import com.basictwitter.apps.basictwitter.helpers.TwitterClient;
import com.loopj.android.http.JsonHttpResponseHandler;

public class HomeTimelineFragment extends TweetsListFragment {

    public void sendClientRequest(TwitterClient client, long max_id, JsonHttpResponseHandler handler) {
        client.getHomeTimeline(max_id, handler);
    }
}
