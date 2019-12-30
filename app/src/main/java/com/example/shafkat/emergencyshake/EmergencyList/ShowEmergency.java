package com.example.shafkat.emergencyshake.EmergencyList;

public class ShowEmergency {

    String name;
    String number;
    boolean box;

    public ShowEmergency(String name, String number,boolean box)
    {
        this.name = name;
        this.number =  number;
        this.box = box;

    }

    public ShowEmergency(String name, String number) {

        this.name = name;
        this.number =  number;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public boolean isBox() {
        return box;
    }

    public void setBox(boolean box) {
        this.box = box;
    }
}
