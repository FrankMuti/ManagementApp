package com.example.blacksky;

import android.graphics.Canvas;
import android.support.annotation.NonNull;
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
import com.example.blacksky.recylceradapters.SwipeController;
import com.example.blacksky.recylceradapters.SwipeControllerActions;

import java.util.ArrayList;
import java.util.List;

public class Clients extends AppCompatActivity {
    private PCRecyclerViewAdapter mAdapter;
    SwipeController swipeController = null;

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

        mAdapter = new PCRecyclerViewAdapter(pc_clients, getApplicationContext());

    }

    private void setupRecyclerView() {
        final RecyclerView pc_RecyclerView = findViewById(R.id.pcRecyclerView);
        pc_RecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        pc_RecyclerView.setAdapter(mAdapter);

        swipeController = new SwipeController(new SwipeControllerActions() {
            @Override
            public void onLeftClicked(int position) {
                String name = mAdapter.pc_clients.get(position).getName();
                Toast.makeText(getApplicationContext(), name, Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onRightClicked(int position) {
                mAdapter.pc_clients.remove(position);
                mAdapter.notifyItemRemoved(position);
                mAdapter.notifyItemChanged(position, mAdapter.getItemCount());
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
}
