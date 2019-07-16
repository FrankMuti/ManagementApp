package com.example.blacksky;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.example.blacksky.databases.DatabaseHelper;
import com.example.blacksky.datamodels.DSDataModel;
import com.example.blacksky.datastructures.ConfirmedClientsData;
import com.example.blacksky.properties.Properties;
import com.example.blacksky.recylceradapters.DSRecyclerViewAdapter;
import com.example.blacksky.recylceradapters.SwipeController;
import com.example.blacksky.recylceradapters.SwipeControllerActions;
import com.github.sundeepk.compactcalendarview.CompactCalendarView;
import com.github.sundeepk.compactcalendarview.domain.Event;

import org.apache.poi.ss.formula.functions.T;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;


public class DashboardActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

   // private DSRecyclerViewAdapter mAdapter;
    DatabaseHelper myDb;
    Toolbar toolbar;
    SwipeController swipeController = null;
    CompactCalendarView calendarView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation);

        //startActivity(new Intent(this, Report.class));
        Toast.makeText(this, "Dashboard", Toast.LENGTH_SHORT).show();

        // toolbar setup
        toolbar = findViewById(R.id.ds_toolbar);
        toolbar.setTitle("Black Sky Lenses");
        toolbar.setTitleTextAppearance(this, R.style.ToolbarTitle);
        toolbar.setTitleTextColor(Color.parseColor("#f3f3f3"));
        setSupportActionBar(toolbar);

        calendarView = findViewById(R.id.calendarView);

        ImageButton button = findViewById(R.id.nextOptions);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setPopUpMenu(button);
            }
        });


        myDb = new DatabaseHelper(this);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(ContextCompat.getColor(getApplicationContext(), R.color.colorAccentBlue));
        }

        // Recycler View Controllers
        setupNavigationView();
        setUpComingEventsDataAdapter();
        setNextEvent();
        setCalendarView();
    }

    private void setPopUpMenu(View view){
        PopupMenu popupMenu = new PopupMenu(DashboardActivity.this, view);
        popupMenu.getMenuInflater().inflate(R.menu.menu_next_event, popupMenu.getMenu());
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.attendEvent:
                        attendEvent();
                       // Toast.makeText(getApplicationContext(), "Attended Event", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.cancelEvent:
                        deleteEvent();
                        Toast.makeText(getApplicationContext(), "Cancel Event", Toast.LENGTH_SHORT).show();
                        break;
                }
                return true;
            }
        });
        popupMenu.show();
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
            up_events.remove(0);
            mAdapter = new DSRecyclerViewAdapter(up_events, this);
        }
        setupRecyclerView(mAdapter);
    }

    private void setupRecyclerView(DSRecyclerViewAdapter mAdapter) {
        RecyclerView ds_recyclerView = findViewById(R.id.ds_RecyclerView);
        ds_recyclerView.setAdapter(null);
        ds_recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        ds_recyclerView.setAdapter(mAdapter);

        swipeController = new SwipeController(new SwipeControllerActions() {
            @Override
            public void onLeftClicked(int position) {
                String title = Properties.CONFIRMED_CLIENT;
                String name = mAdapter.up_events.get(position).getName();
                String phone = mAdapter.up_events.get(position).getPhone();
                String location = mAdapter.up_events.get(position).getLocation();
                String date = mAdapter.up_events.get(position).getDate();
                String time = mAdapter.up_events.get(position).getTime();
                String service = mAdapter.up_events.get(position).getService();
                String agreed = mAdapter.up_events.get(position).getAgreedAmount();
                String deposit = mAdapter.up_events.get(position).getDeposit();

                Intent intent = new Intent(getApplicationContext(), NewClient.class);
                intent.putExtra("title", title);
                intent.putExtra("name", name);
                intent.putExtra("phone", phone);
                intent.putExtra("location", location);
                intent.putExtra("date", date);
                intent.putExtra("time", time);
                intent.putExtra("service", service);
                intent.putExtra("agreed", agreed);
                intent.putExtra("deposit", deposit);
                intent.putExtra("confirmed_client", Properties.CONFIRMED_CLIENT);
                startActivity(intent);
            }

            boolean del;
            @Override
            public void onRightClicked(int position) {
                String name = mAdapter.up_events.get(position).getName();
                String phone = mAdapter.up_events.get(position).getPhone();
                Toast.makeText(getApplicationContext(),"Deleting "+ name, Toast.LENGTH_SHORT).show();

                final AlertDialog.Builder builder = new AlertDialog.Builder(getApplicationContext());
                builder.setCancelable(false);
                builder.setTitle("Delete");
                builder.setMessage("Are you sure you want to delete");
                builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(getApplicationContext(), "Deleted " + name, Toast.LENGTH_SHORT).show();
                        del = true;
                    }
                });

                builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(getApplicationContext(), "Not Deleted", Toast.LENGTH_SHORT).show();
                        del = false;
                    }
                });

                try{
                    if (del)
                        if (myDb.deleteData(phone, DatabaseHelper.CC_TABLE) > 0) {
                            mAdapter.up_events.remove(position);
                            mAdapter.notifyItemRemoved(position);
                            mAdapter.notifyItemChanged(position, mAdapter.getItemCount());
                        }
                }catch (Exception e){
                    Toast.makeText(getApplicationContext(), "Failed to delete", Toast.LENGTH_SHORT).show();
                }
            }
        }, getApplicationContext());

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(swipeController);
        itemTouchHelper.attachToRecyclerView(ds_recyclerView);

        ds_recyclerView.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void onDraw(@NonNull Canvas c, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
                swipeController.onDraw(c);            }
        });
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        setUpComingEventsDataAdapter();
     //   setupRecyclerView();
        setNextEvent();
        setCalendarView();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (swipeController == null){
            setUpComingEventsDataAdapter();
        }
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


    private void deleteEvent() {
        TextView phone = findViewById(R.id.next_phone);

        myDb.deleteData(phone.getText().toString(), DatabaseHelper.CC_TABLE);
        setNextEvent();
        setUpComingEventsDataAdapter();
        setCalendarView();
    }

    private void attendEvent() {
        TextView phone = findViewById(R.id.next_phone);
        List<DSDataModel> events = ConfirmedClientsData.getUp_events(this);
        if (myDb.insertACData(
                events.get(0).getName(),
                events.get(0).getPhone(),
                events.get(0).getService(),
                events.get(0).getAgreedAmount()
        )){

            deleteEvent();
            Toast.makeText(this, "Attended", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(this, "Failed To Update", Toast.LENGTH_SHORT).show();
        }
    }

    private void setCalendarView() {

        List<DSDataModel> up_events = ConfirmedClientsData.getUp_events(this);
        List<Long> longDates = ConfirmedClientsData.getDates(up_events);

        calendarView.setFirstDayOfWeek(Calendar.MONDAY);

        List<Event> event_dates = new ArrayList<>();
        for(int i = 0; i < longDates.size(); i++) {
            Event event = new Event(Color.GREEN, longDates.get(i), "Event " + i);
            event_dates.add(event);
        }

        calendarView.removeAllEvents();

        for (int i = 0; i < event_dates.size(); i++) {
            calendarView.addEvent(event_dates.get(i));
        }


        calendarView.setListener(new CompactCalendarView.CompactCalendarViewListener() {
            @Override
            public void onDayClick(Date dateClicked) {
                //List<Event> events = calendarView.getEvents(dateClicked);
            }

            @Override
            public void onMonthScroll(Date firstDayOfNewMonth) {

            }
        });
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
                startActivity(new Intent(this, Report.class));
                Toast.makeText(this, "Dashboard", Toast.LENGTH_SHORT).show();
                break;
            case R.id.navNewServiceProvider:
                startActivity(new Intent(this, NewServiceProvider.class));
                Toast.makeText(this, "New Service Provider", Toast.LENGTH_SHORT).show();
            case R.id.navAttendedClients:
                startActivity(new Intent(this, AttendedClients.class));
                Toast.makeText(this, "Attended Clients", Toast.LENGTH_SHORT).show();
        }

        DrawerLayout drawerLayout = findViewById(R.id.drawerLayout);
        drawerLayout.closeDrawer(GravityCompat.START);

        return true;
    }
}