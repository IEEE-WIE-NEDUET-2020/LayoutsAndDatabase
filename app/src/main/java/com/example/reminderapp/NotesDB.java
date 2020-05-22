package com.example.reminderapp;

import android.media.Image;
import android.widget.ImageView;

import java.util.Date;

public class NotesDB {

    String title;
    String note;
    String imageURL;
    int color;
    String id;



    public NotesDB(){
    }



    public NotesDB(String title, String note, String imageURL, int color, String id){
        this.title = title;
        this.note = note;
        this.imageURL  = imageURL;
        this.color = color;
        this.id = id;

    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this .note = note;
    }

    public String getImageURL() {return imageURL;}

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    public int getColor() { return color; }

    public String getId() {
        return id;
    }


}
