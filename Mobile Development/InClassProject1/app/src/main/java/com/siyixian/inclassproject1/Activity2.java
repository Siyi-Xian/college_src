package com.siyixian.inclassproject1;

import android.app.Activity;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

public class Activity2 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_2);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Intent myIntent = getIntent();
        String name = myIntent.getStringExtra("MY_NAME");
        Log.i("ACTIVITY_ONE_NAME", name);
        TextView textView = findViewById(R.id.nameView);
        textView.setText(name + " say hello to all!");
    }

    public void clickRedButton(View view) {
        returnButtonCallback(Color.RED);
    }

    public void clickGreenButton(View view) {
        returnButtonCallback(Color.GREEN);
    }

    public void clickBlueButton(View view) {
        returnButtonCallback(Color.BLUE);
    }

    public void returnButtonCallback(int color) {
        Intent myIntent = new Intent();
        myIntent.putExtra("COLOR_DATA", color);
        setResult(Activity.RESULT_OK, myIntent);
        finish();
    }
}
