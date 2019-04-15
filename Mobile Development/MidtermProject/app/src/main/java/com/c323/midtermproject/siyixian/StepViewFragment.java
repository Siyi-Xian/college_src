package com.c323.midtermproject.siyixian;

import android.app.Fragment;
import android.content.Context;
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
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class StepViewFragment extends Fragment {

    View myView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        //return super.onCreateView(inflater, container, savedInstanceState);
        myView = inflater.inflate(R.layout.step_view_layout, container, false);

        Calendar myCal = Calendar.getInstance();
        final Date now = myCal.getTime();
        final SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
        String dateString = sdf.format(now);
        try {
            SharedPreferences myshprefs = getActivity().getSharedPreferences("SPREF_APP", Context.MODE_PRIVATE);
            JSONObject user = new JSONObject(myshprefs.getString("USER", ""));
            String fileName = user.getString("USERNAME") + ".json";
            dateString = myshprefs.getString("DATE", dateString);

            FileInputStream fis = getActivity().openFileInput(fileName);
            BufferedInputStream bis = new BufferedInputStream(fis);
            StringBuffer sBuffer = new StringBuffer();
            while (bis.available() != 0) {
                char c = (char) bis.read();
                sBuffer.append(c);
            }
            bis.close();
            fis.close();

            TextView steps = myView.findViewById(R.id.step_number);

            JSONArray jsonArray = new JSONArray(sBuffer.toString());
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                if (dateString.compareTo(jsonObject.getString("DATE")) == 0) {
                    steps.setText(jsonObject.getInt("STEPS") + "" + " Steps");
                }
            }
        } catch (Exception e) {

        }

        final Button next_date = myView.findViewById(R.id.next_day);
        final String nextDateString = dateString;
        next_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    if (sdf.parse(nextDateString).before(now)) {
                        String next_day = findDate(sdf.parse(nextDateString), 1000 * 60 * 60 * 24);
                        SharedPreferences myshprefs = getActivity().getSharedPreferences("SPREF_APP", Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = myshprefs.edit();
                        editor.remove("DATE");
                        editor.putString("DATE", next_day);
                        editor.commit();

                        getActivity().recreate();
                    } else {
                        Toast.makeText(getActivity(), "This is the last day recorded!", Toast.LENGTH_SHORT).show();
                    }
                } catch (ParseException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });

        final Button previous_date = myView.findViewById(R.id.previous_day);
        final String previousDateString = dateString;
        previous_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    String previous_day = findDate(sdf.parse(previousDateString), -1000 * 60 * 60 * 24);
                    if (previous_day.compareTo("") != 0) {
                        SharedPreferences myshprefs = getActivity().getSharedPreferences("SPREF_APP", Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = myshprefs.edit();
                        editor.remove("DATE");
                        editor.putString("DATE", previous_day);
                        editor.commit();

                        getActivity().recreate();
                    } else {
                        Toast.makeText(getActivity(), "This is the first day recorded!", Toast.LENGTH_SHORT).show();
                    }
                } catch (ParseException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });

        return myView;
    }

    private String findDate(Date current, int delta) throws JSONException, IOException {
        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
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

        TextView steps = myView.findViewById(R.id.step_number);

        JSONArray jsonArray = new JSONArray(sBuffer.toString());
        for (int j = 0; j < 365 * 10; j++) {
            current = new Date(current.getTime() + delta);
            String date_now = sdf.format(current);
            for (int i = 0; i < jsonArray.length(); i++) {
                if (date_now.compareTo(jsonArray.getJSONObject(i).getString("DATE")) == 0)
                    return date_now;
            }
        }
        return "";
    }

}

