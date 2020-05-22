package com.example.reminderapp;

public class WedDB {
    String dayofWeek;
    String subject;
    String code;
    String time;
    String category;



    public WedDB(){
    }



    public WedDB(String dayofWeek, String subject, String code,  String time, String category){
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
