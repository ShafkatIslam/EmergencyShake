package com.example.shafkat.emergencyshake.Group;

public class RenameGroup {

    String name;
    int number;
    boolean box;

    public RenameGroup(String name,int number,boolean box)
    {
        this.name = name;
        this.number =  number;
        this.box = box;

    }


    public boolean isBox() {
        return box;
    }

    public void setBox(boolean box) {
        this.box = box;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }
}

