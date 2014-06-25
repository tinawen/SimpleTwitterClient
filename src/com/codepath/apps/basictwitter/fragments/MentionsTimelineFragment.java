package com.codepath.apps.basictwitter.fragments;

import com.basictwitter.apps.basictwitter.helpers.TwitterClient;
import com.loopj.android.http.JsonHttpResponseHandler;

public class MentionsTimelineFragment extends TweetsListFragment {
    public void sendClientRequest(TwitterClient client, long max_id, JsonHttpResponseHandler handler) {
        client.getMentionsTimeline(max_id, handler);
    }
}
