package com.example.android.newsapp;
public class Story {
    private String mSection;
    private String mTitle;
    private String mAuthor;
    private String mDate;
    private String mBody;
    private String mUrl;
    public Story(String section, String title, String author, String date, String body, String url){
        mSection = section;
        mTitle = title;
        mAuthor = author;
        mDate = date;
        mBody = body;
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
    public String getBody(){
        return mBody;
    }
    public String getUrl() {
        return mUrl;
    }
}