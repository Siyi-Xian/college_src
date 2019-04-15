package com.c323proj8.siyixian;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.provider.CallLog;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CallLogActivity extends AppCompatActivity {

    List<MyCallLog> myCallLogs = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_call_log);

        if (ActivityCompat.checkSelfPermission(CallLogActivity.this, Manifest.permission.READ_CALL_LOG) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(CallLogActivity.this, new String[]{Manifest.permission.READ_CALL_LOG}, 1);
        }
        if (ActivityCompat.checkSelfPermission(CallLogActivity.this, Manifest.permission.WRITE_CALL_LOG) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(CallLogActivity.this, new String[]{Manifest.permission.WRITE_CALL_LOG}, 1);
        }

        populateMyContact();
        populateCallLogInformation();
    }

    private void populateCallLogInformation() {

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_CALL_LOG) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(CallLogActivity.this, new String[]{Manifest.permission.READ_CALL_LOG}, 1);
            recreate();
        }
        Cursor cursor = getContentResolver().query(CallLog.Calls.CONTENT_URI, null, null, null, CallLog.Calls.DEFAULT_SORT_ORDER);
        while (cursor.moveToNext()) {
            String phone_number = cursor.getString(cursor.getColumnIndex(CallLog.Calls.NUMBER));
            String date = cursor.getString(cursor.getColumnIndex(CallLog.Calls.DATE));
            String duration = cursor.getString(cursor.getColumnIndex(CallLog.Calls.DURATION));

            SimpleDateFormat sdf = new SimpleDateFormat("hh:mm:ss a, MM/dd/yyyy");
            date = sdf.format(new Date(Long.parseLong(date)));

            sdf = new SimpleDateFormat("mm:ss");
            duration = sdf.format(new Date(Long.parseLong(duration) * 1000));

            MyCallLog myCallLog = new MyCallLog(phone_number, date, duration);
            myCallLogs.add(myCallLog);
        }
    }

    private void populateMyContact() {

        ArrayAdapter<MyCallLog> adapter = new CallLogActivity.MyListAdapter();
        ListView listView = findViewById(R.id.call_log_list);
        listView.setAdapter(adapter);
    }

    private class MyListAdapter extends ArrayAdapter<MyCallLog> {

        public MyListAdapter() {
            super(CallLogActivity.this, R.layout.call_log_layout, myCallLogs);
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            //return super.getView(position, convertView, parent);

            View itemView = convertView;
            if (itemView == null)
                itemView = getLayoutInflater().inflate(R.layout.call_log_layout, parent, false);

            MyCallLog currentCallLog = myCallLogs.get(position);

            TextView phone_number = itemView.findViewById(R.id.call_log_phone_number);
            phone_number.setText(currentCallLog.getPhone_number());
            TextView date = itemView.findViewById(R.id.call_log_date);
            date.setText(currentCallLog.getDate());
            TextView duration = itemView.findViewById(R.id.call_log_duration);
            duration.setText(currentCallLog.getDuration());

            return itemView;
        }

    }
}

class MyCallLog {
    private String phone_number;
    private String date;
    private String duration;

    public MyCallLog(String phone_number, String date, String duration) {
        this.phone_number = phone_number;
        this.date = date;
        this.duration = duration;
    }

    public String getPhone_number() {

        return phone_number;
    }

    public void setPhone_number(String phone_number) {
        this.phone_number = phone_number;
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
