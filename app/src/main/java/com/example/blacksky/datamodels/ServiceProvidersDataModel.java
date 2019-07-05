package com.example.blacksky.datamodels;

public class ServiceProvidersDataModel {

    private String Name;
    private String Phone;
    private String Service;
    private String LockedDate;
    private int AgreedAmount;
    private int Deposit;
    private int Balance;


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

    public String getService() {
        return Service;
    }

    public void setService(String service) {
        Service = service;
    }

    public String getLockedDate() {
        return LockedDate;
    }

    public void setLockedDate(String lockedDate) {
        LockedDate = lockedDate;
    }

    public int getAgreedAmount() {
        return AgreedAmount;
    }

    public void setAgreedAmount(int agreedAmount) {
        AgreedAmount = agreedAmount;
    }

    public int getDeposit() {
        return Deposit;
    }

    public void setDeposit(int deposit) {
        Deposit = deposit;
    }

    public int getBalance() {
        return Balance;
    }

    public void setBalance(int balance) {
        Balance = balance;
    }
}
