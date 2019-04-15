package com.c323proj9.siyixian;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    List<String> songs_name = new ArrayList<>();
    List<String> songs_url = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        populateSongsList();
        populateSongs();
    }

    private void populateSongs() {
        for (int i = 0; i < 5; i++) {
            songs_name.add(i + "");
            songs_url.add(i + "");
        }
    }

    private void populateSongsList() {
        ArrayAdapter<String> myListAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, songs_name);
        ListView myListViewer = findViewById(R.id.songs_list);
        myListViewer.setAdapter(myListAdapter);
        myListViewer.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm:ss");
                long time_delay = 0;
                try {
                    time_delay = simpleDateFormat.parse(((EditText) findViewById(R.id.time_duration)).getText().toString()).getTime() - 18000000;
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                Intent intent = new Intent(MainActivity.this, MyIntentService.class);
                intent.putExtra("Time", time_delay);
                intent.putExtra("Song", songs_url.get(position));
                startService(intent);
            }
        });
    }

    public void showSleepInfoHandler(View view) {
        Intent myIntent = new Intent(this, SleepInfoActivity.class);
        startActivityForResult(myIntent, 40);
    }
}