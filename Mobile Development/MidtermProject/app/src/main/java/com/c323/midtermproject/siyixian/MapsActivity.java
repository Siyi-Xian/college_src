package com.c323.midtermproject.siyixian;

import android.content.SharedPreferences;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        TextView pathDate = findViewById(R.id.path_today);
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        JSONArray pinArray = new JSONArray();
        SharedPreferences myshprefs = getSharedPreferences("SPREF_APP", MODE_PRIVATE);
        try {
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

            pinArray = new JSONArray(sBuffer.toString());
        } catch (Exception e) {
        }


        String date = myshprefs.getString("DATE", new SimpleDateFormat("MM/dd/yyyy").format(Calendar.getInstance().getTime()));
        TextView dateText = findViewById(R.id.path_date);
        dateText.setText(date);
        for (int i = 0; i < pinArray.length(); i++) {
            try {
                if (pinArray.getJSONObject(i).getString("DATE").compareTo(date) == 0) {
                    JSONObject pins = pinArray.getJSONObject(i).getJSONObject("PINS");
                    for (int j = 1; true; j++) {
                        String tagLocation = "PIN" + j + "";
                        String tagTime = "TIME" + j + "";
                        String[] location;
                        String time;
                        try {
                            location = pins.getString(tagLocation).split(",");
                            time = pins.getString(tagTime);
                        } catch (JSONException e1) {
                            break;
                        }
                        LatLng newMarker = new LatLng(Double.parseDouble(location[1]), Double.parseDouble(location[0]));
                        mMap.addMarker(new MarkerOptions().position(newMarker).title(time));
                        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(newMarker, 13));
                    }
                }
            } catch (JSONException e) {

            }

        }

        // Add a marker in Sydney and move the camera
//        LatLng sydney = new LatLng(39.17250897, -86.48835601);
//        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
//        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
    }
}
