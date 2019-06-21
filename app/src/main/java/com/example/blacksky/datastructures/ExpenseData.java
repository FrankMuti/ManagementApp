package com.example.blacksky.datastructures;

import android.content.Context;
import android.database.Cursor;

import com.example.blacksky.databases.DatabaseHelper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ExpenseData {

    public static HashMap<String, List<String>> ServiceExpense = new HashMap<>();

    public static HashMap<String, List<String>> getData(Context context) {
        DatabaseHelper myDb = new DatabaseHelper(context);
        Cursor res = myDb.getAllData(DatabaseHelper.SPP_TABLE);

        if (res.getCount() == 0){
            ServiceExpense.put(null, null);
            return ServiceExpense;
        }

        while (res.moveToNext()) {
            String sName = res.getString(1);
            String sPhone = res.getString(2);
            String sService = res.getString(3);
            String sDate = res.getString(4);
            String sAgreedAmount = res.getString(4);
            String sDepositAmount = res.getString(5);
            String sBalance = res.getString(6);

            List<String> ServiceDetails = new ArrayList<>();

            ServiceDetails.clear();
            ServiceDetails.add(sName);
            ServiceDetails.add(sPhone);
            ServiceDetails.add(sService);
            ServiceDetails.add(sDate);
            ServiceDetails.add(sAgreedAmount);
            ServiceDetails.add(sDepositAmount);
            ServiceDetails.add(sBalance);

            ServiceExpense.put(sService, ServiceDetails);
        }
        return ServiceExpense;
    }



}
