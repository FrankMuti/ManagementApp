package com.example.blacksky;

import android.content.Intent;
import android.graphics.Canvas;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.blacksky.databases.DatabaseHelper;
import com.example.blacksky.datamodels.SPDataModel;
import com.example.blacksky.datastructures.ServiceProviderData;
import com.example.blacksky.properties.Properties;
import com.example.blacksky.recylceradapters.SPRecyclerViewAdapter;
import com.example.blacksky.recylceradapters.SwipeController;
import com.example.blacksky.recylceradapters.SwipeControllerActions;

import java.util.List;

public class ServiceProvider extends AppCompatActivity {

    DatabaseHelper myDb;

    private SPRecyclerViewAdapter mAdapter;
    SwipeController swipeController = null;
    Toolbar toolbar;
    FloatingActionButton fabNewClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_provider);

        toolbar = findViewById(R.id.spToolbar);
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
        fabNewClient = findViewById(R.id.fabNewServiceProvider);
        fabNewClient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { // Edit here {change NewClient to NewServiceProvider}
                startActivity(new Intent(getApplicationContext(), NewServiceProvider.class));
            }
        });
    }

    private void setPcClientDataAdapter() {
        List<SPDataModel> sp_provider = ServiceProviderData.getSP_clients(this);
        mAdapter = new SPRecyclerViewAdapter(sp_provider, this);
    }

    private void setCcClientDataAdapter() {
        List<SPDataModel> sp_provider = ServiceProviderData.getSP_clients(this);
        // mAdapter = new PCRecyclerViewAdapter(cc_clients, this);

    }

    private void setupRecyclerView() {
        final RecyclerView pc_RecyclerView = findViewById(R.id.spRecyclerView);
        pc_RecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        pc_RecyclerView.setAdapter(mAdapter);

        swipeController = new SwipeController(new SwipeControllerActions() {
            @Override
            public void onLeftClicked(int position) {
                String title = Properties.EDIT_SP;
                String phone = mAdapter.sp_providers.get(position).getPhone();
                String name = mAdapter.sp_providers.get(position).getName();
                String service = mAdapter.sp_providers.get(position).getService();
                String lockedDate = mAdapter.sp_providers.get(position).getLockedDate();
                int agreedAmount = mAdapter.sp_providers.get(position).getAgreedAmount();
                int deposit = mAdapter.sp_providers.get(position).getDeposit();
                int balance = mAdapter.sp_providers.get(position).getBalance();

                Intent intent = new Intent(getApplicationContext(), NewClient.class);
                intent.putExtra("title", title);
                intent.putExtra("phone",phone);
                intent.putExtra("name",name);
                intent.putExtra("service",service);
                intent.putExtra("lockedDate",lockedDate);
                intent.putExtra("agreedAmount", String.valueOf(agreedAmount));
                intent.putExtra("deposit", String.valueOf(deposit));
                intent.putExtra("balance", String.valueOf(balance));

                startActivity(intent);
            }
            @Override
            public void onRightClicked(int position) {
                String name = mAdapter.sp_providers.get(position).getName();
                String phone = mAdapter.sp_providers.get(position).getPhone();
                Toast.makeText(getApplicationContext(),"Deleting "+ name, Toast.LENGTH_SHORT).show();
                try{
                    if (myDb.deleteData(phone, DatabaseHelper.SPP_TABLE) > 0) {
                        mAdapter.sp_providers.remove(position);
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
