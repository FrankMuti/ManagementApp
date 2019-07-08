package com.example.blacksky.datastructures;

import android.content.Context;
import android.database.Cursor;

import com.example.blacksky.databases.DatabaseHelper;
import com.example.blacksky.datamodels.DSDataModel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ConfirmedClientsData {

    public static HashMap<String, List<String>> ConfirmedClient = new HashMap<>();

    public static List<DSDataModel> up_events = new ArrayList<>();

    public static List<DSDataModel> getUp_events(Context context){

        DatabaseHelper myDb = new DatabaseHelper(context);
        Cursor res = myDb.getAllData(DatabaseHelper.CC_TABLE);

        if (res.getCount() == 0){
            up_events.clear();
            return up_events;
        }

        while (res.moveToNext()) {
            String cName = res.getString(1);
            String cPhone = res.getString(2);
            String cLocation = res.getString(3);
            String cService = res.getString(4);
            String cDate = res.getString(5);

            DSDataModel event = new DSDataModel();

            event.setName(cName);
            event.setPhone(cPhone);
            event.setLocation(cLocation);
            event.setService(cService);
            event.setDate(cDate);

            up_events.add(event);
        }

        return up_events;
    }

    public static HashMap<String, List<String>> getData(Context context) {

        DatabaseHelper myDb = new DatabaseHelper(context);
        Cursor res = myDb.getAllData(DatabaseHelper.CC_TABLE);

        if (res.getCount() == 0){
            ConfirmedClient.put(null, null);
            return ConfirmedClient;
        }

        while (res.moveToNext()){
            String cName = res.getString(1);
            String cPhone = res.getString(2);
            String cLocation = res.getString(3);
            String cService = res.getString(4);
            String cDate = res.getString(5);
            String cTime = res.getString(6);
            String cAgreed = res.getString(7);
            String cDeposit = res.getString(8);
            String cBalance =  res.getString(9);

            List<String> ClientDetails = new ArrayList<>();

            ClientDetails.clear();
            ClientDetails.add(cPhone);
            ClientDetails.add(cLocation);
            ClientDetails.add(cDate);
            ClientDetails.add(cTime);
            ClientDetails.add(cService);
            ClientDetails.add("Agreed Amount : " + cAgreed);
            ClientDetails.add("Deposit : " + cDeposit);
            ClientDetails.add("Balance : " + cBalance);

            ConfirmedClient.put(cName, ClientDetails);
        }
        return ConfirmedClient;
    }


    public void clear(){
        ConfirmedClient.clear();
    }
}

