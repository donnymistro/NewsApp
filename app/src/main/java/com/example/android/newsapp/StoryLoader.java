package com.example.android.newsapp;
import android.content.AsyncTaskLoader;
import android.content.Context;
import java.util.List;
public class StoryLoader extends AsyncTaskLoader<List<Story>> {
    /** Query URL */
    private String mUrl;
    /**
     * Constructs a new StoryLoader.
     */
    public StoryLoader(Context context, String url) {
        super(context);
        mUrl = url;
    }
    @Override
    protected void onStartLoading() {
        forceLoad();
    }
    /**
     * This is on a background thread.
     */
    @Override
    public List<Story> loadInBackground() {
        if (mUrl == null) {
            return null;
        }
        // Perform the network request, parse the response, and extract a list of earthquakes.
        List<Story> stories = (List<Story>) Utils.getStoryData(mUrl);
        return stories;
    }
}