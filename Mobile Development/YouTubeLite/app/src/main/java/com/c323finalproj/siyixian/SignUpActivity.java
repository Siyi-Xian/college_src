package com.c323finalproj.siyixian;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.VectorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import java.io.ByteArrayOutputStream;

public class SignUpActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
    }

    public void signUpButton(View view) {
        String username = ((EditText) findViewById(R.id.sign_up_username)).getText().toString();
        String password = ((EditText) findViewById(R.id.sign_up_password)).getText().toString();
        String name = ((EditText) findViewById(R.id.sign_up_name)).getText().toString();
        String email = ((EditText) findViewById(R.id.sign_up_email)).getText().toString();
        String phone_number = ((EditText) findViewById(R.id.sign_up_phone_number)).getText().toString();

        MyDBHandler dbHandler = new MyDBHandler(this, null);
        dbHandler.signUpUser(username, password, name, email, phone_number);


        Bitmap bitmap = ((BitmapDrawable) getResources().getDrawable(R.mipmap.ic_launcher)).getBitmap();
        dbHandler.setColumnImage(bitmap, username);

        dbHandler.updateLocation("FALSE", username);

        Intent myIntent = new Intent();
        setResult(Activity.RESULT_OK, myIntent);
        finish();
    }
}
