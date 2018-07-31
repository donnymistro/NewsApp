package com.example.android.newsapp;
import android.annotation.SuppressLint;
import android.text.TextUtils;
import android.util.Log;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
public class Utils {
    public static Story getStoryData(String getUrl){
        URL url = createUrl(getUrl);
        // Perform HTTP request to the URL and receive a JSON response back
        String jsonResponse = null;
        try {
            jsonResponse = makeHttpRequest(url);
        } catch (IOException e) {
            Log.e("error code: ", String.valueOf(e));
        }
        // Extract relevant fields from the JSON response and create a Story object

        // Return the news story
        return extractFeatureFromJson(jsonResponse);
    }
    /**
     * Returns new URL object from the given string URL.
     */
    private static URL createUrl(String stringUrl) {
        URL url = null;
        try {
            url = new URL(stringUrl);
        } catch (MalformedURLException e) {
            Log.e("Error with creating URL", String.valueOf(e));
        }
        return url;
    }
    /**
     * Make an HTTP request to the given URL and return a String as the response.
     */
    private static String makeHttpRequest(URL url) throws IOException {
        String jsonResponse = "";
        // If the URL is null, then return early.
        if (url == null) {
            return jsonResponse;
        }
        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;
        try {
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setReadTimeout(10000 /* milliseconds */);
            urlConnection.setConnectTimeout(15000 /* milliseconds */);
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();
            // If the request was successful (response code 200),
            // then read the input stream and parse the response.
            if (urlConnection.getResponseCode() == 200) {
                inputStream = urlConnection.getInputStream();
                jsonResponse = readFromStream(inputStream);
            } else {
                return String.valueOf(urlConnection.getResponseCode());
            }
        } catch (IOException e) {
            Log.e("Can't get JSON results.", String.valueOf(e));
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (inputStream != null) {
                inputStream.close();
            }
        }
        return jsonResponse;
    }
    /**
     * Convert the InputStream into a String which contains the
     * whole JSON response from the server.
     */
    private static String readFromStream(InputStream inputStream) throws IOException {
        StringBuilder output = new StringBuilder();
        if (inputStream != null) {
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
            BufferedReader reader = new BufferedReader(inputStreamReader);
            String line = reader.readLine();
            while (line != null) {
                output.append(line);
                line = reader.readLine();
            }
        }
        return output.toString();
    }
    /**
     * Return a Story object by parsing out information
     * about the first story from the input storyJSON string.
     */
    @SuppressLint("LongLogTag")
    private static Story extractFeatureFromJson(String storyJSON) {
        // If the JSON string is empty or null, then return early.
        if (TextUtils.isEmpty(storyJSON)) {
            return null;
        }
        // Create an empty ArrayList that we can start adding stories to
        List<Story> stories = new ArrayList<>();
        try {
            JSONObject baseJsonResponse = new JSONObject(storyJSON);
            JSONArray storyArray = baseJsonResponse.getJSONArray("features");
            // If there are results in the features array
            for (int i = 0; i < storyArray.length(); i++) {
                // Extract out the first feature (which is a story)
                JSONObject currentStory = storyArray.getJSONObject(i);
                JSONObject properties = currentStory.getJSONObject("properties");
                String section = properties.getString("section");
                String title = properties.getString("title");
                String author = properties.getString("author");
                String date = properties.getString("date");
                String body = properties.getString("body");
                String url = properties.getString("url");
                // Create a new Story object
                Story story = new Story(section, title, author, date, body, url);
                stories.add(story);
            }
        } catch (JSONException e) {
            Log.e("Fail parsing JSON results", String.valueOf(e));
        }
        return (Story) stories;
    }
}