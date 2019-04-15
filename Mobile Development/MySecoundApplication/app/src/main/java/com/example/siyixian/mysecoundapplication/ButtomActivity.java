package com.example.siyixian.mysecoundapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class ButtomActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buttom);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        handleButtonClickinJava();

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    private void handleButtonClickinJava() {

        Button myButoton = findViewById(R.id.ClickButton);
        myButoton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i("BUTTON_APP", "Siyi Click Me");
                Toast.makeText(ButtomActivity.this, "You Click Me!", Toast.LENGTH_SHORT).show();

                EditText myInput = findViewById(R.id.editText);
                Log.i("BUTTON_APP", myInput.getText().toString());
                TextView myOutput = findViewById(R.id.textView);
                myOutput.setText(myInput.getText().toString());

                // go to second activity
                startActivities(new Intent(ButtomActivity.this, SecondActivity.class)[], );
            }
        });
    }

    public void myPressMeCallbackFn(View view) {
        Toast.makeText(this, "You Press Me!", Toast.LENGTH_SHORT).show();
    }
}
