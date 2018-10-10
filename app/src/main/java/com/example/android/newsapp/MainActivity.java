package com.example.android.newsapp;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import java.util.ArrayList;
import java.util.List;
import android.app.LoaderManager;
import android.app.LoaderManager.LoaderCallbacks;
import android.content.Loader;
import android.widget.TextView;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.content.Context;
public class MainActivity extends AppCompatActivity implements LoaderCallbacks<List<Story>>{
    private static final String GUARDIAN_URL = "https://content.guardianapis.com/search?q=space&show-tags=contributor&api-key=d8d5b71d-1058-480d-87e6-f508214a26f4";
    private StoryAdapter adapter;
    /**
     * Constant value for the earthquake loader ID. We can choose any integer.
     * This really only comes into play if you're using multiple loaders.
     */
    private static final int STORY_LOADER_ID = 1;
    private TextView mEmptyStateTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_item);
        ListView lv = findViewById(R.id.list);
        adapter = new StoryAdapter(this, new ArrayList<Story>());
        lv.setAdapter(adapter);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                // Find the current story that was clicked on
                Story currentStory = adapter.getItem(position);
                // Convert the String URL into a URI object (to pass into the Intent constructor)
                Uri storyUri = Uri.parse(currentStory.getUrl());
                // Create a new intent to view the story URI
                Intent websiteIntent = new Intent(Intent.ACTION_VIEW, storyUri);
                // Send the intent to launch a new activity
                startActivity(websiteIntent);
            }
        });
        mEmptyStateTextView = findViewById(R.id.empty_view);
        lv.setEmptyView(mEmptyStateTextView);
        // Get a reference to the ConnectivityManager to check state of network connectivity
        ConnectivityManager connMgr = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);
        // Get details on the currently active default data network
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        // If there is a network connection, get data
        if (networkInfo != null && networkInfo.isConnected()) {
            // Get a reference to the LoaderManager, in order to interact with loaders.
            LoaderManager loaderManager = getLoaderManager();
            // Initialize the loader. Pass in the int ID constant defined above and pass in null for
            // the bundle. Pass in this activity for the LoaderCallbacks parameter (which is valid
            // because this activity implements the LoaderCallbacks interface).
            loaderManager.initLoader(STORY_LOADER_ID, null, this);
        } else {
            // Otherwise, display error
            // First, hide loading indicator so error message will be visible
            View loadingIndicator = findViewById(R.id.loading_indicator);
            loadingIndicator.setVisibility(View.GONE);
            // Update empty state with no connection error message
            mEmptyStateTextView.setText(R.string.no_internet);
        }
    }
    @Override
    public Loader<List<Story>> onCreateLoader(int i, Bundle bundle) {
        // Create a new loader for the given URL
        return new StoryLoader(this, GUARDIAN_URL);
    }
    @Override
    public void onLoadFinished(Loader<List<Story>> loader, List<Story> stories) {
        adapter.clear();
        // Hide loading indicator because the data has been loaded
        View loadingIndicator = findViewById(R.id.loading_indicator);
        loadingIndicator.setVisibility(View.GONE);
        // Set empty state text to display "No new stories found."
        mEmptyStateTextView.setText(R.string.no_stories);
        // If there is a valid list of Stories, then add them to the adapter's
        // data set. This will trigger the ListView to update.
        if (stories != null && !stories.isEmpty()) {
            adapter.addAll(stories);
        }
    }
    @Override
    public void onLoaderReset(Loader < List < Story >> loader){
        // Loader reset, so we can clear out our existing data.
        adapter.clear();
    }
}
