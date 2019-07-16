package com.example.blacksky.datastructures;

import android.content.Context;
import android.database.Cursor;

import com.example.blacksky.databases.DatabaseHelper;
import com.example.blacksky.datamodels.SPDataModel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ServiceProviderData {

    public static HashMap<String, List<String>> ServiceExpense = new HashMap<>();

    public static List<SPDataModel> getSP_clients(Context context) {

        DatabaseHelper myDb = new DatabaseHelper(context);
        Cursor res = myDb.getAllData(DatabaseHelper.SPP_TABLE);
        List<SPDataModel> sp_providers = new ArrayList<>();

        if (res.getCount() == 0) {
            sp_providers.clear();
            return sp_providers;
        }

        while (res.moveToNext()) {
            String sName = res.getString(1);
            String sPhone = res.getString(2);
            String sService = res.getString(3);
            String sDate = res.getString(4);
            String sAgreedAmount = res.getString(5);
            String sDepositAmount = res.getString(6);
            String sBalance = res.getString(7);

            SPDataModel s_provider = new SPDataModel();

            s_provider.setName(sName);
            s_provider.setPhone(sPhone);
            s_provider.setService(sService);
            s_provider.setLockedDate(sDate);
            s_provider.setAgreedAmount(Integer.parseInt(sAgreedAmount));
            s_provider.setDeposit(Integer.parseInt(sDepositAmount));
            s_provider.setBalance(Integer.parseInt(sBalance));

            sp_providers.add(s_provider);
        }

        return sp_providers;
    }

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
