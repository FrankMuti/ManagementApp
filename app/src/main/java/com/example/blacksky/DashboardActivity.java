package com.example.blacksky;

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
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.blacksky.datamodels.DSDataModel;
import com.example.blacksky.datamodels.DSDataModel;
import com.example.blacksky.datastructures.ConfirmedClientsData;
import com.example.blacksky.recylceradapters.DSRecyclerViewAdapter;
import com.example.blacksky.recylceradapters.SwipeController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;



public class DashboardActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private DSRecyclerViewAdapter mAdapter;
    //SwipeController swipeController = null;

    // Dashboard View Items
    Toolbar toolbar;

    // navigation view
    ImageButton navDrawerButton;

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
        setupRecyclerView();
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
        List<DSDataModel> up_events = new ArrayList<>();//ConfirmedClientsData.getUp_events(this);
       // List<DSDataModel> up_events = new ArrayList<>();
        DSDataModel client = new DSDataModel();
        
        client.setName("Albert Einstein");
        client.setPhone("0711142832");
        client.setService("Scientific Photoshoot");
        up_events.add(client);

        DSDataModel client1 = new DSDataModel();
        client1.setName("Sir Isaac Newton");
        client1.setPhone("0711142832");
        client1.setService("Scientific Photoshoot");
        up_events.add(client1);

        DSDataModel client2 = new DSDataModel();
        client2.setName("Thomas Edison");
        client2.setPhone("0711142832");
        client2.setService("Scientific Photoshoot");
        up_events.add(client2);

        DSDataModel client3 = new DSDataModel();
        client3.setName("Nikola Tesla");
        client3.setPhone("0711142832");
        client3.setService("Scientific Photoshoot");
        up_events.add(client3);
        
        
        if (up_events.size() != 0) {
           // setNextEvent();
            up_events.remove(0);
            mAdapter = new DSRecyclerViewAdapter(up_events, this);
        }
    }

    private void setupRecyclerView() {
        RecyclerView ds_recylcerView = findViewById(R.id.ds_RecyclerView);
        ds_recylcerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        ds_recylcerView.setAdapter(mAdapter);
    }

    private void setNextEvent(){

        TextView name = findViewById(R.id.next_name);
        TextView phone = findViewById(R.id.next_phone);
        TextView location = findViewById(R.id.next_location);
        TextView date = findViewById(R.id.next_date);
        TextView service = findViewById(R.id.next_service);

        //List<DSDataModel> up_events = ConfirmedClientsData.getUp_events(this);
        List<DSDataModel> up_events = new ArrayList<>();
        DSDataModel client = new DSDataModel();
        client.setName("Albert Einstein");
        client.setPhone("0711142832");
        client.setService("Scientific Photoshoot");
        up_events.add(client);

        DSDataModel client1 = new DSDataModel();
        client1.setName("Sir Isaac Newton");
        client1.setPhone("0711142832");
        client1.setService("Scientific Photoshoot");
        up_events.add(client1);

        DSDataModel client2 = new DSDataModel();
        client2.setName("Thomas Edison");
        client2.setPhone("0711142832");
        client2.setService("Scientific Photoshoot");
        up_events.add(client2);

        DSDataModel client3 = new DSDataModel();
        client3.setName("Nikola Tesla");
        client3.setPhone("0711142832");
        client3.setService("Scientific Photoshoot");
        up_events.add(client3);

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
        //Color upComing = ContextCompat.getColor(this, R.color.green);
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




























