package com.c323.midtermproject.siyixian;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class RecordedDataActivity extends AppCompatActivity {

    private List<Record> records = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recorded_data);



        ArrayAdapter<Record> adapter = new MyListAdapter();
        ListView listView = findViewById(R.id.all_data_records);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.d("Inside List",""+position);
                SharedPreferences myshprefs = getSharedPreferences("SPREF_APP", MODE_PRIVATE);
                SharedPreferences.Editor editor = myshprefs.edit();
                editor.remove("DATE");
                editor.putString("DATE", records.get(position).getDate());
                editor.commit();

                Intent myIntent = new Intent(RecordedDataActivity.this, MainActivity.class);
                startActivityForResult(myIntent, 40);
            }
        });

        populateRecords();
    }

    private void populateRecords() {
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

            JSONArray jsonArray = new JSONArray(sBuffer.toString());
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                Record record = new Record();
                record.setDate(jsonObject.getString("DATE"));
                record.setSteps(jsonObject.getInt("STEPS"));
                records.add(record);
            }
        } catch (Exception e) {

        }
    }

    private class MyListAdapter extends ArrayAdapter<Record> {

        public MyListAdapter() {super(RecordedDataActivity.this, R.layout.list_view_layout, records); }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            //return super.getView(position, convertView, parent);

            View itemView = convertView;
            if (itemView == null)
                itemView = getLayoutInflater().inflate(R.layout.list_view_layout, parent, false);

            Record currentRecord = records.get(position);

            TextView recordDate = itemView.findViewById(R.id.record_date);
            recordDate.setText(currentRecord.getDate());
            TextView recordSteps = itemView.findViewById(R.id.record_steps);
            recordSteps.setText("Steps: " + currentRecord.getSteps() + "");
            return itemView;
        }

    }
}

class Record {
    private String date;
    private int steps;
    private JSONArray pins;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getSteps() {
        return steps;
    }

    public void setSteps(int steps) {
        this.steps = steps;
    }

    public JSONArray getPins() {
        return pins;
    }

    public void setPins(JSONArray pins) {
        this.pins = pins;
    }
}