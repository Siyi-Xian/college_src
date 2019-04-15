package com.c323finalproj.siyixian;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class LogInActivity extends AppCompatActivity {

    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(LogInActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.INTERNET, Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
            recreate();
        }

        SharedPreferences sharedPreferencesViewed = getSharedPreferences("SPREF_VIEWED", MODE_PRIVATE);

        String viewed = sharedPreferencesViewed.getString("VIEWED", "");
        if (viewed.length() == 0) {
            Log.i("VIEWED", "DONE");
            SharedPreferences.Editor editor = sharedPreferencesViewed.edit();
            editor.putString("VIEWED", "0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0");
            editor.commit();
        }
    }

    public void signInButtonHandler(View view) {
        String username = ((EditText) findViewById(R.id.sign_in_username)).getText().toString();
        String password = ((EditText) findViewById(R.id.sign_in_password)).getText().toString();

        MyDBHandler dbHandler = new MyDBHandler(this, null);
        User current_user = dbHandler.findUserDB(username);

        if (current_user == null) {
            Toast.makeText(LogInActivity.this, "Username Incorrect! Please correct it or sign up first.", Toast.LENGTH_SHORT).show();
            ((EditText) findViewById(R.id.sign_in_username)).setText("");
            ((EditText) findViewById(R.id.sign_in_password)).setText("");
        } else if (password.compareTo(current_user.getPassword()) != 0) {
            Toast.makeText(LogInActivity.this, "Password Incorrect!", Toast.LENGTH_SHORT).show();
            ((EditText) findViewById(R.id.sign_in_username)).setText("");
            ((EditText) findViewById(R.id.sign_in_password)).setText("");
        } else {
            sharedPreferences = getSharedPreferences("SPREF_APP", MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.clear();
            editor.putString("USERNAME", username);
            editor.commit();
            Intent intent = new Intent(LogInActivity.this, MainActivity.class);
            startActivityForResult(intent, 40);
        }
    }

    public void signUpButtonHandler(View view) {
        Intent intent = new Intent(LogInActivity.this, SignUpActivity.class);
        startActivityForResult(intent, 40);
    }
}
