package com.c323.midtermproject.siyixian;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
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
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.EventListener;

public class MainActivity extends AppCompatActivity {

    private TextView mTextMessage;
    private final static String SIGNIN_FILE = "user.json";
    SensorManager mySensorManager;
    Sensor mySensor;
    LocationManager myLocationManager;
    LocationListener myLocationListener;
    float steps;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    HomeFragment homeFragment = new HomeFragment();
                    fragmentTransaction.replace(R.id.main_layout, homeFragment);
                    fragmentTransaction.commit();
                    return true;
                case R.id.navigation_Profile:
                    SettingFragment profileFragment = new SettingFragment();
                    Bundle profileBundle = new Bundle();
                    profileBundle.putString("MESSAGE", "PROFILE");
                    profileFragment.setArguments(profileBundle);
                    fragmentTransaction.replace(R.id.main_layout, profileFragment);
                    fragmentTransaction.commit();
                    return true;
                case R.id.navigation_about_me:
                    SettingFragment aboutMeFragment = new SettingFragment();
                    Bundle aboutMeBundle = new Bundle();
                    aboutMeBundle.putString("MESSAGE", "ABOUT_ME");
                    aboutMeFragment.setArguments(aboutMeBundle);
                    fragmentTransaction.replace(R.id.main_layout, aboutMeFragment);
                    fragmentTransaction.commit();
                    return true;
                case R.id.navigation_download:

                    try {
                        SharedPreferences myshprefs = getSharedPreferences("SPREF_APP", MODE_PRIVATE);
                        JSONObject user = new JSONObject(myshprefs.getString("USER", ""));
                        String fileName = user.getString("USERNAME") + ".json";

                        FileInputStream fis = openFileInput(fileName);
                        BufferedInputStream bis = new BufferedInputStream(fis);
                        StringBuffer sBuffer = new StringBuffer();
                        while (bis.available() != 0) {
                            char c = (char) bis.read();
                            sBuffer.append(c);
                        }
                        bis.close();
                        fis.close();

                        File externalDir = getExternalCacheDir(); /*saves in cache folder*/
                        //externalDir = getExternalFilesDir(null); /*saves in files folder*/
                        String myExternalFilePath = getExternalCacheDir().getAbsolutePath();
                        File myFile = new File(externalDir, fileName);
                        //FileOutputStream fos = openFileOutput(JSON_FILE_NAME, MODE_PRIVATE);
                        FileOutputStream fos = new FileOutputStream(myFile);
                        fos.write(sBuffer.toString().getBytes());
                        fos.close();
                        Toast.makeText(MainActivity.this, "JSONFile written in External Storage:\n" + myExternalFilePath, Toast.LENGTH_SHORT).show();
                    } catch (Exception e) {
                        try {
                            SharedPreferences myshprefs = getSharedPreferences("SPREF_APP", MODE_PRIVATE);
                            JSONObject user = new JSONObject(myshprefs.getString("USER", ""));
                            String fileName = user.getString("USERNAME") + ".json";
                            File externalDir = getExternalCacheDir(); /*saves in cache folder*/
                            //externalDir = getExternalFilesDir(null); /*saves in files folder*/
                            String myExternalFilePath = getExternalCacheDir().getAbsolutePath();
                            File myFile = new File(externalDir, fileName);
                            //FileOutputStream fos = openFileOutput(JSON_FILE_NAME, MODE_PRIVATE);
                            FileOutputStream fos = new FileOutputStream(myFile);
                            fos.write("".toString().getBytes());
                            fos.close();
                            Toast.makeText(MainActivity.this, "JSONFile written in External Storage:\n" + myExternalFilePath, Toast.LENGTH_SHORT).show();
                        } catch (Exception e2) {

                        }
                    }
                    return true;
                case R.id.navigation_setting:
                    SettingFragment settingFragment = new SettingFragment();
                    Bundle settingBundle = new Bundle();
                    settingBundle.putString("MESSAGE", "SETTING");
                    settingFragment.setArguments(settingBundle);
                    fragmentTransaction.replace(R.id.main_layout, settingFragment);
                    fragmentTransaction.commit();
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mTextMessage = (TextView) findViewById(R.id.message);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        navigation.getMenu().getItem(2).setChecked(true);

        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        HomeFragment homeFragment = new HomeFragment();
        fragmentTransaction.replace(R.id.main_layout, homeFragment);
        fragmentTransaction.commit();

//        mySensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
//        mySensor = mySensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER);
//        if (mySensorManager.getSensorList(Sensor.TYPE_STEP_COUNTER).size() == 0) {
//            Toast.makeText(this, "Device does not supoort step counter!", Toast.LENGTH_SHORT).show();
//            Intent myIntent = new Intent();
//            setResult(Activity.RESULT_OK, myIntent);
//            finish();
//        }
//        else {
//            // Step Counter
//            mySensorManager.registerListener(new SensorEventListener() {
//                @Override
//                public void onSensorChanged(SensorEvent event) {
//                    steps = event.values[0];
//
//                    LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
//                    if (ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
//                        ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
//                    }
//                    Location location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
//                    double lat, lon;
//                    lat = location.getLatitude();
//                    lon = location.getLongitude();
//
//                    Calendar myCal = Calendar.getInstance();
//                    Date now = myCal.getTime();
//                    SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
//                    String dateString = sdf.format(now);
//                    sdf = new SimpleDateFormat("hh:mm:ss a");
//                    String timeString = sdf.format(now);
//
//                    try {
//                        SharedPreferences myshprefs = getSharedPreferences("SPREF_APP", MODE_PRIVATE);
//                        JSONObject user = new JSONObject(myshprefs.getString("USER", ""));
//                        String fileName = user.getString("USERNAME") + ".json";
//
//                        FileInputStream fis = openFileInput(fileName);
//                        BufferedInputStream bis = new BufferedInputStream(fis);
//                        StringBuffer sBuffer = new StringBuffer();
//                        while (bis.available() != 0) {
//                            char c = (char) bis.read();
//                            sBuffer.append(c);
//                        }
//                        bis.close();
//                        fis.close();
//
//                        JSONArray jsonArray = new JSONArray(sBuffer.toString());
//                        for (int i = 0; i < jsonArray.length(); i++) {
//                            JSONObject jsonObject = jsonArray.getJSONObject(i);
//                            if (dateString.compareTo(jsonObject.getString("DATE")) == 0) {
//                                jsonArray.getJSONObject(i).remove("STEPS");
//                                jsonArray.getJSONObject(i).put("STEPS", steps + "");
//                                for (int p = 1; true; i++)
//                                    try {
//                                        jsonObject.getJSONObject("PINS").get("PIN" + p + "");
//                                    } catch (JSONException je) {
//                                        jsonObject.getJSONObject("PINS").put("PIN" + p + "", lon + "" + "," + lat + "");
//                                        return;
//                                    }
//                            }
//                        }
//                    } catch (Exception e) {
//
//                    }
//
//                }
//
//                @Override
//                public void onAccuracyChanged(Sensor sensor, int accuracy) {
//
//                }
//            }, mySensor, 1000 * 60 * 60 * 5);
//        }
    }

    public void saveProfileData(View view) throws JSONException, IOException {
        EditText height = findViewById(R.id.profile_height_edit);
        EditText weight = findViewById(R.id.profile_weight_edit);
        EditText bloodGroup = findViewById(R.id.profile_blood_type_edit);
        EditText emergencyContact = findViewById(R.id.profile_emergency_contact_edit);

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

        SharedPreferences myshprefs = getSharedPreferences("SPREF_APP", Context.MODE_PRIVATE);
        for (int i = 0; i < jsonArray.length(); i++) {
            if (jsonArray.getJSONObject(i).toString().compareTo(myshprefs.getString("USER", "")) == 0) {
                jsonArray.getJSONObject(i).remove("HEIGHT");
                jsonArray.getJSONObject(i).remove("WEIGHT");
                jsonArray.getJSONObject(i).remove("BLOOD_GROUP");
                jsonArray.getJSONObject(i).remove("EMERGENCY_CONTACT");
                jsonArray.getJSONObject(i).put("HEIGHT", height.getText().toString());
                jsonArray.getJSONObject(i).put("WEIGHT", weight.getText().toString());
                jsonArray.getJSONObject(i).put("BLOOD_GROUP", bloodGroup.getText().toString());
                jsonArray.getJSONObject(i).put("EMERGENCY_CONTACT", emergencyContact.getText().toString());

                SharedPreferences.Editor editor = myshprefs.edit();
                editor.clear();
                editor.putString("USER", jsonArray.getJSONObject(i).toString());
                editor.commit();
            }
        }

        FileOutputStream fos = openFileOutput(SIGNIN_FILE, MODE_PRIVATE);
        fos.write(jsonArray.toString().getBytes());
        fos.close();

        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        SettingFragment profileFragment = new SettingFragment();
        Bundle profileBundle = new Bundle();
        profileBundle.putString("MESSAGE", "PROFILE");
        profileFragment.setArguments(profileBundle);
        fragmentTransaction.replace(R.id.main_layout, profileFragment);
        fragmentTransaction.commit();
    }
}
