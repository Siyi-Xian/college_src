package com.siyixian.project2;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.SimpleTimeZone;

public class AddItems extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_items);

        handleOnClickButton();
    }

    private void handleOnClickButton() {
        Button addItem = findViewById(R.id.addItemButton);

        addItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText itemName = findViewById(R.id.itemName);
                String name = itemName.getText().toString();

                EditText itemQuantity = findViewById(R.id.quantity);
                String quantity = itemQuantity.getText().toString();

                Calendar myCal = Calendar.getInstance();
                Date now = myCal.getTime();
                SimpleDateFormat sdf = new SimpleDateFormat("hh:mm:ss a, MM-dd-yyyy");
                String dateString = sdf.format(now);

                //Save in Shared Preferences
                SharedPreferences myshprefs = getSharedPreferences("SPREF_APP", MODE_PRIVATE);
                Set<String> items = myshprefs.getStringSet("Items", new HashSet<String>());
                Set<String> itemsLog = myshprefs.getStringSet("Items Log", new HashSet<String>());

                items.add(name + "(" + quantity + ")");
                itemsLog.add(name + "(" + quantity + ")" + "  " + dateString);

                SharedPreferences.Editor editor = myshprefs.edit();
                editor.clear();
                editor.putStringSet("Items", items);
                editor.putStringSet("Items Log", itemsLog);
                editor.commit();

                Intent myIntent = new Intent();
                setResult(Activity.RESULT_OK, myIntent);
                finish();
            }
        });
    }
}
