package com.c323proj6.siyixian;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Environment;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    SQLiteDatabase itemsDb = null;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 101) {
            if (resultCode == RESULT_OK) {
                Toast.makeText(this, "Photo has been saved!", Toast.LENGTH_SHORT).show();
            } else if (resultCode == RESULT_CANCELED) {
                Toast.makeText(this, "Photo capture cancelled!", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Failed to take photo!", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initPhotoError();
        createDatabase();
        getCurrentLocation();
    }

    private void getCurrentLocation() {
        LocationManager myLocationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.INTERNET}, 1);
            recreate();
        } else {
            Location location = myLocationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            if (location != null) {
                double lat, lon;
                lat = location.getLatitude();
                lon = location.getLongitude();

                Geocoder myLocation = new Geocoder(MainActivity.this, Locale.getDefault());
                List<Address> myName;
                try {
                    myName = myLocation.getFromLocation(lat, lon, 1);
                    TextView currentName = findViewById(R.id.location_name);
                    currentName.setText(myName.get(0).getFeatureName());
                    Log.i("ADDINFO", myName.get(0).toString());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else
                Toast.makeText(MainActivity.this, "Cannot Find Current Location!", Toast.LENGTH_SHORT).show();
        }
        return;
    }

    public void searchButtonHandler(View view) {
        Intent myIntent = new Intent(MainActivity.this, SearchActivity.class);
        startActivityForResult(myIntent, 40);
    }

    public void saveButtonHandler(View view) {
        Calendar myCal = Calendar.getInstance();
        Date now = myCal.getTime();
        SimpleDateFormat sdf = new SimpleDateFormat("hh:mm:ss a, MM/dd/yyyy");
        String dateString = sdf.format(now);
        EditText message = findViewById(R.id.message);
        TextView location = findViewById(R.id.location_name);

        Log.i("MEE", message.getText().toString());
        itemsDb.execSQL("INSERT INTO noteTable (message, date, location) VALUES('" + message.getText().toString() + "','" + dateString + "','" + location.getText().toString() + "');");
        Toast.makeText(this, "Item Added to Database", Toast.LENGTH_SHORT).show();

        getCurrentLocation();
    }

    public void createDatabase() {
        try {
            itemsDb = this.openOrCreateDatabase("MyNotes", MODE_PRIVATE, null);
            itemsDb.execSQL("CREATE TABLE IF NOT EXISTS noteTable " + "(id integer primary key, message VARCHAR, date VARCHAR, location VARCHAR);");

        } catch (Exception e) {
            Log.v("DB_ERROR", "Error Creating the Database!");
        }
    }

    public void cameraButtonHandler(View view) {
        if (!hasCamera()) {
            view.setEnabled(false);
            Log.v("MEDIAPLAYER", "Camera Not Available!");
        } else {
            Cursor cursor = itemsDb.rawQuery("SELECT * from noteTable", null);
            File mediaFile = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/" + cursor.getCount() + "" + ".jpg");
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            Uri videoURI = Uri.fromFile(mediaFile);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, videoURI);
            startActivityForResult(intent, 101);
        }
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
