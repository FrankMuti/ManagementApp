package com.example.blacksky;

import android.content.Intent;
import android.graphics.Canvas;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.blacksky.R;
import com.example.blacksky.databases.DatabaseHelper;
import com.example.blacksky.datamodels.CCDataModel;
import com.example.blacksky.datamodels.PCDataModel;
import com.example.blacksky.datastructures.ConfirmedClientsData;
import com.example.blacksky.datastructures.PotentialClientsData;
import com.example.blacksky.properties.Properties;
import com.example.blacksky.recylceradapters.PCRecyclerViewAdapter;
import com.example.blacksky.recylceradapters.SwipeController;
import com.example.blacksky.recylceradapters.SwipeControllerActions;

import java.util.ArrayList;
import java.util.List;

public class Clients extends AppCompatActivity {

    DatabaseHelper myDb;

    private PCRecyclerViewAdapter mAdapter;
    SwipeController swipeController = null;
    Toolbar toolbar;
    FloatingActionButton fabNewClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clients);

        toolbar = findViewById(R.id.cToolbar);
        setSupportActionBar(toolbar);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(ContextCompat.getColor(getApplicationContext(), R.color.colorAccentBlue));
        }

        if (getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }


        setupFab();
        setPcClientDataAdapter();
        setupRecyclerView();
    }

    private void setupFab(){
        fabNewClient = findViewById(R.id.fabNewClient);
        fabNewClient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), NewClient.class));
            }
        });
    }

    private void setPcClientDataAdapter() {
        List<PCDataModel> pc_clients = PotentialClientsData.getPc_clients(this);
        mAdapter = new PCRecyclerViewAdapter(pc_clients,this);
    }

    private void setCcClientDataAdapter() {
        List<CCDataModel> cc_clients = ConfirmedClientsData.getCC_clients(this);
       // mAdapter = new PCRecyclerViewAdapter(cc_clients, this);

    }

    private void setupRecyclerView() {
        final RecyclerView pc_RecyclerView = findViewById(R.id.pcRecyclerView);
        pc_RecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        pc_RecyclerView.setAdapter(mAdapter);

        swipeController = new SwipeController(new SwipeControllerActions() {
            @Override
            public void onLeftClicked(int position) {
                String title = Properties.EDIT_CLIENT;
                String phone = mAdapter.pc_clients.get(position).getPhone();
                String name  = mAdapter.pc_clients.get(position).getName();
                String location = mAdapter.pc_clients.get(position).getLocation();
                String date = mAdapter.pc_clients.get(position).getDate();
                String time = mAdapter.pc_clients.get(position).getTime();
                String service = mAdapter.pc_clients.get(position).getService();


                Intent intent = new Intent(getApplicationContext(), NewClient.class);
                intent.putExtra("title", title);
                intent.putExtra("name", name);
                intent.putExtra("phone", phone);
                intent.putExtra("location", location);
                intent.putExtra("date", date);
                intent.putExtra("time", time);
                intent.putExtra("service", service);
                intent.putExtra("potential_client", Properties.POTENTIAL_CLIENT);
                startActivity(intent);

            }
            @Override
            public void onRightClicked(int position) {
                String name = mAdapter.pc_clients.get(position).getName();
                String phone = mAdapter.pc_clients.get(position).getPhone();
                Toast.makeText(getApplicationContext(),"Deleting "+ name, Toast.LENGTH_SHORT).show();
                try{
                    if (myDb.deleteData(phone, DatabaseHelper.PC_TABLE) > 0) {
                        mAdapter.pc_clients.remove(position);
                        mAdapter.notifyItemRemoved(position);
                        mAdapter.notifyItemChanged(position, mAdapter.getItemCount());
                    }
                }catch (Exception e){
                    Toast.makeText(getApplicationContext(), "Failed to delete", Toast.LENGTH_SHORT).show();
                }
            }
        }, getApplicationContext());

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(swipeController);
        itemTouchHelper.attachToRecyclerView(pc_RecyclerView);

        pc_RecyclerView.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void onDraw(@NonNull Canvas c, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
                swipeController.onDraw(c);
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home){
            finish();
            startActivity(new Intent(this, DashboardActivity.class));
        }
        return super.onOptionsItemSelected(item);
    }
}
