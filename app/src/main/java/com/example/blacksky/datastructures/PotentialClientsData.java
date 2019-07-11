package com.example.blacksky.datastructures;

import android.content.Context;
import android.database.Cursor;
import android.support.v7.app.AlertDialog;
import android.widget.Toast;

//import com.example.blacksky.MainActivity;
import com.example.blacksky.databases.DatabaseHelper;
import com.example.blacksky.datamodels.DSDataModel;
import com.example.blacksky.datamodels.PCDataModel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class PotentialClientsData {

    private static HashMap<String, List<String>> PotentialClient = new HashMap<>();

    //public static

    public static List<PCDataModel> getPc_clients(Context context) {
        List<PCDataModel> pc_clients = new ArrayList<>();
        DatabaseHelper myDb = new DatabaseHelper(context);
        Cursor res = myDb.getAllData(DatabaseHelper.PC_TABLE);
        if (res.getCount() == 0) {
            pc_clients.clear();
            return pc_clients;
        }

        while (res.moveToNext()) {
            String cName = res.getString(1);
            String cPhone = res.getString(2);
            String cLocation = res.getString(3);
            String cService = res.getString(4);
            String cDate = res.getString(5);
            String cTime = res.getString(6);

            PCDataModel client = new PCDataModel();

            client.setName(cName);
            client.setPhone(cPhone);
            client.setLocation(cLocation);
            client.setService(cService);
            client.setDate(cDate);
            client.setTime(cTime);

            pc_clients.add(client);
        }

        return pc_clients;
    }

    public static HashMap<String, List<String>> getData(Context context) {

        DatabaseHelper myDb = new DatabaseHelper(context);
        Cursor res = myDb.getAllData(DatabaseHelper.PC_TABLE);

        if (res.getCount() == 0){
       //     showMessage("Failed", "No data available");
            PotentialClient.put(null, null);
            return PotentialClient;
        }

        while (res.moveToNext()) {

            String cName = res.getString(1);
            String cPhone = res.getString(2);
            String cLocation = res.getString(3);
            String cDate = res.getString(4);
            String cTime = res.getString(5);
            String cService = res.getString(6);

            List<String> ClientDetails = new ArrayList<>();

            ClientDetails.clear();
            ClientDetails.add(cPhone);
            ClientDetails.add(cLocation);
            ClientDetails.add(cDate);
            ClientDetails.add(cTime);
            ClientDetails.add(cService);


            PotentialClient.put(cName, ClientDetails);
        }

        return PotentialClient;
    }

    public static void clear(){
        PotentialClient.clear();
    }

//    private static void showMessage(String title, String message){
//        android.support.v7.app.AlertDialog.Builder builder = new AlertDialog.Builder(context);
//        builder.create();
//        builder.setCancelable(true);
//        builder.setTitle(title);
//        builder.setMessage(message);
//        builder.show();
//    }


}
