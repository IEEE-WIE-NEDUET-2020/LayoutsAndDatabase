package com.example.reminderapp;

public class AlertsDB {
    String title;
    String details;
    String id;


    public AlertsDB(){

    }


    public AlertsDB(String title, String details, String id){
        this.title = title;
        this.details = details;
        this.id = id;
    }



    public String getTitle() {
        return title;
    }


    public String getDetails() {
        return details;
    }

    public String getId(){  return  id ;}

}
