package com.siyixian.project1;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.w3c.dom.Text;

public class Add extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        handleButtonAddinJava();
    }

    private void handleButtonAddinJava() {

        Button addButton = findViewById(R.id.addButton);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                EditText number_1 = findViewById(R.id.numOne);
                EditText number_2 = findViewById(R.id.numTwo);
                EditText answer = findViewById(R.id.numThree);

                try {

                    int ans = Integer.parseInt(number_1.getText().toString()) + Integer.parseInt(number_2.getText().toString());
                    answer.setText(ans + "");

                } catch(Exception e) {
                    answer.setText("Error");
                };
            }
        });
    }
}
