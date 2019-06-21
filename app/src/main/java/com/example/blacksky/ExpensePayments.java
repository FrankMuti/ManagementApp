package com.example.blacksky;

import android.graphics.Color;
import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.blacksky.datastructures.ExpenseData;
import com.example.blacksky.datastructures.ListAdapter;
import com.example.blacksky.datastructures.PotentialClientsData;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ExpensePayments extends AppCompatActivity {

    Toolbar toolbar;
    ArrayList<HashMap<String, String>> serviceProvider;

    ExpandableListView expandableListView;
    HashMap<String, List<String>> listChild;
    List<String> listHeader;
    ListAdapter listAdapter;

    TextView childItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expense_payments);

        toolbar = findViewById(R.id.expListToolbar);
        toolbar.setTitle("Service Providers");
        toolbar.setTitleTextAppearance(this, R.style.ToolbarTitle);
        toolbar.setTitleTextColor(Color.parseColor("#f3f3f3"));

        // toolbar.setBackgroundColor(getResources().getColor(R.color.darkText));
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(ContextCompat.getColor(getApplicationContext(), R.color.darkText));
        }

        childItem = findViewById(R.id.childItem);
        expandableListView = findViewById(R.id.epExpListView);
        listChild = ExpenseData.getData(getApplicationContext());
        listHeader = new ArrayList<>(listChild.keySet());
        listAdapter = new ListAdapter(getApplicationContext(), listHeader, listChild);
        expandableListView.setAdapter(listAdapter);

        expandableListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                try {
                    Toast.makeText(getApplicationContext(), listAdapter.getChild(groupPosition, childPosition).toString(), Toast.LENGTH_SHORT).show();
                }catch (Exception e){
                    Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_SHORT).show();
                }
                return false;
            }
        });
    }
}
