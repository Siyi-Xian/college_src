package com.c323.midtermproject.siyixian;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class SignUpActivity extends AppCompatActivity {

    private final static String SIGNIN_FILE = "user.json";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

    }

    public void signUpUser(View view) throws JSONException, IOException {
        EditText username = findViewById(R.id.sign_up_username);
        EditText firstName = findViewById(R.id.sign_up_first_name);
        EditText lastName = findViewById(R.id.sign_up_last_name);
        EditText email = findViewById(R.id.sign_up_email);
        EditText password = findViewById(R.id.sign_up_password);

        JSONArray jsonArray;

        try {
            FileInputStream fis = openFileInput(SIGNIN_FILE);
            BufferedInputStream bis = new BufferedInputStream(fis);
            StringBuffer sBuffer = new StringBuffer();
            while (bis.available() != 0) {
                char c = (char) bis.read();
                sBuffer.append(c);
            }
            bis.close();
            fis.close();

            jsonArray = new JSONArray(sBuffer.toString());
        } catch (Exception e) {
            jsonArray = new JSONArray();
        }

        JSONObject newUser = new JSONObject();
        newUser.put("USERNAME", username.getText().toString());
        newUser.put("FIRST_NAME", firstName.getText().toString());
        newUser.put("LAST_NAME", lastName.getText().toString());
        newUser.put("EMAIL", email.getText().toString());
        newUser.put("PASSWORD", password.getText().toString());
        newUser.put("HEIGHT", "No Data");
        newUser.put("WEIGHT", "No Data");
        newUser.put("BLOOD_GROUP", "No Data");
        newUser.put("EMERGENCY_CONTACT", "No Data");
        jsonArray.put(newUser);

        FileOutputStream fos = openFileOutput(SIGNIN_FILE, MODE_PRIVATE);
        fos.write(jsonArray.toString().getBytes());
        fos.close();

        Intent myIntent = new Intent();
        setResult(Activity.RESULT_OK, myIntent);
        finish();
    }
}
