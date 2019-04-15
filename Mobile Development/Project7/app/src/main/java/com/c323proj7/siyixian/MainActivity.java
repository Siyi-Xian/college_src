package com.c323proj7.siyixian;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
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

public class MainActivity extends AppCompatActivity {

    ArrayList<Section> mySections = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        populateMySection();
        populateSectionsData();

        getWebInfo();
    }

    private void populateSectionsData() {
        mySections.add(new Section("U.S."));
        mySections.add(new Section("Style"));
        mySections.add(new Section("Climate"));
        mySections.add(new Section("home"));
        mySections.add(new Section("Technology"));
        mySections.add(new Section("World"));

    }

    private void populateMySection() {

        ArrayAdapter<Section> adapter = new MyListAdapter();
        ListView listView = findViewById(R.id.section_list);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //Go to second activity
                Intent myIntent = new Intent(MainActivity.this, SectionDetailActivity.class);
                myIntent.putExtra("SECTION", mySections.get(position).getName());
                startActivityForResult(myIntent, 40);
            }
        });
    }

    private class MyListAdapter extends ArrayAdapter<Section> {

        public MyListAdapter() {
            super(MainActivity.this, R.layout.section_list_layout, mySections);
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            //return super.getView(position, convertView, parent);

            View itemView = convertView;
            if (itemView == null)
                itemView = getLayoutInflater().inflate(R.layout.section_list_layout, parent, false);

            Section currentSection = mySections.get(position);

            TextView itemName = itemView.findViewById(R.id.section);
            itemName.setText(currentSection.getName());
            return itemView;
        }

    }


    private final String restURL = "https://api.nytimes.com/svc/topstories/v2/home.json?api-key=2a904cefea37404d9bf4fe6b529e42ec";
    String jsonData = "";
    ProgressDialog progressDialog;

    private void getWebInfo() {
        progressDialog = ProgressDialog.show(this, "Article Info", "Connecting, Please wait ...", true, true);
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
            //Here is where all the fun happens!
            //new DownloadWebpageTask().execute(restURL);
            new DownloadWebpageTask().execute(restURL);

        } else {
            Toast.makeText(MainActivity.this, "No network connection available!", Toast.LENGTH_SHORT).show();
        }
    }

    private class DownloadWebpageTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... urls) {
            jsonData = downloadFromURL(urls[0]);

            try {
                JSONObject jsonObject = new JSONObject(jsonData);
                JSONArray jsonArray = jsonObject.getJSONArray("results");

                FileOutputStream fos = openFileOutput("article.json", MODE_PRIVATE);
                fos.write(jsonArray.toString().getBytes());
                fos.close();
//                Log.i("JSON", "Success: " + getFilesDir().getAbsolutePath());
            } catch (IOException e) {
                e.printStackTrace();
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
                Log.i("ARTICLE", "The response is: " + response);
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
            super.onPostExecute(result);
        }
    }
}

class Section {
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    private String name;

    public Section(String name) {
        this.name = name;
    }
}