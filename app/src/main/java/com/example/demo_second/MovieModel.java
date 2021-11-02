package com.example.demo_second;

import android.graphics.Bitmap;

public class MovieModel {

    String title;
    String release_date;
    String img_path;

    public MovieModel(String title, String release_date, String img_path){
        this.title = title;
        this.release_date = release_date;
        this.img_path = img_path;
    }

    public MovieModel(){

    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getRelease_date() {
        return release_date;
    }

    public void setRelease_date(String release_date) {
        this.release_date = release_date;
    }

    public String getImg_path(){
        return img_path;
    }

    public void setImg_path(String img_path) {
        this.img_path = img_path;
    }
}
