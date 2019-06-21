package com.example.blacksky.databases;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * @author Stein
 * contact frankmuti373@gmail.com
 */
public class DatabaseHelper extends SQLiteOpenHelper {



    public static final String DATABASE_NAME = "BlackSkyLenses.db";

    // POTENTIAL CLIENTS TABLE
    public final static  String PC_TABLE = "potential_clients";
    public static final String COL_ID_P = "ID";
    public static final String COL_NAME_P  = "NAME";
    public static final String COL_PHONE_P  = "PHONE";
    public static final String COL_LOCATION_P  = "LOCATION";
    public static final String COL_SERVICE_P  = "SERVICE";
    public static final String COL_DATE_P  = "DATE";
    public static final String COL_TIME_P  = "TIME";

    // CONFIRMED CLIENTS TABLE
    public final static  String CC_TABLE = "confirmed_clients";
    public static final String COL_ID_C = "ID";
    public static final String COL_NAME_C  = "NAME";
    public static final String COL_PHONE_C  = "PHONE";
    public static final String COL_LOCATION_C  = "LOCATION";
    public static final String COL_SERVICE_C  = "SERVICE";
    public static final String COL_DATE_C  = "DATE";
    public static final String COL_TIME_C  = "TIME";
    public static final String COL_AGREED_AMOUNT_C = "AGREED_AMOUNT";
    public static final String COL_DEPOSIT_C = "DEPOSIT";
    public static final String COL_BALANCE_C = "BALANCE";

    // SERVICE PROVIDER PAYMENTS TABLE
    public final static  String SPP_TABLE = "service_providers";
    public static final String COL_ID_SP = "ID";
    public static final String COL_NAME_SP  = "NAME";
    public static final String COL_PHONE_SP  = "PHONE";
    public static final String COL_SERVICE_SP  = "SERVICE";
    public static final String COL_LOCKED_DATE_SP  = "LOCKED_DATE";
    public static final String COL_AGREED_AMOUNT_SP = "AGREED_AMOUNT";
    public static final String COL_DEPOSIT_SP = "DEPOSIT";
    public static final String COL_BALANCE_SP = "BALANCE";

    // ATTENDED CLIENTS TABLE
    public static final String AC_TABLE = "added_clients";
    public static final String COL_ID_A = "ID";
    public static final String COL_NAME_A = "NAME";
    public static final String COL_PHONE_A = "PHONE";
    public static final String COL_AMOUNT_A = "AMOUNT";


    // MY SERVICES TABLE
    public final static String SERVICES_TABLE = "services_table";
    public static final String COL_ID_SS = "ID";
    public static final String COL_SERVICES_SS = "SERVICES";

    public DatabaseHelper(Context context){
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS " + PC_TABLE + " (ID INTEGER PRIMARY KEY AUTOINCREMENT," +
                "NAME TEXT, PHONE TEXT, LOCATION TEXT, SERVICE TEXT, DATE TEXT, TIME TEXT)");
        db.execSQL("CREATE TABLE IF NOT EXISTS " + CC_TABLE + " (ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "NAME TEXT, PHONE TEXT, LOCATION TEXT, SERVICE TEXT, DATE TEXT, TIME TEXT, AGREED_AMOUNT INTEGER, DEPOSIT INTEGER, BALANCE INTEGER)");
        db.execSQL("CREATE TABLE IF NOT EXISTS " + SPP_TABLE +" (ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "NAME TEXT, PHONE TEXT, SERVICE TEXT, LOCKED_DATE TEXT ,AGREED_AMOUNT INTEGER, DEPOSIT INTEGER, BALANCE INTEGER)");
        db.execSQL("CREATE TABLE IF NOT EXISTS " + SERVICES_TABLE + " (ID INTEGER PRIMARY KEY AUTOINCREMENT, SERVICE TEXT)");
        db.execSQL("CREATE TABLE IF NOT EXISTS " + AC_TABLE + " (ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "NAME TEXT, PHONE TEXT, AMOUNT INTEGER)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + PC_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + CC_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + SPP_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + SERVICES_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + AC_TABLE);
        onCreate(db);
    }

    public boolean insertPCData(String name, String phone,String location, String service, String date, String time){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COL_NAME_P, name);
        cv.put(COL_PHONE_P, phone);
        cv.put(COL_LOCATION_P, location);
        cv.put(COL_SERVICE_P, service);
        cv.put(COL_DATE_P, date);
        cv.put(COL_TIME_P, time);

        long result = db.insert(PC_TABLE, null, cv);
        return result != -1;
    }

    public boolean insertCCData(String name, String phone, String location, String service, String date, String time, String agreedAmount, String deposit){
        int balance = Integer.parseInt(agreedAmount) - Integer.parseInt(deposit);

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COL_NAME_C, name);
        cv.put(COL_PHONE_C, phone);
        cv.put(COL_LOCATION_C, location);
        cv.put(COL_SERVICE_C, service);
        cv.put(COL_DATE_C, date);
        cv.put(COL_TIME_C, time);
        cv.put(COL_AGREED_AMOUNT_C, agreedAmount);
        cv.put(COL_DEPOSIT_C, deposit);
        cv.put(COL_BALANCE_C, String.valueOf(balance));

        long result = db.insert(CC_TABLE, null, cv);
        return result != -1;
    }

    public boolean insertSPPData(String name, String phone, String service, String locked_date, String agreedAmount, String deposit){
        int balance = Integer.parseInt(agreedAmount) - Integer.parseInt(deposit);

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COL_NAME_SP, name);
        cv.put(COL_PHONE_SP, phone);
        cv.put(COL_SERVICE_SP, service);
        cv.put(COL_LOCKED_DATE_SP, locked_date);
        cv.put(COL_AGREED_AMOUNT_SP, agreedAmount);
        cv.put(COL_DEPOSIT_SP, deposit);
        cv.put(COL_BALANCE_SP, String.valueOf(balance));

        long result = db.insert(SPP_TABLE, null, cv);
        return result != -1;
    }

    public boolean insertACData(String name, String phone, String amount) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COL_NAME_A, name);
        cv.put(COL_PHONE_A, phone);
        cv.put(COL_AMOUNT_A, amount);
        long result = db.insert(AC_TABLE, null, cv);
        return result != -1;
    }

    public boolean insertServices(String service){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COL_SERVICES_SS, service);

        long result = db.insert(SERVICES_TABLE, null, cv);
        return result != -1;
    }

    public Cursor getData(String phone, String table){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT * FROM " + table + " WHERE PHONE = '" + phone + "'";
        return db.rawQuery(query, null);
    }

    public Cursor getAllData(String table){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT * FROM " + table ;
        return db.rawQuery(query, null);
    }

    public Integer deleteData(String name, String table){
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(table, "NAME = ?", new String[] {name});
    }

}































