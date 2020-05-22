package com.example.reminderapp;

import java.util.Date;

public class Assignment {
    String subject;
    String topic;
    String date;
    String id;
    Date originalDate;
    Date reminderDate;

    public Assignment(){
    }



    public Assignment(String subject, String topic, String date,  String id, Date originalDate, Date reminderDate){
        this.subject = subject;
        this.topic = topic;
        this.date = date;
        this.id = id;
        this.originalDate = originalDate;
        this.reminderDate = reminderDate;
    }

    public String getSubject() {
        return subject;
    }

    public String getTopic() {
        return topic;
    }

    public String getDate() {  return date; }

    public String getId() { return id; }

    public Date getOriginalDate() { return  originalDate;}

    public Date getReminderDate() { return  reminderDate;}


}
