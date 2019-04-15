package com.c323proj9.siyixian;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class SleepInfoActivity extends AppCompatActivity {

    List<MySleepInfo> mySleepInfos = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sleep_info);

        populateMyMovie();
        populateCustomListView();
    }

    private void populateCustomListView() {
    }

    private void populateMyMovie() {
        ArrayAdapter<MySleepInfo> adapter = new MyListAdapter();
        ListView listView = findViewById(R.id.sleep_info_list);
        listView.setAdapter(adapter);
    }

    private class MyListAdapter extends ArrayAdapter<MySleepInfo> {

        public MyListAdapter() {super(SleepInfoActivity.this, R.layout.sleep_info_layout, mySleepInfos); }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            //return super.getView(position, convertView, parent);

            View itemView = convertView;
            if (itemView == null)
                itemView = getLayoutInflater().inflate(R.layout.sleep_info_layout, parent, false);

            MySleepInfo currentSleepInfo = mySleepInfos.get(position);

            ((TextView)itemView.findViewById(R.id.date)).setText(currentSleepInfo.getDate());
            ((TextView)itemView.findViewById(R.id.date_duration)).setText(currentSleepInfo.getDuration());
            return itemView;
        }

    }
}

class MySleepInfo {
    private String date;
    private String duration;

    public MySleepInfo(String date, String duration) {
        this.date = date;
        this.duration = duration;
    }

    public String getDate() {

        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }
}