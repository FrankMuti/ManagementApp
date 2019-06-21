package com.example.blacksky.fragments;


import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.example.blacksky.R;
import com.example.blacksky.databases.DatabaseHelper;
import com.example.blacksky.datastructures.AttendedClientsData;
import com.example.blacksky.datastructures.ConfirmedClientsData;
import com.example.blacksky.datastructures.ExpenseData;
import com.example.blacksky.datastructures.PotentialClientsData;
import com.example.blacksky.properties.Properties;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class Dashboard extends Fragment {

    TextView dsDueTime;
    TextView dsServiceAndClient;
    TextView dsDate;
    TextView dsTime;
    TextView dsLocation;
    TextView dsPhone;

    Button dsAttended;
    Button dsReschedule;

    HashMap<String, List<String>> listChild;
    List<String> listHeader;
    ListAdapter listAdapter;

    String name;
    String cPhone;
    String cLocation;
    String cDate;
    String cTime;
    String cService;
    String cAmount;

    public Dashboard() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_dashboard, container, false);
        dsDueTime = view.findViewById(R.id.dsTimeDue);
        dsServiceAndClient = view.findViewById(R.id.dsServiceAndClient);
        dsDate = view.findViewById(R.id.dsClientDate);
        dsTime = view.findViewById(R.id.dsClientTime);
        dsLocation = view.findViewById(R.id.dsClientLocation);
        dsPhone = view.findViewById(R.id.dsClientPhone);

        dsAttended = view.findViewById(R.id.dsAttended);
       // dsReschedule = view.findViewById(R.id.dsReschedule);

        listChild = ConfirmedClientsData.getData(getContext());
        listHeader = new ArrayList<>(listChild.keySet());

        setViews();

        dsAttended.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    attended();
                }catch (Exception e){
                    Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                }

            }
        });

//        dsReschedule.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//            }
//        });


        return view;
    }

    private void setViews(){
//        for (int i = 0; i < listHeader.size(); i++){
//            names[i] = listHeader.get(i);
//        }
        name = listHeader.get(0);
        if (null == name){
            dsServiceAndClient.setText("No Available Clients");
            dsDate.setText("");
            dsLocation.setText("");
            dsTime.setText("");
            dsPhone.setText("");
            return;
        }
        cPhone = listChild.get(name).get(0);
        cLocation = listChild.get(name).get(1);
        cDate = listChild.get(name).get(2);
        cTime = listChild.get(name).get(3);
        cService = listChild.get(name).get(4);
        cAmount = listChild.get(name).get(5);

        dsServiceAndClient.setText(cService + " at " + name);
        dsDate.setText(cDate);
        dsLocation.setText(cLocation);
        dsTime.setText(cTime);
        dsPhone.setText(cPhone);
    }

    private void attended() {
        DatabaseHelper myDb = new DatabaseHelper(getContext());

        boolean add = myDb.insertACData(name, cPhone, filterAmount(cAmount));
        if (add){
            Toast.makeText(getContext(), "Attended", Toast.LENGTH_SHORT).show();
            myDb.deleteData(name, DatabaseHelper.CC_TABLE);
            Toast.makeText(getContext(), "Done", Toast.LENGTH_SHORT).show();
            AttendedClientsData.getData(getContext());
            ConfirmedClientsData.getData(getContext());
            ExpenseData.getData(getContext());
            PotentialClientsData.getData(getContext());
        }
    }

    private void datePicker(String date){
        String d, m, y;
    }

    private String filterAmount(String am){
        String res = "";
        for(int i = 0; i < am.length(); i++){
            try{
                Integer.parseInt(am.substring(i, i+1));
                res = res.concat(am.substring(i, i+1));
            }catch (Exception ignored){

            }
        }
        return res;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_new_client, menu);
    }

}

























