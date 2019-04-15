package com.siyixian.project4;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.res.Configuration;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FragmentManager fragmentManager = getFragmentManager();
        final FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        CreateFragment createFragment = new CreateFragment();
        fragmentTransaction.replace(R.id.linearLayout_Main, createFragment);
        fragmentTransaction.commit();


        Button creatButton = findViewById(R.id.create);
        Button addButton = findViewById(R.id.appointments);

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                AppointmentFragment appointmentFragment = new AppointmentFragment();
                fragmentTransaction.replace(R.id.linearLayout_Main, appointmentFragment);
                fragmentTransaction.commit();
            }
        });

        creatButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                CreateFragment createFragment = new CreateFragment();
                fragmentTransaction.replace(R.id.linearLayout_Main, createFragment);
                fragmentTransaction.commit();
            }
        });
    }
}
