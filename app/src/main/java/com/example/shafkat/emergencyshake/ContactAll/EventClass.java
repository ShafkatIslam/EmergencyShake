package com.example.shafkat.emergencyshake.ContactAll;

public class EventClass {

    private int id;
    private String number;
    private String name;
    private static EventClass eventClass= new EventClass();

    public EventClass() {
    }

    /* Static 'instance' method */
    public static EventClass getInstance( ) {
        return eventClass;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
