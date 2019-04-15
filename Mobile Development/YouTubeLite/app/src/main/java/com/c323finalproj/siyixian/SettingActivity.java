package com.c323finalproj.siyixian;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.CompoundButton;
import android.widget.Switch;

public class SettingActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        Switch location = findViewById(R.id.location_switch);
        User user = (new MyDBHandler(SettingActivity.this, null)).findUserDB(getSharedPreferences("SPREF_APP", MODE_PRIVATE).getString("USERNAME", "admin"));
        if (!user.getLocation().equals("FALSE"))
            location.setChecked(true);
        location.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    String location = getSharedPreferences("LOCATION", MODE_PRIVATE).getString("LOCATION", "Bloomington");
                    String username = getSharedPreferences("SPREF_APP", MODE_PRIVATE).getString("USERNAME", "admin");
                    MyDBHandler myDBHandler = new MyDBHandler(SettingActivity.this, null);
                    myDBHandler.updateLocationTrue(username);
                    myDBHandler.updateLocation(location, username);
                }
                else {
                    String username = getSharedPreferences("SPREF_APP", MODE_PRIVATE).getString("USERNAME", "admin");
                    MyDBHandler myDBHandler = new MyDBHandler(SettingActivity.this, null);
                    myDBHandler.updateLocationFalse(username);
                }
            }
        });

        Switch shake = findViewById(R.id.shake_to_pause_switch);
        shake.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

            }
        });

        Switch screen_time = findViewById(R.id.track_screen_time_switch);
        screen_time.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

            }
        });
    }
}
