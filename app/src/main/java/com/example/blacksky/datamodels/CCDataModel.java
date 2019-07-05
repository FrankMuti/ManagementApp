package com.example.blacksky.datamodels;

public class CCDataModel {

    private String Name;
    private String Phone;
    private String Location;
    private String Service;
    private String Date;
    private String Time;
    private int AgreedAmount;
    private int DepositAmount;
    private int BalanceAmount;


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

    public String getService() {
        return Service;
    }

    public void setService(String service) {
        Service = service;
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

    public int getAgreedAmount() {
        return AgreedAmount;
    }

    public void setAgreedAmount(int agreedAmount) {
        AgreedAmount = agreedAmount;
    }

    public int getDepositAmount() {
        return DepositAmount;
    }

    public void setDepositAmount(int depositAmount) {
        DepositAmount = depositAmount;
    }

    public int getBalanceAmount() {
        return BalanceAmount;
    }

    public void setBalanceAmount(int balanceAmount) {
        BalanceAmount = balanceAmount;
    }
}
