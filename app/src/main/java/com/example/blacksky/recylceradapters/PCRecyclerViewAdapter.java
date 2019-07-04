package com.example.blacksky.recylceradapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.example.blacksky.R;
import com.example.blacksky.datamodels.PCDataModel;

import java.util.List;

public class PCRecyclerViewAdapter extends RecyclerView.Adapter<PCRecyclerViewAdapter.PCViewHolder> {

    public List<PCDataModel> pc_clients;
    public PCRecyclerViewAdapter(List<PCDataModel> pc_clients) {
        this.pc_clients = pc_clients;
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

    public class PCViewHolder extends RecyclerView.ViewHolder{
        
        private TextView name;
        private TextView phone;
        private TextView service;
        
        public PCViewHolder(View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.pc_name);
            phone = itemView.findViewById(R.id.pc_phone);
            service = itemView.findViewById(R.id.pc_service);
        }
        
        
    }
}























