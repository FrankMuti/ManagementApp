package com.example.blacksky;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.design.button.MaterialButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabItem;
import android.support.design.widget.TabLayout;
import android.support.design.widget.TextInputEditText;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.blacksky.fragments.PageAdapter;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, NavigationView.OnNavigationItemSelectedListener {

//    public Context context = getApplicationContext();

    // MainActivity utilities
    Toolbar toolbar;
    TabLayout tabLayout;
    ViewPager viewPager;
    PageAdapter pageAdapter;
    TabItem tabDashboard;
    TabItem tabClients;
    TabItem tabOptions;

    // Dashboard Fragment

    // New Clients Fragment
    Spinner ncServiceSpinner;
    TextInputEditText ncNameEt;
    TextInputEditText ncPhoneEt;
    MaterialButton ncDatePicker;
    TextView ncDateText;
    MaterialButton ncTimePicker;
    TextView ncTimeText;
    TextView ncAddPotentialClient;

    String ncName, ncPhone;

    // Utilities Fragment
    Button utPotentialCLientBtn;

    // Navigation
    ImageButton navDrawerButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation);

        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Black Sky Lenses");
        toolbar.setTitleTextAppearance(this, R.style.ToolbarTitle);
        toolbar.setTitleTextColor(Color.parseColor("#f3f3f3"));
        //toolbar.setBackgroundColor(getResources().getColor(R.color.darkText));
        setSupportActionBar(toolbar);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(ContextCompat.getColor(getApplicationContext(), R.color.darkText));
        }

        tabLayout = findViewById(R.id.tablayout);
        tabDashboard = findViewById(R.id.tabDashboard);
        tabClients = findViewById(R.id.tabClients);
        tabOptions = findViewById(R.id.tabOptions);

        viewPager = findViewById(R.id.viewPager);

        pageAdapter = new PageAdapter(getSupportFragmentManager(), tabLayout.getTabCount());

        viewPager.setAdapter(pageAdapter);

        tabLayout.setupWithViewPager(viewPager);


        tabLayout.getTabAt(0).setText("Dashboard");
        tabLayout.getTabAt(1).setText("New Client");
        tabLayout.getTabAt(2).setText("Utilities");

        // Typeface typeface = Typeface.createFromAsset(getApplicationContext().getAssets(), "fonts/Roboto-Light.ttf");

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });


        // Navigation View
        DrawerLayout drawer = findViewById(R.id.drawerLayout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer,toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
//        navDrawerButton = findViewById(R.id.navDrawerBtn);
//
//
//        navDrawerButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                drawer.openDrawer(Gravity.LEFT);
//            }
//        });

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


        // New Client Fragment
        newClientFragment();
        newUtilityFragment();
    }

    private void newClientFragment(){
        ncServiceSpinner = findViewById(R.id.ncServiceSpinner);
        ncNameEt = findViewById(R.id.ncNameEt);
        ncPhoneEt = findViewById(R.id.ncPhoneEt);
        ncDatePicker = findViewById(R.id.ncDatePicker);
        ncDateText = findViewById(R.id.ncDateTxt);
        ncTimePicker = findViewById(R.id.ncTimePicker);
        ncTimeText = findViewById(R.id.ncTimeTxt);
        ncAddPotentialClient = findViewById(R.id.ncAddPotentialClientBtn);

    }

    public void newUtilityFragment() {
        utPotentialCLientBtn = findViewById(R.id.utPotentialClientsBtn);
//        utPotentialCLientBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Toast.makeText(getApplicationContext(), "Potential Client", Toast.LENGTH_SHORT).show();
//                startActivity(new Intent(getApplicationContext(), ListActivity.class));
//            }
//        });
    }


    @Override
    public void onClick(View v) {
        if (v == ncDatePicker){

        }else if (v == ncTimePicker){

        }else if (v == ncAddPotentialClient) {

        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawerLayout);
        if (drawer.isDrawerOpen(GravityCompat.START)){
            drawer.closeDrawer(GravityCompat.START);
        }else {
            super.onBackPressed();
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem menuItem) {

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
                Toast.makeText(this, "Dashboard", Toast.LENGTH_SHORT).show();
                break;
            case R.id.navServiceProviders:
                Toast.makeText(this, "Dashboard", Toast.LENGTH_SHORT).show();
                break;
            case R.id.navReport:
                Toast.makeText(this, "Dashboard", Toast.LENGTH_SHORT).show();
                break;
        }

        DrawerLayout drawer = findViewById(R.id.drawerLayout);
        drawer.closeDrawer(GravityCompat.START);

        return true;
    }
}










































