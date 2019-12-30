package com.example.shafkat.emergencyshake.GroupContact;

public class ShowAllGroup {
    String name;
    String number;

    public ShowAllGroup(String name, String number)
    {
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
}
