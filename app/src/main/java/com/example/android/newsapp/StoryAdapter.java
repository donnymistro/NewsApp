package com.example.android.newsapp;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import java.util.List;
public class StoryAdapter extends ArrayAdapter<Story>{
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
        author.setText(stories.getAuthor());
        TextView date = listItem.findViewById(R.id.date_view);
        date.setText(stories.getDate());
        TextView body = listItem.findViewById(R.id.body_view);
        body.setText(stories.getBody());
        TextView url = listItem.findViewById(R.id.url_view);
        body.setText(stories.getUrl());
        return listItem;
    }
}