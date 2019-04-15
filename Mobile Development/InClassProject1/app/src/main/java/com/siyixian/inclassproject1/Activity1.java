package com.siyixian.inclassproject1;

import android.app.Activity;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Layout;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class Activity1 extends AppCompatActivity {

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == Activity.RESULT_CANCELED) {
            Log.i("ERROR_BACK", "Return Canceled");
            Toast.makeText(Activity1.this, "Error!", Toast.LENGTH_SHORT).show();
            return;
        }

        switch (requestCode) {
            case 40 :
                int backColor = data.getIntExtra("COLOR_DATA", 0);
                Log.i("BACKGROUND_COLOR", backColor + "");
                ConstraintLayout activityBackground = findViewById(R.id.background);
                activityBackground.setBackgroundColor(backColor);
                break;
        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_1);

        handleButtonClickinJava();

    }

    private void handleButtonClickinJava() {
        Button nextButton = findViewById(R.id.nextActivityButton);

        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText myInput = findViewById(R.id.nameText);
                String myMessage = myInput.getText().toString();
                Log.i("ACTIVITY_ONE_NAME", myMessage);

                //Go to second activity
                Intent myIntent = new Intent(Activity1.this, Activity2.class);
                myIntent.putExtra("MY_NAME", myMessage);
                startActivityForResult(myIntent, 40);
            }
        });
    }
}
