package com.example.reminderapp;

import java.util.Date;

public class TestofSub1 {
    String subject;
    String topic;
    String date;
    String id;
    String category;
    Date originalDate;
    Date reminderDate;

    public TestofSub1(){
    }



    public TestofSub1(String subject, String topic, String date,  String id, String category, Date originalDate, Date reminderDate){
        this.subject = subject;
        this.topic = topic;
        this.date = date;
        this.id = id;
        this.category = category;
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

    public String getCategory() { return category; }

    public Date getOriginalDate() { return  originalDate;}

    public Date getReminderDate() { return  reminderDate;}




}
