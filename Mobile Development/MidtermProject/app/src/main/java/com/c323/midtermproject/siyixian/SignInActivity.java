package com.c323.midtermproject.siyixian;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.maps.model.LatLng;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

public class SignInActivity extends AppCompatActivity {

    private final static String SIGNIN_FILE = "user.json";
    private JSONArray jsonArray;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
    }

    public void loginHandler(View view) throws JSONException, IOException {
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

        EditText fullName = findViewById(R.id.username);
        EditText passWord = findViewById(R.id.password);
        boolean find = false;

        for (int i = 0; i < jsonArray.length(); i++) {
            String name = jsonArray.getJSONObject(i).getString("USERNAME");
            String password = jsonArray.getJSONObject(i).getString("PASSWORD");
            if (name.compareTo(fullName.getText().toString()) == 0) {
                find = true;
                if (password.compareTo(passWord.getText().toString()) == 0) {
                    SharedPreferences myshprefs = getSharedPreferences("SPREF_APP", MODE_PRIVATE);
                    SharedPreferences.Editor editor = myshprefs.edit();
                    editor.clear();
                    editor.putString("USER", jsonArray.getJSONObject(i).toString());
                    editor.commit();

                    Intent myIntent = new Intent(SignInActivity.this, MainActivity.class);
                    startActivityForResult(myIntent, 40);
                } else
                    Toast.makeText(SignInActivity.this, "Invalid Password", Toast.LENGTH_SHORT).show();
            }
        }
        if (!find)
            Toast.makeText(SignInActivity.this, "User Not Found", Toast.LENGTH_SHORT).show();
    }

    public void signUpHandler(View view) {
        Intent myIntent = new Intent(SignInActivity.this, SignUpActivity.class);
        startActivityForResult(myIntent, 40);
    }
}
