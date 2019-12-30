package com.example.shafkat.emergencyshake.ContactAll;

public class EventDate {

    String eventName;
    String eventDate;
    boolean box;

    public EventDate(String eventName, String eventDate, boolean box) {
        this.eventName = eventName;
        this.eventDate = eventDate;
        this.box = box;
    }

    public EventDate(String eventName, String eventDate) {

        this.eventName = eventName;
        this.eventDate = eventDate;
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

    public boolean isBox() {
        return box;
    }

    public void setBox(boolean box) {
        this.box = box;
    }
}
