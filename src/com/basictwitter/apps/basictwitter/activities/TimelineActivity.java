package com.basictwitter.apps.basictwitter.activities;

import android.app.ActionBar;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;

import com.basic.twitter.apps.basictwitter.listeners.FragmentTabListener;
import com.basictwitter.apps.basictwitter.models.Tweet;
import com.basictwitter.apps.basictwitter.models.User;
import com.codepath.apps.basictwitter.R;
import com.codepath.apps.basictwitter.fragments.HomeTimelineFragment;
import com.codepath.apps.basictwitter.fragments.MentionsTimelineFragment;
import com.codepath.apps.basictwitter.fragments.TweetsListFragment;

public class TimelineActivity extends FragmentActivity implements TweetsListFragment.OnProgressChangedListener {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
        setContentView(R.layout.activity_timeline);
        setupTabs();
    }

    private void setupTabs() {
        ActionBar actionBar = getActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
        actionBar.setDisplayShowTitleEnabled(true);

        ActionBar.Tab tab1 = actionBar
                .newTab()
                .setText(R.string.tab_home)
                .setIcon(R.drawable.ic_action_home)
                .setTag("HomeTimelineFragment")
                .setTabListener(
                        new FragmentTabListener<HomeTimelineFragment>(R.id.flContainer, this, "HomeTimelineFragment",
                                HomeTimelineFragment.class));

        actionBar.addTab(tab1);
        actionBar.selectTab(tab1);

        ActionBar.Tab tab2 = actionBar
                .newTab()
                .setText(R.string.tab_mentions)
                .setIcon(R.drawable.ic_action_mentions)
                .setTag("MentionsTimelineFragment")
                .setTabListener(
                        new FragmentTabListener<MentionsTimelineFragment>(R.id.flContainer, this, "MentionsTimelineFragment",
                                MentionsTimelineFragment.class)
                );

        actionBar.addTab(tab2);
    }

    @Override
    public void onStartLoading() {
        setProgressBarIndeterminateVisibility(true);
    }

    @Override
    public void onFinishLoading() {
        setProgressBarIndeterminateVisibility(false);
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
        } else if (id == R.id.miProfile) {
            editProfile();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private final int REQUEST_CODE = 10;
    private void composeMessage() {
        Intent intent = new Intent(getApplicationContext(), ComposeTweetActivity.class);
        startActivityForResult(intent, REQUEST_CODE);
    }

    private void editProfile() {
        Intent intent = new Intent(getApplicationContext(), ProfileActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // REQUEST_CODE is defined above
        if (resultCode == RESULT_OK && requestCode == REQUEST_CODE) {
            Tweet newTweet = (Tweet) data.getSerializableExtra("newTweet");
            TweetsListFragment fragmentTweetList = (TweetsListFragment) getSupportFragmentManager().findFragmentByTag((String) getActionBar().getSelectedTab().getTag());
            fragmentTweetList.insertFirst(newTweet);
            fragmentTweetList.populateTimeline();
        }
    }


    public void profileImageTapped(View v) {
        Intent intent = new Intent(getApplicationContext(), ProfileActivity.class);
        User user = (User) v.getTag();
        intent.putExtra("user", user);
        startActivity(intent);
    }
}
