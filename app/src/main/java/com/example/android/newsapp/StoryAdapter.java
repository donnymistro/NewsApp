package com.example.android.newsapp;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class StoryAdapter extends ArrayAdapter<Story>{
    public String formatDate(Date dateObject) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("EEE dd, yyyy", Locale.US);
        return dateFormat.format(dateObject);
    }
    public StoryAdapter(Context context, List<Story> stories){
        super(context, 0, stories);
    }
    public View getView(int position, View convertView, ViewGroup parent) {
        // Check if an existing view is being reused, otherwise inflate the view
        View listItem = convertView;
        if (listItem == null) {
            listItem = LayoutInflater.from(getContext()).inflate(
                    R.layout.activity_main, parent, false);
        }
        Story stories = getItem(position);
        TextView section = listItem.findViewById(R.id.section_view);
        section.setText(stories.getSection());
        TextView title = listItem.findViewById(R.id.title_view);
        title.setText(stories.getTitle());
        TextView author = listItem.findViewById(R.id.author_view);
        if (!stories.getAuthor().equals("")) {
            author.setText(stories.getAuthor());
            author.setVisibility(View.VISIBLE);
        } else {
            author.setVisibility(View.GONE);
        }
        Date dateObject = new Date(stories.getDate());
        TextView date = listItem.findViewById(R.id.date_view);
        String formatDate = formatDate(dateObject);
        date.setText(formatDate);
        TextView url = listItem.findViewById(R.id.url_view);
        url.setText(stories.getUrl());
        return listItem;
    }
}