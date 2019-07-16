package com.example.blacksky.datamodels;

import android.content.Context;

import com.example.blacksky.databases.DatabaseHelper;
import com.example.blacksky.properties.Properties;

import java.util.ArrayList;
import java.util.List;

public class ChartDataModel {

    private int totalEarnings;
    private int totalSpending;
    private List<Integer> jobEarning = new ArrayList<>();

    DatabaseHelper myDb;

    public ChartDataModel(Context context) {
        myDb = new DatabaseHelper(context);
    }


    public void setTotalEarnings() {
        totalEarnings = myDb.getTotalEarnings();
    }

    public int getTotalEarnings() {
        setTotalEarnings();
        return totalEarnings;
    }

    public void setTotalSpending() {
        totalSpending = myDb.getTotalServicePaid();
    }

    public int getTotalSpending() {
        setTotalSpending();
        return totalSpending;
    }

    private int setJobEarning(String job){
        return myDb.getTotalEarnings(job);
    }

    public List<Integer> getJobEarnings() {
        for (String job : Properties.SERVICES) {
            jobEarning.add(setJobEarning(job));
        }

        return jobEarning;
    }

}
