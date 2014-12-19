package com.omegar;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.omegar.RSSparser.RSSFeed;
import com.omegar.Tools.LoadRSSFeed;
import com.omegar.Tools.WriteObjectFile;

import static com.omegar.RSSparser.RSSUtil.getFeedName;

/**
 * Main list to display the RSS Feed of a site and takes the
 * user to the site in the inline browser when an item is clicked.
 * Also allows manual reloading of the RSS Feed if desired.
 */
public class ListActivity extends Activity implements OnRefreshListener {

    // The adapter for the list
    private ListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Create a new ViewGroup for the fragment
        setContentView(R.layout.feed_list);
        // If we're above Honeycomb
        if (android.os.Build.VERSION.SDK_INT >= 11) {
            // Remove the icon from the ActionBar
            getActionBar().setDisplayShowHomeEnabled(false);
        }

        /* Swipe layout settings to refresh ListView on swipe down */
        SwipeRefreshLayout swipeLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_container);
        swipeLayout.setOnRefreshListener(this);
        // Set the colors to use for the swipelayout
        swipeLayout.setColorSchemeResources(
                android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_purple);

        // Get feed from the passed bundle
        RSSFeed feed = (RSSFeed) new WriteObjectFile(this).readObject(getFeedName());

        // Find the ListView we're using
        ListView list = (ListView) findViewById(R.id.listView);
        // Set the vertical edges to fade when scrolling
        list.setVerticalFadingEdgeEnabled(true);

        // Create a new adapter
        adapter = new ListAdapter(this, feed);
        // Set the adapter to the list
        list.setAdapter(adapter);

        // Set on item click listener to the ListView
        list.setOnItemClickListener(new listItemClickListener());
    }

    private class listItemClickListener implements OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
            // Start the new activity and pass on the feed item
            startActivity(new Intent(getBaseContext(), com.omegar.PostActivity.class).putExtra("pos", position));
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        // Exit instead of going to splash screen
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onRefresh() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                new LoadRSSFeed(ListActivity.this).execute();
            }
        }, 3000);
    }
}