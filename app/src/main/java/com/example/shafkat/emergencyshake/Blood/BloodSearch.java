package com.example.shafkat.emergencyshake.Blood;

public class BloodSearch {

    String name;
    String number;
    String blood;

    public BloodSearch(String name, String number, String blood) {
        this.name = name;
        this.number = number;
        this.blood = blood;
    }

    public BloodSearch() {

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

    public String getBlood() {
        return blood;
    }

    public void setBlood(String blood) {
        this.blood = blood;
    }
}
