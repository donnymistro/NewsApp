package com.example.android.newsapp;
public class Story {
    private String mSection;
    private String mTitle;
    private String mAuthor;
    private String mDate;
    private String mUrl;
    public Story(String section, String title, String author, String date, String url){
        mSection = section;
        mTitle = title;
        mAuthor = author;
        mDate = date;
        mUrl = url;
    }
    public String getSection(){
        return mSection;
    }
    public String getTitle(){
        return mTitle;
    }
    public String getAuthor(){
        return mAuthor;
    }
    public String getDate(){
        return mDate;
    }
    public String getUrl() {
        return mUrl;
    }
}