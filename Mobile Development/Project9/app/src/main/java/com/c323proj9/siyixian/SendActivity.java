package com.c323proj9.siyixian;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class SendActivity extends AppCompatActivity {

    private String email_phone;
    private String message_body;
    private String message_subject;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send);

        SharedPreferences sharedPreferences = getSharedPreferences("SPREF_APP", MODE_PRIVATE);
        message_subject = sharedPreferences.getString("Message", "");

        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.commit();

        ((TextView) findViewById(R.id.alarm_message_view)).setText(message_subject);
    }

    private void getInfo() {
        email_phone = ((EditText) findViewById(R.id.email_phone)).getText().toString();
        message_body = ((EditText) findViewById(R.id.meesage_body)).getText().toString();
    }

    public void sendEmail(View view) {
        getInfo();

        String[] TO = {email_phone};
        String[] CC = {""};
        Intent emailIntent = new Intent(Intent.ACTION_SEND);

        emailIntent.setData(Uri.parse("mailto:"));
        emailIntent.setType("text/plain");
        emailIntent.putExtra(Intent.EXTRA_EMAIL, TO);
        emailIntent.putExtra(Intent.EXTRA_CC, CC);
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, message_subject);
        emailIntent.putExtra(Intent.EXTRA_TEXT, message_body);

        try {
            startActivity(Intent.createChooser(emailIntent, "Send mail..."));
            finish();
        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(SendActivity.this, "There is no email client installed.", Toast.LENGTH_SHORT).show();
        }

    }

    public void sendSMS(View view) {
        getInfo();

        SmsManager smsManager = SmsManager.getDefault();

        String SENT_SMS_ACTION = "SENT_SMS_ACTION";
        Intent sentIntent = new Intent(SENT_SMS_ACTION);
        PendingIntent sentPI = PendingIntent.getBroadcast(this, 0, sentIntent, 0);
        registerReceiver(new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                if (getResultCode() == Activity.RESULT_OK)
                    Toast.makeText(SendActivity.this, "SMS send successful!", Toast.LENGTH_SHORT).show();
            }
        }, new IntentFilter(SENT_SMS_ACTION));

        String DELIVERED_SMS_ACTION = "DELIVERED_SMS_ACTION";
        Intent deliverIntent = new Intent(DELIVERED_SMS_ACTION);
        PendingIntent deliverPI = PendingIntent.getBroadcast(this, 0, deliverIntent, 0);
        registerReceiver(new BroadcastReceiver() {
            @Override
            public void onReceive(Context _context, Intent _intent) {
                Toast.makeText(SendActivity.this, "Receiver receive SMS successful!", Toast.LENGTH_SHORT).show();
            }
        }, new IntentFilter(DELIVERED_SMS_ACTION));

        List<String> divideContents = smsManager.divideMessage(message_body);
        for (String text : divideContents) {
            smsManager.sendTextMessage(email_phone, null, text, sentPI, deliverPI);
        }
    }
}
