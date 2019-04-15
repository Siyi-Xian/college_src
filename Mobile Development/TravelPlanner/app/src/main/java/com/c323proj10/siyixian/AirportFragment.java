package com.c323proj10.siyixian;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.app.Fragment;
import android.app.ProgressDialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.design.widget.FloatingActionButton;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;

public class AirportFragment extends Fragment {

    View myView;
    String restURL = "";
    String jsonData = "";
    ProgressDialog progressDialog;

    ArrayList<Airport> results = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        myView = inflater.inflate(R.layout.fragment_airport, container, false);

        FloatingActionButton search = myView.findViewById(R.id.search_airport);
        search.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View v) {
                String term = ((EditText) myView.findViewById(R.id.airport_search_terms)).getText().toString();
                String country = ((EditText) myView.findViewById(R.id.airport_country)).getText().toString();

                restURL = String.format(getContext().getString(R.string.airport_api), term, country);
                getWebInfo();
            }
        });

        populateResultAirports();

        return myView;
    }

    private void populateResultAirports() {

        ArrayAdapter<Airport> adapter = new MyListAdapter();
        ListView listView = myView.findViewById(R.id.airport_result);
        listView.setAdapter(adapter);
    }

    private class MyListAdapter extends ArrayAdapter<Airport> {

        public MyListAdapter() {
            super(getActivity(), R.layout.airport_layout, results);
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            //return super.getView(position, convertView, parent);

            View itemView = convertView;
            if (itemView == null)
                itemView = getActivity().getLayoutInflater().inflate(R.layout.airport_layout, parent, false);

            Airport currentAirpot = results.get(position);

            ((TextView) itemView.findViewById(R.id.airport_value)).setText(currentAirpot.getValue());
            ((TextView) itemView.findViewById(R.id.airport_label)).setText(currentAirpot.getLabel());
            return itemView;
        }

    }

    private void getWebInfo() {
        progressDialog = ProgressDialog.show(getActivity(), "Search Info", "Connecting, Please wait ...", true, true);
        ConnectivityManager connectivityManager = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
            new DownloadWebpageTask().execute(restURL);
        } else {
            Toast.makeText(getActivity(), "No network connection available!", Toast.LENGTH_SHORT).show();
        }
    }

    private class DownloadWebpageTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... urls) {
            jsonData = downloadFromURL(urls[0]);

            try {
                JSONArray jsonArray = new JSONArray(jsonData);

                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    results.add(new Airport(jsonObject.getString("value"), jsonObject.getString("label")));
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }

            return jsonData;
        }

        private String downloadFromURL(String url) {

            //Here is where all the magic happens!
            InputStream is = null;
            StringBuffer result = new StringBuffer();
            URL myUrl = null;
            try {
                myUrl = new URL(url);
                HttpURLConnection conn = (HttpURLConnection) myUrl.openConnection();
                conn.setReadTimeout(10000);
                conn.setConnectTimeout(15000);
                conn.setRequestMethod("GET"); //not needed any more , GET is default
                conn.connect();
                int response = conn.getResponseCode();
                Log.i("AIRPORT", "The response is: " + response);
                is = conn.getInputStream();
                progressDialog.dismiss();

                BufferedReader reader = new BufferedReader((new InputStreamReader(is, "UTF-8")));
                String line = "";
                while ((line = reader.readLine()) != null) {
                    result.append(line);
                }

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (ProtocolException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return result.toString();
        }

        @Override
        protected void onPostExecute(String result) {
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    populateResultAirports();
                }
            });
            super.onPostExecute(result);
        }
    }
}

class Airport {
    private String value;
    private String label;

    public Airport(String value, String label) {
        this.value = value;
        this.label = label;
    }

    public String getValue() {

        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }
}