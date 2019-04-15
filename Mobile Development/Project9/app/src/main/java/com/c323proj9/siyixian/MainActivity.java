package com.c323proj9.siyixian;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        requestPermission();

        SharedPreferences sharedPreferences = getSharedPreferences("SPREF_APP", MODE_PRIVATE);
        String message_subject = sharedPreferences.getString("Message", "");
        ((EditText) findViewById(R.id.alarm_message)).setText(message_subject);
    }

    private void requestPermission() {
        if (ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.SEND_SMS) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.SEND_SMS}, 1);
            recreate();
        }
    }

    public void setAlarmHandler(View view) throws ParseException {
        findViewById(R.id.set_alarm).setEnabled(false);

        Intent intent = new Intent(this, MyIntentService.class);

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm:ss");
        long time_delay = simpleDateFormat.parse(((EditText) findViewById(R.id.alarm_time)).getText().toString()).getTime() - 18000000;

        intent.putExtra("Time", time_delay);
        startService(intent);

        SharedPreferences sharedPreferences = getSharedPreferences("SPREF_APP", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("Message", ((EditText) findViewById(R.id.alarm_message)).getText().toString());
        editor.commit();
    }

    public void stopAlarmHandler(View view) {
        findViewById(R.id.set_alarm).setEnabled(true);

        Intent intent = new Intent(this, MyIntentService.class);
        stopService(intent);

        Intent myIntent = new Intent(MainActivity.this, SendActivity.class);
        startActivityForResult(myIntent, 40);
    }
}
