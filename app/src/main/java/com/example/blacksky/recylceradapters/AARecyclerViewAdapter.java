package com.example.blacksky.recylceradapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.example.blacksky.R;
import com.example.blacksky.datamodels.AADataModel;

import java.util.List;

public class AARecyclerViewAdapter extends RecyclerView.Adapter<AARecyclerViewAdapter.AAViewHolder> {

    public List<AADataModel> ac_clients;
    public Context context;

    public AARecyclerViewAdapter(List<AADataModel> ac_clients, Context context) {
        this.ac_clients = ac_clients;
        this.context = context;
    }


    @Override
    public AAViewHolder onCreateViewHolder( ViewGroup parent, int i) {
        View itemView = LayoutInflater.from(parent.getContext()).
                inflate(R.layout.pc_client_row, parent, false);
        return new AAViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder( AAViewHolder holder, int position) {
        AADataModel ac_client = ac_clients.get(position);
        holder.name.setText(ac_client.getName());
        holder.phone.setText(ac_client.getPhone());
        holder.service.setText(ac_client.getService());
        holder.amount.setText(ac_client.getAmount());
        holder.balance.setText(ac_client.getBalance());
    }

    public String editNumbers(String number) {
        String temp = number;



        return number;
    }

    @Override
    public int getItemCount() {
        return ac_clients.size();
    }

    public class AAViewHolder extends RecyclerView.ViewHolder {

        private TextView name;
        private TextView phone;
        private TextView service;
        private TextView amount;
        private TextView balance;
        private LinearLayout llt;

        AAViewHolder(View itemView) {
            super(itemView);
            llt = itemView.findViewById(R.id.ll_attended_client);
            llt.setVisibility(View.VISIBLE);
            name = itemView.findViewById(R.id.pc_name);
            phone = itemView.findViewById(R.id.pc_phone);
            service = itemView.findViewById(R.id.pc_service);
            amount = itemView.findViewById(R.id.pc_amount);
            balance = itemView.findViewById(R.id.pc_balance);
        }
    }
}
