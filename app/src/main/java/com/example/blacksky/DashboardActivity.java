package com.example.blacksky;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.CalendarView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.blacksky.datamodels.DSDataModel;
import com.example.blacksky.datastructures.ConfirmedClientsData;
import com.example.blacksky.recylceradapters.DSRecyclerViewAdapter;

import java.util.ArrayList;
import java.util.List;

//import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class DashboardActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

   // private DSRecyclerViewAdapter mAdapter;

    Toolbar toolbar;

 //   static DSRecyclerViewAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation);

        // toolbar setup
        toolbar = findViewById(R.id.ds_toolbar);
        toolbar.setTitle("Black Sky Lenses");
        toolbar.setTitleTextAppearance(this, R.style.ToolbarTitle);
        toolbar.setTitleTextColor(Color.parseColor("#f3f3f3"));
        setSupportActionBar(toolbar);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(ContextCompat.getColor(getApplicationContext(), R.color.colorAccentBlue));
        }

        // Recycler View Controllers
        setupNavigationView();
        setUpComingEventsDataAdapter();
        setNextEvent();

        setCalendarView();
       // robotoCalendarTrial();
    }

    private void setupNavigationView(){
        DrawerLayout drawerLayout = findViewById(R.id.drawerLayout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawerLayout, toolbar,
                R.string.navigation_drawer_open,
                R.string.navigation_drawer_close
        );
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

    }

    private void setUpComingEventsDataAdapter() {
        DSRecyclerViewAdapter mAdapter = null;
        List<DSDataModel> up_events = ConfirmedClientsData.getUp_events(this);//ConfirmedClientsData.getUp_events(this);

        if (up_events.size() != 0) {
           // setNextEvent();
            up_events.remove(0);
            mAdapter = new DSRecyclerViewAdapter(up_events, this);
        }
        setupRecyclerView(mAdapter);
    }

    private void setupRecyclerView(DSRecyclerViewAdapter mAdapter) {
       // mAdapter = null;

        RecyclerView ds_recyclerView = findViewById(R.id.ds_RecyclerView);
        ds_recyclerView.setAdapter(null);
        ds_recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        ds_recyclerView.setAdapter(mAdapter);
    }
//    @Override
//    protected void onResume() {
//        super.onResume();
//       // setUpComingEventsDataAdapter();
//        setupRecyclerView();
//        setNextEvent();
//    }

    @Override
    protected void onRestart() {
        super.onRestart();
        setUpComingEventsDataAdapter();
     //   setupRecyclerView();
        setNextEvent();
    }

    @SuppressLint("SetTextI18n")
    private void setNextEvent( ){

        TextView name = findViewById(R.id.next_name);
        TextView phone = findViewById(R.id.next_phone);
        TextView location = findViewById(R.id.next_location);
        TextView date = findViewById(R.id.next_date);
        TextView service = findViewById(R.id.next_service);

        List<DSDataModel> up_events = ConfirmedClientsData.getUp_events(this);

        if (up_events.size() == 0){
            name.setText("No Client");
            phone.setText("");
            location.setText("");
            date.setText("");
            service.setText("");
            return;
        }

        name.setText(up_events.get(0).getName());
        phone.setText(up_events.get(0).getPhone());
        location.setText(up_events.get(0).getLocation());
        date.setText(up_events.get(0).getDate());
        service.setText(up_events.get(0).getService());
    }

    private void setCalendarView(){
        CalendarView calendarView = findViewById(R.id.ds_calendar);
        calendarView.getDate();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

        int id = menuItem.getItemId();
        switch (id){
            case R.id.navDashboard:
                Toast.makeText(this, "Dashboard", Toast.LENGTH_SHORT).show();
                break;
            case R.id.navNewClient:
                startActivity(new Intent(this, NewClient.class));
                Toast.makeText(this, "New Client", Toast.LENGTH_SHORT).show();
                break;
            case R.id.navClients:
                startActivity(new Intent(this, Clients.class));
                Toast.makeText(this, "Clients", Toast.LENGTH_SHORT).show();
                break;
            case R.id.navServiceProviders:
                startActivity(new Intent(this, ServiceProvider.class));
                Toast.makeText(this, "Service Providers", Toast.LENGTH_SHORT).show();
                break;
            case R.id.navReport:
                Toast.makeText(this, "Dashboard", Toast.LENGTH_SHORT).show();
                break;
        }

        DrawerLayout drawerLayout = findViewById(R.id.drawerLayout);
        drawerLayout.closeDrawer(GravityCompat.START);

        return true;
    }
}


