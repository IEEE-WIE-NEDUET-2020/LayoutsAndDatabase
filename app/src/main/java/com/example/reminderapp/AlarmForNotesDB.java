package com.example.reminderapp;

import com.google.firebase.database.DatabaseReference;

import java.util.Date;

public class AlarmForNotesDB {
    String title;
    String note;
    String imageUrl;
    String id;
    Date originalDate;



    public AlarmForNotesDB(){

    }


    public AlarmForNotesDB(String title, String note, String imageUrl, String id, Date originalDate){
        this.title = title;
        this.note = note;
        this.imageUrl = imageUrl;
        this.id = id;
        this.originalDate  = originalDate;
    }



    public String getTitle() {
        return title;
    }


    public String getNote() {
        return note;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public String getId(){  return  id ;}

    public Date getOriginalDate(){ return originalDate;}
}
