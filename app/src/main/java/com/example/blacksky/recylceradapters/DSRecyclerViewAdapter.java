package com.example.blacksky.recylceradapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.example.blacksky.R;
import com.example.blacksky.datamodels.DSDataModel;

import java.util.List;

public class DSRecyclerViewAdapter extends RecyclerView.Adapter<DSRecyclerViewAdapter.DSViewHolder> {

    public List<DSDataModel> up_events;
    public Context context;

    public DSRecyclerViewAdapter(List<DSDataModel> up_events, Context context) {
        this.up_events = up_events;
        this.context = context;
    }

    @Override
    public DSViewHolder onCreateViewHolder( ViewGroup parent, int positon) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.upcoming_events_row, parent, false);
        return new DSViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder( DSViewHolder holder, int position) {
        DSDataModel up_event = up_events.get(position);
        holder.name.setText(up_event.getName());
        holder.phone.setText(up_event.getPhone());
        holder.service.setText(up_event.getService());
        holder.date.setText(up_event.getDate());
        holder.location.setText(up_event.getLocation());
    }

    @Override
    public int getItemCount() {
        return up_events.size();
    }

    class DSViewHolder extends RecyclerView.ViewHolder{
        
        private TextView name;
        private TextView phone;
        private TextView service;
        private TextView date;
        private TextView location;
        
        DSViewHolder( View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.up_name);
            phone = itemView.findViewById(R.id.up_phone);
            service = itemView.findViewById(R.id.up_service);
            date = itemView.findViewById(R.id.up_date);
            location = itemView.findViewById(R.id.up_location);
        }
    }
}























