package com.example.blacksky.fragments;


import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.design.button.MaterialButton;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.blacksky.R;
import com.example.blacksky.databases.DatabaseHelper;
import com.example.blacksky.datastructures.PotentialClientsData;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class New_Clients extends Fragment implements AdapterView.OnItemSelectedListener {

    DatabaseHelper myDb;

//    Spinner           ncServiceSpinner;
//    TextInputEditText ncNameEdit;
//    TextInputEditText ncPhoneEdit;
//    TextInputEditText ncLocationEdit;
//    MaterialButton    ncDatePickerBtn;
//    TextView          ncDateTxt;
//    MaterialButton    ncTimePickerBtn;
//    TextView          ncTimeTxt;
//    MaterialButton    ncAddPotentialClientBtn;

    TextInputEditText nsServiceEdit;
    TextInputEditText nsNameEdit;
    TextInputEditText nsPhoneEdit;
    TextInputEditText nsAgreedEdit;
    TextInputEditText nsDepositEdit;
    MaterialButton    nsDateBtn;
    TextView nsDateTxt;
    MaterialButton nsAddServiceProvider;

//    private String cName;
//    private String cPhone;
//    private String cLocation;
//    private String cDate;
//    private String cTime;
//    private String cService;

    private String nsService;
    private String nsName;
    private String nsPhone;
    private String nsAgreedAmount;
    private String nsDeposit;
    private String nsDate;

    private int mYear, mMonth, mDay, mHour, mMinute;

    String time[] = {"PM", "AM"};
    String months[] = {"JANUARY", "FEBRUARY", "MARCH", "APRIL", "MAY", "JUNE", "JULY", "AUGUST", "SEPTEMBER", "OCTOBER", "NOVEMBER", "DECEMBER"};
    String services[] = {"NONE","WEDDING PHOTOGRAPHY", "WEDDING VIDEOGRAPHY", "WEDDING PHOTOGRAPHY & VIDEOGRAPHY", "FAMILY PHOTOSHOOT", "BIRTHDAY SHOOT",
                            "STUDIO SHOOT", "COUPLE SHOOT", "FASHION SHOOT", "PRODUCT PHOTOGRAPHY", "GRADUATION SHOOT", "BABY SHOWERS", "BRIDAL SHOWERS",
                            "BRIDAL SHOWERS", "ENGAGEMENT SHOOT", "CORP AND COMMERCIAL COVERAGE", "INTERIOR/ REAL ESTATE PHOTOGRAPHY"};

    HashMap<String, List<String>> PotentialClient = new HashMap<>();

    String clientName;


    public New_Clients() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_new_clients, container, false);

//        ncServiceSpinner = view.findViewById(R.id.ncServiceSpinner);
//        ncNameEdit = view.findViewById(R.id.ncNameEt);
//        ncLocationEdit = view.findViewById(R.id.ncLocationEt);
//        ncPhoneEdit = view.findViewById(R.id.ncPhoneEt);
//        ncDatePickerBtn = view.findViewById(R.id.ncDatePicker);
//        ncDateTxt = view.findViewById(R.id.ncDateTxt);
//        ncTimePickerBtn = view.findViewById(R.id.ncTimePicker);
//        ncTimeTxt = view.findViewById(R.id.ncTimeTxt);
//        ncAddPotentialClientBtn = view.findViewById(R.id.ncAddPotentialClientBtn);

        nsServiceEdit = view.findViewById(R.id.nsServiceEt);
        nsNameEdit =  view.findViewById(R.id.nsNameEt);
        nsPhoneEdit = view.findViewById(R.id.nsPhoneEt);
        nsAgreedEdit = view.findViewById(R.id.nsAgreedAmount);
        nsDepositEdit = view.findViewById(R.id.nsDepositAmount);
        nsDateBtn = view.findViewById(R.id.nsDatePicker);
        nsDateTxt = view.findViewById(R.id.nsDateTxt);
        nsAddServiceProvider = view.findViewById(R.id.nsAddServiceBtn);

        myDb = new DatabaseHelper(getContext());


//        ncDatePickerBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                 datePicker(ncDateTxt);
//            }
//        });

        nsDateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                datePicker(nsDateTxt);
            }
        });

//        ncTimePickerBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                timePicker();
//            }
//        });
//
//        ncAddPotentialClientBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                addClient();
//            }
//        });

        nsAddServiceProvider.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addServiceProvider();
            }
        });

//        ncServiceSpinner.setOnItemSelectedListener(this);
//        ncServiceSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                Toast.makeText(getContext(), parent.getSelectedItem().toString(), Toast.LENGTH_SHORT).show();
//                cService = parent.getSelectedItem().toString();
//            }
//            @Override
//            public void onNothingSelected(AdapterView<?> parent) {
//                cService = "None";
//            }
//        });
       // setServiceList();
        // Define views
        return view;
    }

    private void datePicker(final TextView tx) {
        //Toast.makeText(getActivity(), "Clicked ", Toast.LENGTH_LONG).show();
        final Calendar c = Calendar.getInstance();
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                tx.setText(dayOfMonth + " " + months[month] + ", " + year);
            }
        }, mYear, mMonth, mDay);
        datePickerDialog.show();
    }

//    private void timePicker() {
//        //Toast.makeText(getActivity(), "Clicked ", Toast.LENGTH_LONG).show();
//        final Calendar c = Calendar.getInstance();
//        mHour = c.get(Calendar.HOUR_OF_DAY);
//        mMinute = c.get(Calendar.MINUTE);
//        TimePickerDialog timePickerDialog = new TimePickerDialog(getContext(), new TimePickerDialog.OnTimeSetListener() {
//            @Override
//            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
//                String tt = hourOfDay >= 12 ? time[0] : time[1];
//                ncTimeTxt.setText(hourOfDay + ":" + minute + " " + tt);
//            }
//        }, mHour, mMinute, true);
//        timePickerDialog.show();
//    }

//    private void addClient() {
//        cName = ncNameEdit.getText().toString();
//        cPhone = ncPhoneEdit.getText().toString();
//        cLocation = ncLocationEdit.getText().toString();
//        cDate = ncDateTxt.getText().toString();
//        cTime = ncTimeTxt.getText().toString();
//
//        ncServiceSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                Toast.makeText(getContext(), parent.getSelectedItem().toString(), Toast.LENGTH_SHORT).show();
//                cService = parent.getSelectedItem().toString();
//            }
//            @Override
//            public void onNothingSelected(AdapterView<?> parent) {
//                cService = "None";
//            }
//        });
//
//        if (checkTextInput(cName, ncNameEdit) && checkTextInput(cPhone, ncPhoneEdit) && checkTextInput(cLocation, ncLocationEdit)
//                && checkTextInput(cDate, ncDateTxt) && checkTextInput(cTime, ncTimeTxt)){
//
//            if (TextUtils.isEmpty(cService) || "None".equals(cService)) {
//                Toast.makeText(getContext(), "Select Service", Toast.LENGTH_SHORT).show();
//                return;
//            }
//
//            if (myDb.insertPCData(cName, cPhone, cLocation, cService, cDate, cTime)){
//                Toast.makeText(getContext(), "Client Added", Toast.LENGTH_SHORT).show();
//                clearAll();
//            }else{
//                Toast.makeText(getContext(), "Failed To add", Toast.LENGTH_SHORT).show();
//            }
//        }
//    }

    private void addServiceProvider() {
        nsName = getString(nsNameEdit);
        nsPhone = getString(nsPhoneEdit);
        nsAgreedAmount = getString(nsAgreedEdit);
        nsDeposit = getString(nsDepositEdit);
        nsDate = getString(nsDateTxt);
        nsService = getString(nsServiceEdit);

        if (checkTextInput(nsName, nsNameEdit) && checkTextInput(nsPhone, nsPhoneEdit) && checkTextInput(nsService, nsServiceEdit) && checkTextInput(nsAgreedAmount, nsAgreedEdit) &&
                checkTextInput(nsDeposit, nsDepositEdit) && checkTextInput(nsDate, nsDateTxt)){
            if (myDb.insertSPPData(nsName, nsPhone, nsService, nsDate, nsAgreedAmount, nsDeposit)){
                Toast.makeText(getContext(), "Service Provider Added", Toast.LENGTH_SHORT).show();
                //clearAll();
            }else{
                Toast.makeText(getContext(), "Failed To Add", Toast.LENGTH_LONG).show();
            }
        }

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

//    private void clearAll(){
//        ncNameEdit.setText("");
//        ncPhoneEdit.setText("");
//        ncDateTxt.setText("Pick Date");
//        ncTimeTxt.setText("Pick Time");
//        ncLocationEdit.setText("");
//
//        nsNameEdit.setText("");
//        nsPhoneEdit.setText("");
//        nsServiceEdit.setText("");
//        nsAgreedEdit.setText("");
//        nsDepositEdit.setText("");
//        nsDateTxt.setText("Pick Date");
//
//
//        cName = "";
//        cPhone = "";
//        cTime = "";
//        cDate = "";
//        cService = "";
//
//        nsName = "";
//        nsDate = "";
//        nsPhone = "";
//        nsAgreedAmount = "";
//        nsService = "";
//        nsDeposit = "";
//    }

//
//    private void setServiceList() {
//        List<String> categories = new ArrayList<>();
//
//        for (String service : services){
//            categories.add(service);
//        }
//
//        ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, categories);
//        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        ncServiceSpinner.setAdapter(dataAdapter);
//
//    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_new_client, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_new_client){
            Toast.makeText(getActivity(), "Clicked on " + item.getTitle(), Toast.LENGTH_LONG).show();
        }
        return true;
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    private String getString(TextView tx) {
        return  tx.getText().toString();
    }

    private String getString(EditText tx) {
        return  tx.getText().toString();
    }


}
