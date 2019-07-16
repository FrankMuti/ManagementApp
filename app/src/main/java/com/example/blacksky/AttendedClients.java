package com.example.blacksky;

import android.content.Intent;
import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.example.blacksky.databases.DatabaseHelper;
import com.example.blacksky.datamodels.AADataModel;
import com.example.blacksky.datastructures.AttendedClientsData;
import com.example.blacksky.recylceradapters.AARecyclerViewAdapter;

import java.util.List;

public class AttendedClients extends AppCompatActivity {

    DatabaseHelper myDB;

    private AARecyclerViewAdapter mAdapter;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attended_clients);

        toolbar = findViewById(R.id.acToolbar);
        toolbar.setTitle("Attended Clients");
        setSupportActionBar(toolbar);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(ContextCompat.getColor(getApplicationContext(), R.color.colorAccentBlue));
        }

        if (getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        setACDataAdapter();
        setupRecyclerView();
    }

    private void setACDataAdapter() {
        List<AADataModel> ac_clients = AttendedClientsData.getAA_clients(this);
        mAdapter = new AARecyclerViewAdapter(ac_clients, this);
    }

    private void setupRecyclerView() {
        final RecyclerView ac_RecyclerView = findViewById(R.id.acRecyclerView);
        ac_RecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        ac_RecyclerView.setAdapter(mAdapter);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            startActivity(new Intent(this, DashboardActivity.class));
        }
        return true;
    }
}
