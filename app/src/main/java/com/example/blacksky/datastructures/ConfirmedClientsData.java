package com.example.blacksky.datastructures;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;

import com.example.blacksky.databases.DatabaseHelper;
import com.example.blacksky.datamodels.CCDataModel;
import com.example.blacksky.datamodels.DSDataModel;
import com.example.blacksky.properties.Properties;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class ConfirmedClientsData {

   public static HashMap<String, List<String>> ConfirmedClient = new HashMap<>();

  //  public static

    public static List<CCDataModel> getCC_clients(Context context){
        DatabaseHelper myDb = new DatabaseHelper(context);
        Cursor res = myDb.getAllData(DatabaseHelper.CC_TABLE);
        List<CCDataModel> cc_clients = new ArrayList<>();
        if (res.getCount() == 0){
            cc_clients.clear();
            return cc_clients;
        }

        while (res.moveToNext()) {
            String cName = res.getString(1);
            String cPhone = res.getString(2);
            String cLocation = res.getString(3);
            String cService = res.getString(4);
            String cDate = res.getString(5);
            String cTime = res.getString(6);
            String cAgreed = res.getString(7);
            String cDeposit = res.getString(8);
            String cBalance = res.getString(9);

            CCDataModel event = new CCDataModel();

            event.setName(cName);
            event.setPhone(cPhone);
            event.setLocation(cLocation);
            event.setService(cService);
            event.setDate(cDate);
            event.setTime(cTime);
            event.setAgreedAmount(Integer.parseInt(cAgreed));
            event.setDepositAmount(Integer.parseInt(cDeposit));

            cc_clients.add(event);
        }

        return cc_clients;
    }

    public static List<DSDataModel> getUp_events(Context context){
        DatabaseHelper myDb = new DatabaseHelper(context);
        Cursor res = myDb.getAllData(DatabaseHelper.CC_TABLE);
        List<DSDataModel> up_events = new ArrayList<>();
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
            String cTime = res.getString(6);
            String cAgreed = res.getString(7);
            String cDeposit = res.getString(8);
            DSDataModel event = new DSDataModel();

            event.setName(cName);
            event.setPhone(cPhone);
            event.setLocation(cLocation);
            event.setService(cService);
            event.setDate(cDate);
            event.setTime(cTime);
            event.setAgreedAmount(cAgreed);
            event.setDeposit(cDeposit);

            up_events.add(event);
        }

        return sortDate(up_events);
    }


    private static List<DSDataModel> sortDate(List<DSDataModel> upcoming) {
        List<DSDataModel> newUpComing = upcoming;
        List<Long> longDates = new ArrayList<>();
        for (int i = 0; i < upcoming.size(); i++) {
            longDates.add(dateConverter(upcoming.get(i).getDate()));
        }

        long temp = 0L;
        DSDataModel tempModel = null;

        for (int i = 0; i < upcoming.size(); i++) {
            for (int j = 0; j < upcoming.size()-1; j++) {
                if (longDates.get(j+1) < longDates.get(j)) {
                    temp = longDates.get(j+1);
                    tempModel = upcoming.get(j+1);

                    longDates.set(j+1, longDates.get(j));
                    upcoming.set(j+1, upcoming.get(j));

                    longDates.set(j, temp);
                    upcoming.set(j, tempModel);
                }
            }
        }

        return upcoming;
    }

    public static List<Long> getDates(List<DSDataModel> upcoming) {
        List<Long> longDates = new ArrayList<>();

        for (int i = 0; i < upcoming.size(); i++) {
            longDates.add(dateConverter(upcoming.get(i).getDate()));
        }

        return longDates;
    }


    private static String changeDate(String date) {
        String[] DT = date.split("-");

        for (int i = 0; i < Properties.MONTHS.length; i++) {
            if (DT[1].equals(Properties.MONTHS[i])) {
                DT[1] = String.valueOf(i+1);
            }
        }

        return DT[0] + "-" + DT[1] + "-" + DT[2];
    }

    private static long dateConverter(String date) {
        @SuppressLint("SimpleDateFormat")
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        Date dt = null;
        try {
            dt = sdf.parse(changeDate(date));
          //  assert dt != null;
            return dt.getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return 0L;
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

