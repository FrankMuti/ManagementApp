package com.example.blacksky;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.database.Cursor;
import android.database.DatabaseErrorHandler;
import android.os.Build;
import android.support.design.button.MaterialButton;
import android.support.design.widget.TextInputEditText;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatCheckBox;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.blacksky.databases.DatabaseHelper;
import com.example.blacksky.datamodels.PCDataModel;
import com.example.blacksky.datastructures.PotentialClientsData;
import com.example.blacksky.properties.Properties;

import org.jetbrains.annotations.Contract;
import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class NewClient extends AppCompatActivity{

    DatabaseHelper myDb;
    List<PCDataModel> pc_client;

    static int TAG = 1;

    Toolbar toolbar;

    // Form Items
    Spinner ncSpinner;
    TextInputEditText ncName;
    TextInputEditText ncPhone;
    TextInputEditText ncLocation;
    MaterialButton ncDatePicker;
    MaterialButton ncTimePicker;
    TextView ncDateText;
    TextView ncTimeText;
    AppCompatCheckBox checkConfirmed;
    LinearLayout llConfirmed;
    TextInputEditText ncAgreedAmount;
    TextInputEditText ncDepositAmount;
    TextView ncBalance;

    private String cName;
    private String cPhone;
    private String cLocation;
    private String cDate;
    private String cTime;
    private String cService;
    private String cAgreedAmount;
    private String cDepositAmount;
    private String cBalance;

    String[] time = {"PM", "AM"};
    String[] months = {"JANUARY", "FEBRUARY", "MARCH", "APRIL", "MAY", "JUNE", "JULY", "AUGUST", "SEPTEMBER", "OCTOBER", "NOVEMBER", "DECEMBER"};
    String[] services = {"NONE","WEDDING PHOTOGRAPHY", "WEDDING VIDEOGRAPHY", "WEDDING PHOTOGRAPHY & VIDEOGRAPHY", "FAMILY PHOTOSHOOT", "BIRTHDAY SHOOT",
            "STUDIO SHOOT", "COUPLE SHOOT", "FASHION SHOOT", "PRODUCT PHOTOGRAPHY", "GRADUATION SHOOT", "BABY SHOWERS", "BRIDAL SHOWERS",
            "BRIDAL SHOWERS", "ENGAGEMENT SHOOT", "CORP AND COMMERCIAL COVERAGE", "INTERIOR/ REAL ESTATE PHOTOGRAPHY"};

    String title = "NULL";
    String search_phone = "";

    String name;
    String phone;
    String location;
    String date;
    String time_et;
    String service;
    String agreed;
    String deposit;
    String balance;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_client);

        toolbar = findViewById(R.id.ncToolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitleTextColor(getResources().getColor(R.color.colorAccent));

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(ContextCompat.getColor(getApplicationContext(), R.color.colorAccentBlue));
        }
        // add back arrow to toolbar
        if (getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        initialize();

        try {
            title = getIntent().getStringExtra("title");
            name = getIntent().getStringExtra("name");
            phone =  getIntent().getStringExtra("phone");
            location = getIntent().getStringExtra("location");
            date = getIntent().getStringExtra("date");
            time_et = getIntent().getStringExtra("time");
            service = getIntent().getStringExtra("service");

            try{
                agreed = getIntent().getStringExtra("agreed");
                deposit = getIntent().getStringExtra("deposit");
                balance = getIntent().getStringExtra("balance");
            }catch (Exception e){
                Toast.makeText(this, "Not Found", Toast.LENGTH_SHORT).show();
            }

        }catch (Exception e) {
//            e.printStackTrace();
            Toast.makeText(this, "No Title", Toast.LENGTH_SHORT).show();
            title = "NULL";
        }

        if (title == "NULL" || title == null) {
            title = "NULL";
        }
//        search_phone = getIntent().getStringExtra("phone");
        if (title == null || title.equals("NULL")) {
            newClientView();
            Toast.makeText(this, "New Client", Toast.LENGTH_SHORT).show();
            TAG = 1;
        }else if (title.equals(Properties.EDIT_CLIENT) || title.equals(Properties.CONFIRMED_CLIENT)) {
            editClientView();
            Toast.makeText(this, title, Toast.LENGTH_SHORT).show();
            TAG = 0;
        } else if (title.equals(Properties.ATTENDED_CLIENTS)) {
            editClientView();
            Toast.makeText(this, title, Toast.LENGTH_SHORT).show();
            TAG = 3;
        }
        else {
            newClientView();
            Toast.makeText(this, "New Client", Toast.LENGTH_SHORT).show();
            TAG = 1;
        }
    }

    private void editClientView(){
        toolbar.setTitle(title);
        myDb = new DatabaseHelper(this);
        if (title.equals(Properties.CONFIRMED_CLIENT)){
            checkConfirmed.setChecked(true);
            check();
            ncAgreedAmount.setText(agreed);
            ncDepositAmount.setText(deposit);

            ncBalance.setText(String.valueOf(Integer.parseInt(agreed) - Integer.parseInt(deposit)));
        } else if (title.equals(Properties.ATTENDED_CLIENTS)){
            checkConfirmed.setChecked(true);
            check();
            ncAgreedAmount.setText(agreed);
            ncDepositAmount.setText(deposit);
            ncDateText.setVisibility(View.GONE);
            ncDatePicker.setVisibility(View.GONE);
            ncTimeText.setVisibility(View.GONE);
            ncLocation.setVisibility(View.GONE);
            checkConfirmed.setVisibility(View.GONE);
            ncTimePicker.setVisibility(View.GONE);
        }

        ncName.setText(name);
        ncPhone.setText(phone);
        ncLocation.setText(location);
        ncDateText.setText(date);
        ncTimeText.setText(time_et);
        ncSpinner.setSelection(setSpinner(service));

    }

    @Contract(pure = true)
    private int setSpinner(String service) {
        for (int i = 0; i < services.length; i++){
            if (service.equals(services[i])){
                return i;
            }
        }
        return 0;
    }

    private void newClientView() {
        toolbar.setTitle("New Client");
        myDb = new DatabaseHelper(this);
    }

    private boolean checkIfExists(String table) {
        Cursor res = myDb.getData(phone, table);
        if (res.getCount() >= 1){
            deleteUser(table);
        }
        return true;
    }

    private void deleteUser(String table){
        myDb.deleteDataByInteger(getClientID(table), table);
    }

    private String getClientID(String table){
        Cursor res = myDb.getData(phone, table);
        String data = null;
        if (res.moveToNext())
            data =  res.getString(0);
        return data;
    }

    private boolean saveClient(){
        checkWhichDBUpdated();
        if (checkConfirmed.isChecked()){
            return savedClient();
        }else {
            return savedPotentialClient();
        }
    }

    private boolean savedPotentialClient() {
        cName = getString(ncName);
        cPhone = getString(ncPhone);
        cLocation = getString(ncLocation);
        cDate = getString(ncDateText);
        cTime = getString(ncTimeText);
        cService = ncSpinner.getSelectedItem().toString();

        if (checkTextInput(cName, ncName) && checkTextInput(cPhone, ncPhone) && checkTextInput(cLocation, ncLocation)
                && checkTextInput(cDate, ncDateText) && checkTextInput(cTime, ncTimeText)){
            if (TextUtils.isEmpty(cService) || "None".equals(cService)) {
                Toast.makeText(getApplicationContext(), "Select Service", Toast.LENGTH_SHORT).show();
                return false;
            }
            if (myDb.insertPCData(cName, cPhone, cLocation, cService, cDate, cTime)){
                Toast.makeText(getApplicationContext(), "Client Added", Toast.LENGTH_SHORT).show();
                clearAll();
            }else{
                Toast.makeText(getApplicationContext(), "Failed To add", Toast.LENGTH_SHORT).show();
                return false;
            }
        }

        return true;

    }

    private boolean savedClient() {
        cName = getString(ncName);
        cPhone = getString(ncPhone);
        cLocation = getString(ncLocation);
        cDate = getString(ncDateText);
        cTime = getString(ncTimeText);
        cService = ncSpinner.getSelectedItem().toString();
        cAgreedAmount = getString(ncAgreedAmount);
        cDepositAmount = getString(ncDepositAmount);

        if (title.equals(Properties.ATTENDED_CLIENTS)) {

            if (checkIfExists(DatabaseHelper.AC_TABLE))
                myDb.deleteData(cPhone, DatabaseHelper.AC_TABLE);
            if (myDb.insertACData(cName, cPhone, cService, cAgreedAmount, cDepositAmount)) {
                Toast.makeText(getApplicationContext(), "Client Updated", Toast.LENGTH_SHORT).show();
                clearAll();
            } else {
                Toast.makeText(getApplicationContext(), "Failed To add", Toast.LENGTH_SHORT).show();
                return false;
            }
            return true;
        }

        if (checkTextInput(cName, ncName) && checkTextInput(cPhone, ncPhone) && checkTextInput(cLocation, ncLocation)
                && checkTextInput(cDate, ncDateText) && checkTextInput(cTime, ncTimeText)
                && checkTextInput(cAgreedAmount, ncAgreedAmount)
                && checkTextInput(cDepositAmount, ncDepositAmount)){
            if (TextUtils.isEmpty(cService) || "NONE".equalsIgnoreCase(cService)) {
                Toast.makeText(getApplicationContext(), "Select Service", Toast.LENGTH_SHORT).show();
                return false;
            }

            if (title.equals(Properties.EDIT_CLIENT)) {
                if (myDb.insertCCData(cName, cPhone, cLocation, cService, cDate, cTime, cAgreedAmount, cDepositAmount)) {
                    Toast.makeText(getApplicationContext(), "Client Added", Toast.LENGTH_SHORT).show();
                   // myDb.deleteData(cPhone, DatabaseHelper.PC_TABLE);
                    clearAll();
                } else {
                    Toast.makeText(getApplicationContext(), "Failed To add", Toast.LENGTH_SHORT).show();
                    return false;
                }
                return true;
            }

            if (title.equals(Properties.CONFIRMED_CLIENT)) {
                if (myDb.insertCCData(cName, cPhone, cLocation, cService, cDate, cTime, cAgreedAmount, cDepositAmount)) {
                    Toast.makeText(getApplicationContext(), "Client Added", Toast.LENGTH_SHORT).show();
                    clearAll();
                } else {
                    Toast.makeText(getApplicationContext(), "Failed To add", Toast.LENGTH_SHORT).show();
                    return false;
                }
                return true;
            }
            // New Client
            if (myDb.insertCCData(cName, cPhone, cLocation, cService, cDate, cTime, cAgreedAmount, cDepositAmount)) {
                Toast.makeText(getApplicationContext(), "Client Added", Toast.LENGTH_SHORT).show();

                clearAll();
            } else {
                Toast.makeText(getApplicationContext(), "Failed To add", Toast.LENGTH_SHORT).show();
                return false;
            }
            return true;

        }

        return false;
    }

    private void initialize(){
        ncSpinner = findViewById(R.id.ncServiceSpinner);
        ncName = findViewById(R.id.ncNameEt);
        ncPhone = findViewById(R.id.ncPhoneEt);
        ncLocation = findViewById(R.id.ncLocationEt);
        ncDatePicker = findViewById(R.id.ncDatePicker);
        ncTimePicker = findViewById(R.id.ncTimePicker);
        ncDateText = findViewById(R.id.ncDateTxt);
        ncTimeText = findViewById(R.id.ncTimeTxt);
        checkConfirmed = findViewById(R.id.checkConfirmed);
        llConfirmed = findViewById(R.id.ll_confirmed);
        ncAgreedAmount = findViewById(R.id.ncAgreedAmount);
        ncDepositAmount = findViewById(R.id.ncDepositAmount);
        checkConfirmed.setChecked(false);
        ncBalance = findViewById(R.id.ncBalance);
        check();
        setServiceSpinnerList();
        setSpinner();
        setupCheckBoxes();

        ncDatePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                datePicker(ncDateText);
            }
        });

        ncTimePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                timePicker(ncTimeText);
            }
        });
    }

    private void setupCheckBoxes() {
        checkConfirmed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                check();
            }
        });
    }

    private void check() {
        if (!checkConfirmed.isChecked()){
            ncAgreedAmount.setEnabled(false);
            ncDepositAmount.setEnabled(false);
            llConfirmed.setVisibility(View.INVISIBLE);
        }else {
            ncAgreedAmount.setEnabled(true);
            ncDepositAmount.setEnabled(true);
            llConfirmed.setVisibility(View.VISIBLE);
        }
    }

    private void setSpinner() {
        initSpinner();
        ncSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                cService = parent.getSelectedItem().toString();
             //   Toast.makeText(getApplicationContext(), parent.getSelectedItem().toString(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                cService = "NONE";
            }
        });
    }

    private void setServiceSpinnerList() {
        List<String> categories = new ArrayList<>();
        Collections.addAll(categories, services);

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_item, categories);
//        {
//          //  @androidx.annotation.NonNull
//            @Override
//            public View getView(int position, View convertView,ViewGroup parent) {
//                View v = super.getView(position, convertView, parent);
//                ((TextView) v).setTextSize(14);
//                ((TextView) v).setTextColor(
//                       getResources().getColor(R.color.colorAccentBlue)
//                );
//                return v;
//            }
//
//            @Override
//            public View getDropDownView(int position,View convertView, ViewGroup parent) {
//                View v = super.getDropDownView(position, convertView, parent);
//               // v.setBackgroundResource(android.R.drawable.spinner_dropdown_background);
//                ((TextView) v).setTextColor(
//                        getResources().getColor(R.color.darkText)
//                );
//
//              //  ((TextView) v).setTypeface(getResources().getFont(R.font.roboto_light));
//                ((TextView) v).setGravity(Gravity.LEFT);
//                return v;
//            }
//        };
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        ncSpinner.setAdapter(dataAdapter);
    }

    private void initSpinner() {

    }

    int mYear, mMonth, mDay;
    private void datePicker(final TextView tx) {
        //Toast.makeText(getActivity(), "Clicked ", Toast.LENGTH_LONG).show();
        final Calendar c = Calendar.getInstance();
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(NewClient.this, new DatePickerDialog.OnDateSetListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                tx.setText(dayOfMonth + "-" + months[month] + "-" + year);
            }
        }, mYear, mMonth, mDay);
        datePickerDialog.show();
    }

    int mHour;
    int mMinute;
    private void timePicker(final TextView tx) {
        //Toast.makeText(getActivity(), "Clicked ", Toast.LENGTH_LONG).show();
        final Calendar c = Calendar.getInstance();
        mHour = c.get(Calendar.HOUR_OF_DAY);
        mMinute = c.get(Calendar.MINUTE);
        TimePickerDialog timePickerDialog = new TimePickerDialog(NewClient.this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                String tt = hourOfDay >= 12 ? time[0] : time[1];

                String hr = String.valueOf(hourOfDay);
                if (hr.length() == 1) {
                    hr = "0" + hr;
                }

                String min = String.valueOf(minute);
                if (min.length() == 1){
                    min = "0" + min;
                }

                String time = String.valueOf(hourOfDay) + ":" + min + " " + tt;
                tx.setText(time);
            }
        }, mHour, mMinute, true);
        timePickerDialog.show();
    }

    @SuppressLint("SetTextI18n")
    private void clearAll() {
        ncName.setText("");
        ncPhone.setText("");
        ncDateText.setText("Pick Date");
        ncTimeText.setText("Pick Time");
        ncLocation.setText("");
        cName = cPhone = cDate = cTime = cLocation = cAgreedAmount = cDepositAmount = "";
    }

    private boolean checkTextInput(String data, TextInputEditText editText){
        if (TextUtils.isEmpty(data)){
            editText.setError("Fill this");
            return false;
        }
        return true;
    }

    private boolean checkTextInput(String data, TextView textView){
        if (TextUtils.isEmpty(data) || "Pick Date".equals(textView.getText().toString()) || "Pick Time".equals(textView.getText().toString())){
            textView.setError("Fill this");
            return false;
        }
        return true;
    }

    private void checkWhichDBUpdated(){
        if (checkIfExists(DatabaseHelper.CC_TABLE))
            Toast.makeText(this, "CC TABLE", Toast.LENGTH_SHORT).show();

        if (checkIfExists(DatabaseHelper.PC_TABLE))
            Toast.makeText(this, "CC TABLE", Toast.LENGTH_SHORT).show();

        if (checkIfExists(DatabaseHelper.AC_TABLE))
            Toast.makeText(this, "CC TABLE", Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home){
            finish();
            startActivity(new Intent(this, DashboardActivity.class));
        }else if (item.getItemId() == R.id.action_add_new_client){
            if (saveClient()){
                Toast.makeText(this, "Saved", Toast.LENGTH_SHORT).show();
                finish();
                if (title.equals(Properties.POTENTIAL_CLIENT))
                    startActivity(new Intent(this, Clients.class));
                else if (title.equals(Properties.CONFIRMED_CLIENT))
                    startActivity(new Intent(this, DashboardActivity.class));
                else if (title.equals(Properties.ATTENDED_CLIENTS))
                    startActivity(new Intent(this, AttendedClients.class));
                else
                    startActivity(new Intent(this, Clients.class));
            }else {
                Toast.makeText(this, "Failed", Toast.LENGTH_SHORT).show();
            }
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_add_new_client, menu);
        return true;
    }

    private String getString(TextView tx) {
        return  tx.getText().toString();
    }

    private String getString(TextInputEditText tx) {
        return  Objects.requireNonNull(tx.getText()).toString();
    }
}





// Software Engineering.... Pearson Ian Sommerville