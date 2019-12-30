package com.example.shafkat.emergencyshake.SendSms;

import com.example.shafkat.emergencyshake.ContactAll.EventClass;

public class IndividualClass {

    private String sms;

    private static IndividualClass individualClass= new IndividualClass();

    public IndividualClass() {
    }

    public static IndividualClass getInstance( ) {
        return individualClass;
    }

    public String getSms() {
        return sms;
    }

    public void setSms(String sms) {
        this.sms = sms;
    }
}
