package com.example.blacksky;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;
import android.support.design.button.MaterialButton;
import android.support.design.widget.TextInputEditText;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import com.example.blacksky.databases.DatabaseHelper;
import com.example.blacksky.properties.Properties;

import java.util.Calendar;
import java.util.Objects;

public class NewServiceProvider extends AppCompatActivity{

    DatabaseHelper myDb;

    static int classtag = 1;

    Toolbar toolbar;

    TextInputEditText nsName;
    TextInputEditText nsPhone;
    TextInputEditText nsService;
    MaterialButton nsDatePicker;
    TextView nsDateText;
    TextInputEditText nsAgreedAmount;
    TextInputEditText nsDepositAmount;
    TextView nsBalance;

    private String sName;
    private String sPhone;
    private String sService;
    private String sDate;
    private String sAgreedAmount;
    private String sDepositAmount;

    String[] time = {"PM", "AM"};
    String[] months = {"JANUARY", "FEBRUARY", "MARCH", "APRIL", "MAY", "JUNE", "JULY", "AUGUST", "SEPTEMBER", "OCTOBER", "NOVEMBER", "DECEMBER"};
    String title = "";

    String name;
    String phone;
    String date;
    String service;
    String agreed;
    String deposit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_service_provider);

        toolbar = findViewById(R.id.nsToolbar);
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
            service = getIntent().getStringExtra("service");
            date = getIntent().getStringExtra("lockedDate");
            agreed = getIntent().getStringExtra("agreedAmount");
            deposit = getIntent().getStringExtra("deposit");

        }catch (Exception e) {
            title = null;
        }
        if (title == null) {
            newSPView();
            Toast.makeText(this, "New Service Provider", Toast.LENGTH_SHORT).show();
            classtag = 1;
        }else if (title.equals(Properties.EDIT_SP)) {
            editSPView();
           // toolbar.setTitle(Properties.EDIT_SP);
            Toast.makeText(this, title, Toast.LENGTH_SHORT).show();
            classtag = 0;
        }
        else {
            newSPView();
            Toast.makeText(this, "New Service Provider", Toast.LENGTH_SHORT).show();
            classtag = 1;
        }
    }

    private void editSPView(){
        toolbar.setTitle(title);
        myDb = new DatabaseHelper(this);

        nsName.setText(name);
        nsPhone.setText(phone);
        nsService.setText(service);
        nsDateText.setText(date);
        nsAgreedAmount.setText(agreed);
        nsDepositAmount.setText(deposit);



        nsBalance.setText(String.valueOf(Integer.parseInt(agreed) - Integer.parseInt(deposit)));
    }


    private void newSPView() {
        toolbar.setTitle("New Service Provider");
        myDb = new DatabaseHelper(this);
    }

    private boolean checkIfExists() {
        Cursor res = myDb.getData(phone, DatabaseHelper.SPP_TABLE);
        if (res.getCount() >= 1){
            deleteUser();
        }
        return true;
    }

    private void deleteUser(){
        myDb.deleteDataByInteger(getClientID(), DatabaseHelper.SPP_TABLE);
    }

    private String getClientID(){
        Cursor res = myDb.getData(phone, DatabaseHelper.SPP_TABLE);
        String data = null;
        if (res.moveToNext())
            data =  res.getString(0);
        return data;
    }

    private boolean saveClient(){
        checkWhichDBUpdated();
        if (classtag == 1){
            return savedClient();
        }else {
            return savedPotentialClient();
        }
    }

    private boolean savedPotentialClient() {
        sName = getString(nsName);
        sPhone = getString(nsPhone);
        sService = getString(nsService);
        sDate = getString(nsDateText);
        sAgreedAmount = getString(nsAgreedAmount);
        sDepositAmount = getString(nsDepositAmount);

        if (checkTextInput(sName, nsName) && checkTextInput(sPhone, nsPhone) && checkTextInput(sService, nsService)
                && checkTextInput(sDate, nsDateText) && checkTextInput(sService, nsService)){
            if (myDb.insertSPPData(sName, sPhone, sService, sDate, sAgreedAmount, sDepositAmount)){
                Toast.makeText(getApplicationContext(), "Service Provider Added", Toast.LENGTH_SHORT).show();
                clearAll();
            }else{
                Toast.makeText(getApplicationContext(), "Failed To add", Toast.LENGTH_SHORT).show();
                return false;
            }
        }
        return true;
    }

    private boolean savedClient() {
        sName = getString(nsName);
        sPhone = getString(nsPhone);
        sService = getString(nsService);
        sDate = getString(nsDateText);
        sAgreedAmount = getString(nsAgreedAmount);
        sDepositAmount = getString(nsDepositAmount);

        if (checkTextInput(sName, nsName) && checkTextInput(sPhone, nsPhone) && checkTextInput(sService, nsService)
                && checkTextInput(sDate, nsDateText) && checkTextInput(sService, nsService)){
            if (myDb.insertSPPData(sName, sPhone, sService, sDate, sAgreedAmount, sDepositAmount)){
                Toast.makeText(getApplicationContext(), "Service Provider Added", Toast.LENGTH_SHORT).show();
                clearAll();
            }else{
                Toast.makeText(getApplicationContext(), "Failed To add", Toast.LENGTH_SHORT).show();
                return false;
            }
        }

        return true;
    }

    private void initialize(){
        nsName = findViewById(R.id.nsNameEt);
        nsPhone = findViewById(R.id.nsPhoneEt);
        nsService = findViewById(R.id.nsServiceEt);
        nsDatePicker = findViewById(R.id.nsDatePicker);
        nsDateText = findViewById(R.id.nsDateTxt);
        nsAgreedAmount = findViewById(R.id.nsAgreedAmount);
        nsDepositAmount = findViewById(R.id.nsDepositAmount);
        nsBalance = findViewById(R.id.nsBalance);
        nsDatePicker.setOnClickListener(v -> datePicker(nsDateText));


    }

    int mYear, mMonth, mDay;
    private void datePicker(final TextView tx) {
        //Toast.makeText(getActivity(), "Clicked ", Toast.LENGTH_LONG).show();
        final Calendar c = Calendar.getInstance();
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(NewServiceProvider.this, new DatePickerDialog.OnDateSetListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                tx.setText(dayOfMonth + "-" + months[month] + "-" + year);
            }
        }, mYear, mMonth, mDay);
        datePickerDialog.show();
    }

    @SuppressLint("SetTextI18n")
    private void clearAll() {
        nsName.setText("");
        nsPhone.setText("");
        nsDateText.setText("Pick Date");
        nsService.setText("");
        nsDepositAmount.setText("");
        nsAgreedAmount.setText("");
        sName = sPhone = sDate = sService = sAgreedAmount = sDepositAmount = "";
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
        checkIfExists();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home){
            finish();
        }else if (item.getItemId() == R.id.action_add_new_client){
            if (saveClient()){
                Toast.makeText(this, "Saved", Toast.LENGTH_SHORT).show();
                finish();
                startActivity(new Intent(this, ServiceProvider.class));
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
