package com.c323.midtermproject.siyixian;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class HomeFragment extends Fragment {

    View myView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        //return super.onCreateView(inflater, container, savedInstanceState);
        myView = inflater.inflate(R.layout.home_layout, container, false);

        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        StepViewFragment stepViewFragment = new StepViewFragment();
        fragmentTransaction.replace(R.id.step_view, stepViewFragment);
        fragmentTransaction.commit();

        Calendar myCal = Calendar.getInstance();
        Date now = myCal.getTime();
        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
        String dateString = sdf.format(now);

        SharedPreferences myshprefs = getActivity().getSharedPreferences("SPREF_APP", Context.MODE_PRIVATE);
        dateString = myshprefs.getString("DATE", dateString);

        TextView date = myView.findViewById(R.id.date);
        date.setText(dateString);

        Button all_data = myView.findViewById(R.id.show_all_data);
        all_data.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(getActivity(), RecordedDataActivity.class);
                startActivityForResult(myIntent, 40);
            }
        });

        Button map_view = myView.findViewById(R.id.path_today);
        map_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(getActivity(), MapsActivity.class);
                startActivityForResult(myIntent, 40);
            }
        });

        TextView avg_week = myView.findViewById(R.id.week_avg);
        avg_week.setText("Avg this week: " + calcAvg(dateString, 7) + "" + " steps");
        TextView avg_month = myView.findViewById(R.id.month_avg);
        avg_month.setText("Avg this week: " + calcAvg(dateString, 30) + "" + " steps");
        TextView avg_year = myView.findViewById(R.id.year_avg);
        avg_year.setText("Avg this week: " + calcAvg(dateString, 365) + "" + " steps");

        return myView;
    }

    private int calcAvg(String date, int period) {
        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
        int total_steps = 0;
        int total_days = 0;
        try {
            String start = sdf.format(new Date(sdf.parse(date).getTime() - 1000 * 60 * 60 * 24 * period));

            SharedPreferences myshprefs = getActivity().getSharedPreferences("SPREF_APP", Context.MODE_PRIVATE);
            JSONObject user = new JSONObject(myshprefs.getString("USER", ""));
            String fileName = user.getString("USERNAME") + ".json";

            FileInputStream fis = getActivity().openFileInput(fileName);
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
                if ((sdf.parse(start).before(sdf.parse(jsonArray.getJSONObject(i).getString("DATE"))) &&
                        sdf.parse(date).after(sdf.parse(jsonArray.getJSONObject(i).getString("DATE")))) ||
                        date.compareTo(jsonArray.getJSONObject(i).getString("DATE")) == 0) {
                    total_days++;
                    total_steps += jsonArray.getJSONObject(i).getInt("STEPS");
                }
            }
            try {
                return total_steps / total_days;
            } catch (Exception e) {
                return 0;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return 0;
    }
}
