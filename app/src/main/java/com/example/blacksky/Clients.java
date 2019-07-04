package com.example.blacksky;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;
import android.widget.Toast;

import com.example.blacksky.R;
import com.example.blacksky.datamodels.PCDataModel;
import com.example.blacksky.recylceradapters.PCRecyclerViewAdapter;

import java.util.ArrayList;
import java.util.List;

public class Clients extends AppCompatActivity {

    private PCRecyclerViewAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clients);

        setPcClientDataAdapter();
        setupRecyclerView();
    }

    private void setPcClientDataAdapter() {
        List<PCDataModel> pc_clients = new ArrayList<>();
        PCDataModel client = new PCDataModel();
        client.setName("Albert Einstein");
        client.setPhone("0711142832");
        client.setService("Scientific Photoshoot");
        pc_clients.add(client);

        PCDataModel client1 = new PCDataModel();
        client1.setName("Sir Isaac Newton");
        client1.setPhone("0711142832");
        client1.setService("Scientific Photoshoot");
        pc_clients.add(client1);

        PCDataModel client2 = new PCDataModel();
        client2.setName("Thomas Edison");
        client2.setPhone("0711142832");
        client2.setService("Scientific Photoshoot");
        pc_clients.add(client2);

        PCDataModel client3 = new PCDataModel();
        client3.setName("Nikola Tesla");
        client3.setPhone("0711142832");
        client3.setService("Scientific Photoshoot");
        pc_clients.add(client3);

        mAdapter = new PCRecyclerViewAdapter(pc_clients);

    }

    private void setupRecyclerView() {
        final RecyclerView pc_RecyclerView = findViewById(R.id.pcRecyclerView);
        pc_RecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        pc_RecyclerView.setAdapter(mAdapter);

        pc_RecyclerView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = mAdapter.pc_clients.get(pc_RecyclerView.getChildPosition(v)).getName();
                Toast.makeText(getApplicationContext(), name, Toast.LENGTH_SHORT).show();
            }
        });

    }
}
