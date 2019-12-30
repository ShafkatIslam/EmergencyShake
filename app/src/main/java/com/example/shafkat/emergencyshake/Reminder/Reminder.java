package com.example.shafkat.emergencyshake.Reminder;

public class Reminder {

    String eventName;
    String eventDate;
    String eventContactName;

    public Reminder(String eventName, String eventDate, String eventContactName) {
        this.eventName = eventName;
        this.eventDate = eventDate;
        this.eventContactName = eventContactName;
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public String getEventDate() {
        return eventDate;
    }

    public void setEventDate(String eventDate) {
        this.eventDate = eventDate;
    }

    public String getEventContactName() {
        return eventContactName;
    }

    public void setEventContactName(String eventContactName) {
        this.eventContactName = eventContactName;
    }
}
