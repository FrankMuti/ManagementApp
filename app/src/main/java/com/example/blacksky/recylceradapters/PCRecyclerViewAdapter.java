package com.example.blacksky.recylceradapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.blacksky.R;
import com.example.blacksky.datamodels.PCDataModel;

import java.util.ArrayList;
import java.util.List;

public class PCRecyclerViewAdapter extends RecyclerView.Adapter<PCRecyclerViewAdapter.PCViewHolder> {

    public List<PCDataModel> pc_clients;
    public Context context;

    public PCRecyclerViewAdapter(List<PCDataModel> pc_clients, Context context) {
        this.pc_clients = pc_clients;
        this.context = context;
    }

    @Override
    public PCViewHolder onCreateViewHolder( ViewGroup parent, int i) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.pc_client_row, parent, false);
        return new PCViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder( PCViewHolder holder, int position) {
        PCDataModel pc_client = pc_clients.get(position);
        holder.name.setText(pc_client.getName());
        holder.phone.setText(pc_client.getPhone());
        holder.service.setText(pc_client.getService());
    }

    @Override
    public int getItemCount() {
        return pc_clients.size();
    }

    class PCViewHolder extends RecyclerView.ViewHolder {
        
        private TextView name;
        private TextView phone;
        private TextView service;
        
        PCViewHolder(View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.pc_name);
            phone = itemView.findViewById(R.id.pc_phone);
            service = itemView.findViewById(R.id.pc_service);
        }
    }
}























