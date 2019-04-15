package com.c323proj10.siyixian;

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
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;

public class FlightFragment extends Fragment {

    View myView;
    String restURL = "";
    String jsonData = "";
    ProgressDialog progressDialog;

//    ArrayList<Itinerary> results = new ArrayList<>();
    ArrayList<String> results = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        myView = inflater.inflate(R.layout.fragment_flight, container, false);

        FloatingActionButton search = myView.findViewById(R.id.search_flight);
        search.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View v) {
                String origin = ((EditText) myView.findViewById(R.id.flight_origin)).getText().toString();
                String destination = ((EditText) myView.findViewById(R.id.flight_destination)).getText().toString();
                String departure_date = ((EditText) myView.findViewById(R.id.flight_departure_date)).getText().toString();
                String return_date = ((EditText) myView.findViewById(R.id.flight_return_date)).getText().toString();
                String arrive_by = ((EditText) myView.findViewById(R.id.flight_arrive_by)).getText().toString();
                String return_by = ((EditText) myView.findViewById(R.id.flight_return_by)).getText().toString();
                String adults = ((EditText) myView.findViewById(R.id.flight_adults)).getText().toString();
                String children = ((EditText) myView.findViewById(R.id.flight_children)).getText().toString();
                String infants = ((EditText) myView.findViewById(R.id.flight_infants)).getText().toString();
                String max_price = ((EditText) myView.findViewById(R.id.flight_max_price)).getText().toString();
                String currency = ((EditText) myView.findViewById(R.id.flight_currency)).getText().toString();

                restURL = String.format(getContext().getString(R.string.flight_api), origin, destination, departure_date, return_date, arrive_by, return_by, adults, children, infants, max_price, currency);
                getWebInfo();
            }
        });

        populateResultAirports();

        return myView;
    }

    private void populateResultAirports() {

//        ArrayAdapter<Itinerary> adapter = new MyListAdapter();
//        ListView listView = myView.findViewById(R.id.flight_result);
//        listView.setAdapter(adapter);
        ArrayAdapter<String> myListAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, results.toArray(new String[results.size()]));
        ListView myListViewer = myView.findViewById(R.id.flight_result);
        myListViewer.setAdapter(myListAdapter);
    }

//    private class MyListAdapter extends ArrayAdapter<Itinerary> {
//
//        public MyListAdapter() {
//            super(getActivity(), R.layout.flight_layout, results);
//        }
//
//        @NonNull
//        @Override
//        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
//            //return super.getView(position, convertView, parent);
//
//            View itemView = convertView;
//            if (itemView == null)
//                itemView = getActivity().getLayoutInflater().inflate(R.layout.flight_layout, parent, false);
//
//            Itinerary currentItinerary = results.get(position);
//
//            return itemView;
//        }
//
//    }

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
                JSONObject jsonObject = new JSONObject(jsonData);
                JSONArray jsonArray = jsonObject.getJSONArray("results");
                for (int i = 0 ; i < jsonArray.length(); i++)
                    results.add(jsonArray.get(i).toString());

//                JSONArray jsonArray = new JSONArray(jsonData);
//
//                for (int i = 0; i < jsonArray.length(); i++) {
//                    JSONObject jsonObject = jsonArray.getJSONObject(i);
//                    results.add(new Itinerary(jsonObject.getString("value"), jsonObject.getString("label")));
//                }

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

//class Flight {
//    private String departure, arrive, origin, destination, airline, flight_number, aircraft;
//
//    public Flight(String departure, String arrive, String origin, String destination, String airline, String flight_number, String aircraft) {
//        this.departure = departure;
//        this.arrive = arrive;
//        this.origin = origin;
//        this.destination = destination;
//        this.airline = airline;
//        this.flight_number = flight_number;
//        this.aircraft = aircraft;
//    }
//
//    public String getDeparture() {
//
//        return departure;
//    }
//
//    public String getArrive() {
//        return arrive;
//    }
//
//    public String getOrigin() {
//        return origin;
//    }
//
//    public String getDestination() {
//        return destination;
//    }
//
//    public String getAirline() {
//        return airline;
//    }
//
//    public String getFlight_number() {
//        return flight_number;
//    }
//
//    public String getAircraft() {
//        return aircraft;
//    }
//}
//
//class Itinerary {
//    public Itinerary(String fare) {
//        this.outbound = new ArrayList<>();
//        this.inbound = new ArrayList<>();
//        this.fare = fare;
//    }
//
//    public Itinerary(ArrayList<Flight> outbound, ArrayList<Flight> inbound, String fare) {
//        this.outbound = outbound;
//        this.inbound = inbound;
//        this.fare = fare;
//    }
//
//    private ArrayList<Flight> outbound, inbound;
//    private String outbound_duration;
//
//    public String getOutbound_duration() {
//        return outbound_duration;
//    }
//
//    public String getInbound_duration() {
//        return inbound_duration;
//    }
//
//    private String inbound_duration;
//    private String fare;
//
//    public ArrayList<Flight> getOutbound() {
//        return outbound;
//    }
//
//    public ArrayList<Flight> getInbound() {
//        return inbound;
//    }
//
//    public String getFare() {
//        return fare;
//    }
//}