package com.example.blacksky.datastructures;


import android.content.Context;
import android.database.Cursor;

import com.example.blacksky.databases.DatabaseHelper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class AttendedClientsData {

    public static HashMap<String, List<String>> AttendedClients = new HashMap<>();



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
