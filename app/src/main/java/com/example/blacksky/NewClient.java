package com.example.blacksky;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Build;
import android.support.design.button.MaterialButton;
import android.support.design.widget.TextInputEditText;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatCheckBox;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.blacksky.databases.DatabaseHelper;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class NewClient extends AppCompatActivity{

    DatabaseHelper myDb;

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

    private String cName;
    private String cPhone;
    private String cLocation;
    private String cDate;
    private String cTime;
    private String cService;
    private String cAgreedAmoount;
    private String cDepositAmount;

    String[] time = {"PM", "AM"};
    String[] months = {"JANUARY", "FEBRUARY", "MARCH", "APRIL", "MAY", "JUNE", "JULY", "AUGUST", "SEPTEMBER", "OCTOBER", "NOVEMBER", "DECEMBER"};
    String[] services = {"NONE","WEDDING PHOTOGRAPHY", "WEDDING VIDEOGRAPHY", "WEDDING PHOTOGRAPHY & VIDEOGRAPHY", "FAMILY PHOTOSHOOT", "BIRTHDAY SHOOT",
            "STUDIO SHOOT", "COUPLE SHOOT", "FASHION SHOOT", "PRODUCT PHOTOGRAPHY", "GRADUATION SHOOT", "BABY SHOWERS", "BRIDAL SHOWERS",
            "BRIDAL SHOWERS", "ENGAGEMENT SHOOT", "CORP AND COMMERCIAL COVERAGE", "INTERIOR/ REAL ESTATE PHOTOGRAPHY"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_client);

        toolbar = findViewById(R.id.ncToolbar);
        setSupportActionBar(toolbar);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(ContextCompat.getColor(getApplicationContext(), R.color.colorAccentBlue));
        }
        // add back arrow to toolbar
        if (getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        myDb = new DatabaseHelper(getApplicationContext());

        // initialize form layout
        initialize();
        // Setup Form Logic
    }

    private boolean saveClient(){
        if (checkConfirmed.isChecked()){
            savedClient();
        }else {
            savedPotentialClient();
        }

        return true;
    }

    private void savedPotentialClient() {
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
                return;
            }
            if (myDb.insertPCData(cName, cPhone, cLocation, cService, cDate, cTime)){
                Toast.makeText(getApplicationContext(), "Client Added", Toast.LENGTH_SHORT).show();
                clearAll();
            }else{
                Toast.makeText(getApplicationContext(), "Failed To add", Toast.LENGTH_SHORT).show();
            }
        }

    }

    private void savedClient() {
        cName = getString(ncName);
        cPhone = getString(ncPhone);
        cLocation = getString(ncLocation);
        cDate = getString(ncDateText);
        cTime = getString(ncTimeText);
        cService = ncSpinner.getSelectedItem().toString();
        cAgreedAmoount = getString(ncAgreedAmount);
        cDepositAmount = getString(ncDepositAmount);

        if (checkTextInput(cName, ncName) && checkTextInput(cPhone, ncPhone) && checkTextInput(cLocation, ncLocation)
                && checkTextInput(cDate, ncDateText) && checkTextInput(cTime, ncTimeText)
                && checkTextInput(cAgreedAmoount, ncAgreedAmount)
                && checkTextInput(cDepositAmount, ncDepositAmount)){
            if (TextUtils.isEmpty(cService) || "None".equals(cService)) {
                Toast.makeText(getApplicationContext(), "Select Service", Toast.LENGTH_SHORT).show();
                return;
            }
            if (myDb.insertCCData(cName, cPhone, cLocation, cService, cDate, cTime, cAgreedAmoount, cDepositAmount)){
                Toast.makeText(getApplicationContext(), "Client Added", Toast.LENGTH_SHORT).show();
                myDb.deleteData(cName, DatabaseHelper.PC_TABLE);
                clearAll();
            }else{
                Toast.makeText(getApplicationContext(), "Failed To add", Toast.LENGTH_SHORT).show();
            }
        }
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
                Toast.makeText(getApplicationContext(), parent.getSelectedItem().toString(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                cService = "NONE";
            }
        });
    }

    private void setServiceSpinnerList() {
        List<String> categories = new ArrayList<>();
        for (String service : services) {
            ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_spinner_item, categories);
            dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            ncSpinner.setAdapter(dataAdapter);
        }
    }

    private void initSpinner() {
        ncSpinner.setPrompt("Category");
    }

    private void datePicker(final TextView tx) {
        //Toast.makeText(getActivity(), "Clicked ", Toast.LENGTH_LONG).show();
        final Calendar c = Calendar.getInstance();
        int mYear = c.get(Calendar.YEAR);
        int mMonth = c.get(Calendar.MONTH);
        int mDay = c.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(getApplicationContext(), new DatePickerDialog.OnDateSetListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                tx.setText(dayOfMonth + " " + months[month] + ", " + year);
            }
        }, mYear, mMonth, mDay);
        datePickerDialog.show();
    }

    private void timePicker(final TextView tx) {
        //Toast.makeText(getActivity(), "Clicked ", Toast.LENGTH_LONG).show();
        final Calendar c = Calendar.getInstance();
        int mHour = c.get(Calendar.HOUR_OF_DAY);
        int mMinute = c.get(Calendar.MINUTE);
        TimePickerDialog timePickerDialog = new TimePickerDialog(getApplicationContext(), new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                String tt = hourOfDay >= 12 ? time[0] : time[1];
                String time = String.valueOf(hourOfDay) + ":" + String.valueOf(minute) + " " + tt;
                tx.setText(time);
            }
        }, mHour, mMinute, true);
        timePickerDialog.show();
    }

    private void clearAll() {
        ncName.setText("");
        ncPhone.setText("");
        ncDateText.setText("Pick Date");
        ncTimeText.setText("Pick Time");
        ncLocation.setText("");
        cName = cPhone = cDate = cTime = cLocation = cAgreedAmoount = cDepositAmount = "";
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

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home){
            finish();
        }else if (item.getItemId() == R.id.action_add_new_client){
            if (saveClient()){
                Toast.makeText(this, "Saved", Toast.LENGTH_SHORT).show();
                finish();
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
        return  tx.getText().toString();
    }
}





// Software Engineering.... Pearson Ian Sommerville