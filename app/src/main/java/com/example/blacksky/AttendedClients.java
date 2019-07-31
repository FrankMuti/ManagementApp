package com.example.blacksky;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Canvas;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.blacksky.databases.DatabaseHelper;
import com.example.blacksky.datamodels.AADataModel;
import com.example.blacksky.datastructures.AttendedClientsData;
import com.example.blacksky.properties.Properties;
import com.example.blacksky.recylceradapters.AARecyclerViewAdapter;
import com.example.blacksky.recylceradapters.SwipeController;
import com.example.blacksky.recylceradapters.SwipeControllerActions;

import java.util.List;

public class AttendedClients extends AppCompatActivity {

    DatabaseHelper myDb;
    SwipeController swipeController = null;

    private AARecyclerViewAdapter mAdapter;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attended_clients);

        myDb = new DatabaseHelper(this);
        
        toolbar = findViewById(R.id.acToolbar);
        toolbar.setTitle("Completed Jobs");
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
        
        swipeController = new SwipeController(new SwipeControllerActions() {
            @Override
            public void onLeftClicked(int position) {
                String title = Properties.ATTENDED_CLIENTS;
                String phone = mAdapter.ac_clients.get(position).getPhone();
                String name = mAdapter.ac_clients.get(position).getName();
                String service = mAdapter.ac_clients.get(position).getService();
                String amount = mAdapter.ac_clients.get(position).getAmount();
                String deposit = mAdapter.ac_clients.get(position).getDeposit();
                String balance = mAdapter.ac_clients.get(position).getBalance();

                Intent intent = new Intent(getApplicationContext(), NewClient.class);
                intent.putExtra("title", title);
                intent.putExtra("phone",phone);
                intent.putExtra("name",name);
                intent.putExtra("service",service);
                intent.putExtra("agreed",amount);
                intent.putExtra("deposit",deposit);
                intent.putExtra("balance",balance);
                
                startActivity(intent);
            }

            boolean del = false;
            @Override
            public void onRightClicked(int position) {
                String name = mAdapter.ac_clients.get(position).getName();
                String phone = mAdapter.ac_clients.get(position).getPhone();

//                final AlertDialog.Builder builder = new AlertDialog.Builder(getApplicationContext());
//                builder.setCancelable(false);
//                builder.setTitle("Delete");
//                builder.setMessage("Are you sure you want to delete");
//                builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        Toast.makeText(getApplicationContext(), "Deleted " + name, Toast.LENGTH_SHORT).show();
//                        del = true;
//                    }
//                });
//
//                builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        Toast.makeText(getApplicationContext(), "Not Deleted", Toast.LENGTH_SHORT).show();
//                        del = false;
//                    }
//                });
//
//               // builder.create().show();
//                AlertDialog alertDialog = builder.create();
//                alertDialog.show();

               // Toast.makeText(getApplicationContext(),"Deleting "+ name, Toast.LENGTH_SHORT).show();

            //    if (del) {

                    try {
                        if (myDb.deleteData(phone, DatabaseHelper.AC_TABLE) > 0) {
                            mAdapter.ac_clients.remove(position);
                            mAdapter.notifyItemRemoved(position);
                            mAdapter.notifyItemChanged(position, mAdapter.getItemCount());
                        }
                    } catch (Exception e) {
                        Toast.makeText(getApplicationContext(), "Failed to delete", Toast.LENGTH_SHORT).show();
                    }
                }
         //   }
        }, getApplicationContext());

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(swipeController);
        itemTouchHelper.attachToRecyclerView(ac_RecyclerView);
        ac_RecyclerView.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void onDraw(@NonNull Canvas c, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
                swipeController.onDraw(c);
            }
        });
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
