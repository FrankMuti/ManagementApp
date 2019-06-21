package com.example.blacksky.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.blacksky.ConfirmedClientsActivity;
import com.example.blacksky.EarningsActivity;
import com.example.blacksky.ExpensePayments;
import com.example.blacksky.PotentialClientActivity;
import com.example.blacksky.R;

/**
 *
 */
public class Utilities extends Fragment {

    Button utPotentialClient;
    Button utServiceProviders;
    Button utConfirmedClients;
    Button utAttendedClients;

    public Utilities() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_utilities, container, false);

        utPotentialClient = view.findViewById(R.id.utPotentialClientsBtn);
        utServiceProviders = view.findViewById(R.id.utServiceProvider);
        utConfirmedClients = view.findViewById(R.id.utConfirmedClients);
        utAttendedClients = view.findViewById(R.id.utMyEarnings);

        utPotentialClient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Toast.makeText(getContext(), "Potential Client", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(getContext(), PotentialClientActivity.class));
                }catch (Exception e){
                    Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });

        utServiceProviders.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Toast.makeText(getContext(), "Potential Client", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(getContext(), ExpensePayments.class));
                }catch (Exception e){
                    Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                }
                Toast.makeText(getContext(), "Service Providers", Toast.LENGTH_SHORT).show();
            }
        });

        utConfirmedClients.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), ConfirmedClientsActivity.class));
                Toast.makeText(getContext(), "Confirmed Clients", Toast.LENGTH_SHORT).show();
            }
        });

        utAttendedClients.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), EarningsActivity.class));
            }
        });

        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_new_client, menu);
    }
}
