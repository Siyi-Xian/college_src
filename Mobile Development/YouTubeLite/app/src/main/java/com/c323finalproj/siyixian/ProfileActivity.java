package com.c323finalproj.siyixian;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.io.File;

public class ProfileActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        initPhotoError();
    }

    public void updateInformation(View view) {
        String name = ((EditText) findViewById(R.id.profile_name)).getText().toString();
        String email = ((EditText) findViewById(R.id.profile_email)).getText().toString();
        String password = ((EditText) findViewById(R.id.profile_password)).getText().toString();
        String phone = ((EditText) findViewById(R.id.proflie_phone)).getText().toString();
        String username = getSharedPreferences("SPREF_APP", MODE_PRIVATE).getString("USERNAME", "");

        MyDBHandler myDBHandler = new MyDBHandler(this, null);
        myDBHandler.updateProfile(name, email, password, phone, username);

        Intent myIntent = new Intent();
        setResult(Activity.RESULT_OK, myIntent);
        finish();

//        try {
//            Bitmap bitmap = BitmapFactory.decodeFile(Environment.getExternalStorageDirectory().getAbsolutePath() + "/temp.jpg");
//            myDBHandler.setColumnImage(bitmap, username);
//        } catch (Exception e) {
//
//        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 101) {
            if (resultCode == RESULT_OK) {
                String username = getSharedPreferences("SPREF_APP", MODE_PRIVATE).getString("USERNAME", "");
                Toast.makeText(this, "Photo has been saved!", Toast.LENGTH_SHORT).show();
                MyDBHandler myDBHandler = new MyDBHandler(this, null);
                Bitmap bitmap = BitmapFactory.decodeFile(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM).getAbsolutePath() + "/temp.jpg", getBitmapOption(2));
                myDBHandler.setColumnImage(bitmap, username);
            } else if (resultCode == RESULT_CANCELED) {
                Toast.makeText(this, "Photo capture cancelled!", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Failed to take photo!", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private BitmapFactory.Options getBitmapOption(int inSampleSize){
        System.gc();
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inPurgeable = true;
        options.inSampleSize = inSampleSize;
        return options;
    }

    public void cameraButtonHandler(View view) {
        if (!hasCamera()) {
            Log.v("MEDIAPLAYER", "Camera Not Available!");
        } else {
            File mediaFile = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM).getAbsolutePath() + "/temp.jpg");
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            Uri photoUri = FileProvider.getUriForFile(
                    this,
                    getPackageName() + ".fileprovider",
                    mediaFile);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri);
            startActivityForResult(intent, 101);
        }
//        File mediaFile = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/temp.jpg");
//        if (!hasCamera()) {
//            view.setEnabled(false);
//            Log.v("MEDIAPLAYER", "Camera Not Available!");
//        } else {
//            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//            Uri videoURI = Uri.fromFile(mediaFile);
//            intent.putExtra(MediaStore.EXTRA_OUTPUT, videoURI);
//            startActivityForResult(intent, 101);
//        }
    }

    private boolean hasCamera() {
        if (getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA_ANY)) {
            return true;
        } else {
            return false;
        }
    }

    private void initPhotoError() {
        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());
        builder.detectFileUriExposure();
    }
}
