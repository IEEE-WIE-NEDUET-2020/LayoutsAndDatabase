package com.example.reminderapp;

public class FriDB {
    String dayofWeek;
    String subject;
    String code;
    String time;
    String category;



    public FriDB(){
    }



    public FriDB(String dayofWeek, String subject, String code,  String time, String category){
        this.dayofWeek = dayofWeek;
        this.subject = subject;
        this.code = code;
        this.time = time;
        this.category = category;

    }

    public String getDayofWeek() {
        return dayofWeek;
    }


    public String getSubject() {
        return subject;
    }


    public String getCode() {  return code; }

    public String getTime() { return time; }

    public String getCategory() { return category; }



}
