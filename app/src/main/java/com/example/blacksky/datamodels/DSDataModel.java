package com.example.blacksky.datamodels;

public class DSDataModel {

    private String Name;
    private String Phone;
    private String Location;
    private String Date;
    private String Time;
    private String Service;
    private String AgreedAmount;
    private String Deposit;

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getPhone() {
        return Phone;
    }

    public void setPhone(String phone) {
        Phone = phone;
    }

    public String getLocation() {
        return Location;
    }

    public void setLocation(String location) {
        Location = location;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }

    public String getTime() {
        return Time;
    }

    public void setTime(String time) {
        Time = time;
    }

    public String getService() {
        return Service;
    }

    public void setService(String service) {
        Service = service;
    }


    public String getAgreedAmount() {
        return AgreedAmount;
    }

    public void setAgreedAmount(String agreedAmount) {
        AgreedAmount = agreedAmount;
    }

    public String getDeposit() {
        return Deposit;
    }

    public void setDeposit(String deposit) {
        Deposit = deposit;
    }
}
