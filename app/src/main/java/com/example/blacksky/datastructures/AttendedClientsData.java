package com.example.blacksky.datastructures;


import android.content.Context;
import android.database.Cursor;

import com.example.blacksky.databases.DatabaseHelper;
import com.example.blacksky.datamodels.AADataModel;
import com.example.blacksky.datamodels.PCDataModel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class AttendedClientsData {

    public static HashMap<String, List<String>> AttendedClients = new HashMap<>();

    public static List<AADataModel> getAA_clients(Context context) {
        List<AADataModel> aa_clients = new ArrayList<>();
        DatabaseHelper myDb = new DatabaseHelper(context);
        Cursor res = myDb.getAllData(DatabaseHelper.AC_TABLE);
        if (res.getCount() == 0) {
            aa_clients.clear();
            return aa_clients;
        }

        while (res.moveToNext()) {
            String cName = res.getString(1);
            String cPhone = res.getString(2);
            String cService = res.getString(3);
            String cAmount = res.getString(4);
            String cDeposit = res.getString(5);
            String cBalance = res.getString(6);

            AADataModel ac_client = new AADataModel();

            ac_client.setName(cName);
            ac_client.setPhone(cPhone);
            ac_client.setService(cService);
            ac_client.setAmount(cAmount);
            ac_client.setDeposit(cDeposit);
            ac_client.setBalance(cBalance);

            aa_clients.add(ac_client);
        }

        return aa_clients;
    }

    public static HashMap<String, List<String>> getData(Context context) {

        DatabaseHelper myDb = new DatabaseHelper(context);
        Cursor res = myDb.getAllData(DatabaseHelper.AC_TABLE);

        if (res.getCount() == 0){
            AttendedClients.put(null, null);
            return AttendedClients;
        }

        while (res.moveToNext()){
            String cName = res.getString(1);
            String cPhone = res.getString(2);
            String cAmount = res.getString(3);


            List<String> ClientDetails = new ArrayList<>();

            ClientDetails.clear();
            ClientDetails.add(cPhone);
            ClientDetails.add("Amount: " + cAmount);

            AttendedClients.put(cName, ClientDetails);
        }

        return AttendedClients;
    }

}
