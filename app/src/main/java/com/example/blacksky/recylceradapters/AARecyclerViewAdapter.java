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

public class AARecyclerViewAdapter extends RecyclerView.Adapter<AARecyclerViewAdapter.SPViewHolder> {

    public List<AADataModel> ac_clients;
    public Context context;

    public AARecyclerViewAdapter(List<AADataModel> ac_clients, Context context) {
        this.ac_clients = ac_clients;
        this.context = context;
    }


    @Override
    public SPViewHolder onCreateViewHolder( ViewGroup parent, int i) {
        View itemView = LayoutInflater.from(parent.getContext()).
                inflate(R.layout.pc_client_row, parent, false);
        return new SPViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder( SPViewHolder holder, int position) {
        AADataModel sp_provider = ac_clients.get(position);
        holder.name.setText(sp_provider.getName());
        holder.phone.setText(sp_provider.getPhone());
        holder.service.setText(sp_provider.getService());
        holder.amount.setText(sp_provider.getAmount());
    }

    @Override
    public int getItemCount() {
        return ac_clients.size();
    }

    public class SPViewHolder extends RecyclerView.ViewHolder {

        private TextView name;
        private TextView phone;
        private TextView service;
        private TextView amount;
        private LinearLayout llt;

        public SPViewHolder( View itemView) {
            super(itemView);
            llt = itemView.findViewById(R.id.ll_attended_client);
            llt.setVisibility(View.VISIBLE);
            name = itemView.findViewById(R.id.pc_name);
            phone = itemView.findViewById(R.id.pc_phone);
            service = itemView.findViewById(R.id.pc_service);
            amount = itemView.findViewById(R.id.pc_amount);
        }
    }
}
