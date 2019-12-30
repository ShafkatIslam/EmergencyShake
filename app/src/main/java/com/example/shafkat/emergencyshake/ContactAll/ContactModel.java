package com.example.shafkat.emergencyshake.ContactAll;

public class ContactModel {

    private String name, number;

    public ContactModel(String name, String number) {

        this.name = name;
        this.number = number;
    }

    public ContactModel() {

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
