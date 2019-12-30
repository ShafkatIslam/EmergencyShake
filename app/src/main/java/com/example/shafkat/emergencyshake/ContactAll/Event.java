package com.example.shafkat.emergencyshake.ContactAll;

public class Event {

    private int id;
    private String event;
    private String timestamp;

    public Event() {
    }

    public Event(int id, String event, String timestamp) {
        this.id = id;
        this.event = event;
        this.timestamp = timestamp;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEvent() {
        return event;
    }

    public void setEvent(String event) {
        this.event = event;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }
}
