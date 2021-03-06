package com.example.blacksky.recylceradapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.example.blacksky.R;
import com.example.blacksky.datamodels.SPDataModel;

import org.w3c.dom.Text;

import java.util.List;

public class SPRecyclerViewAdapter extends RecyclerView.Adapter<SPRecyclerViewAdapter.SPViewHolder> {

    public List<SPDataModel> sp_providers;
    public Context context;

    public SPRecyclerViewAdapter(List<SPDataModel> sp_providers, Context context) {
        this.sp_providers = sp_providers;
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
        SPDataModel sp_provider = sp_providers.get(position);
        holder.name.setText(sp_provider.getName());
        holder.phone.setText(sp_provider.getPhone());
        holder.service.setText(sp_provider.getService());
        holder.amount.setText(String.valueOf(sp_provider.getAgreedAmount()));
        holder.balance.setText(String.valueOf(sp_provider.getBalance()));
    }

    @Override
    public int getItemCount() {
        return sp_providers.size();
    }

    public class SPViewHolder extends RecyclerView.ViewHolder {
        
        private TextView name;
        private TextView phone;
        private TextView service;
        private TextView amount;
        private TextView balance;
        private LinearLayout llt;
        
        public SPViewHolder( View itemView) {
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
