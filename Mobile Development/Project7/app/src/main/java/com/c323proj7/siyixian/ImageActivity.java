package com.c323proj7.siyixian;

import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
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
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;

public class ImageActivity extends AppCompatActivity {

    ArrayList<MyImage> myImages = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image);

        populateMyImage();
        populateImageData();
    }

    private void populateImageData() {
        Intent myIntent = getIntent();
        try {
            JSONArray jsonArray = new JSONArray(myIntent.getStringExtra("IMAGE"));
            String[] urls = new String[jsonArray.length()];
            for (int i = 0 ; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                myImages.add(new MyImage(jsonObject.getString("caption"), jsonObject.getString("url")));
                urls[i] = jsonObject.getString("url");
                Log.i("URLS", urls[i]);
            }
            getWebInfo(urls);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void populateMyImage() {

        ArrayAdapter<MyImage> adapter = new ImageActivity.MyListAdapter();
        ListView listView = findViewById(R.id.image_list);
        listView.setAdapter(adapter);
    }

    private class MyListAdapter extends ArrayAdapter<MyImage> {

        public MyListAdapter() {
            super(ImageActivity.this, R.layout.image_list_layout, myImages);
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            //return super.getView(position, convertView, parent);

            View itemView = convertView;
            if (itemView == null)
                itemView = getLayoutInflater().inflate(R.layout.image_list_layout, parent, false);

            MyImage currentSection = myImages.get(position);

            TextView itemName = itemView.findViewById(R.id.caption);
            itemName.setText(currentSection.getCaption());
            TextView itemURL = itemView.findViewById(R.id.source_url);
            itemURL.setText(currentSection.getUrl());
            ImageView imageView = itemView.findViewById(R.id.imageView);
            String[] filename = currentSection.getUrl().split("/");
            File imageFile = new File(getFilesDir().getAbsolutePath()+"/"+filename[filename.length-1]);
            Uri uri = Uri.fromFile(imageFile);
            imageView.setImageURI(uri);
            return itemView;
        }

    }

    ArrayList<Bitmap> bitmap = new ArrayList<>();
    ProgressDialog progressDialog;

    private void getWebInfo(String[] urls) {
        progressDialog = ProgressDialog.show(this, "Image Info", "Connecting, Please wait ...", true, true);
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
            new DownloadWebpageTask().execute(urls);
        } else {
            Toast.makeText(ImageActivity.this, "No network connection available!", Toast.LENGTH_SHORT).show();
        }
    }

    private class DownloadWebpageTask extends AsyncTask<String, Integer, ArrayList<Bitmap>> {
        @Override
        protected ArrayList<Bitmap> doInBackground(String... urls) {
            for (String url : urls)
                downloadFromURL(url);
            return bitmap;
        }

        private Bitmap downloadFromURL(String url) {

            //Here is where all the magic happens!
            InputStream is = null;
            URL myUrl = null;
            Bitmap bm = null;
            try {
                myUrl = new URL(url);
                HttpURLConnection conn = (HttpURLConnection) myUrl.openConnection();
                conn.setReadTimeout(1000000);
                conn.setConnectTimeout(1500000);
                //conn.setRequestMethod("GET"); //not needed any more , GET is default
                conn.connect();
                is = conn.getInputStream();
                progressDialog.dismiss();

                StringBuffer result = new StringBuffer();
                BufferedReader reader = new BufferedReader((new InputStreamReader(is, "UTF-8")));
                String line = "";
                while ((line = reader.readLine()) != null) {
                    result.append(line);
                }
                String[] filename = myUrl.getFile().split("/");
                FileOutputStream fos = openFileOutput(filename[filename.length-1], MODE_PRIVATE);
                fos.write(result.toString().getBytes());
                fos.close();

                bm = BitmapFactory.decodeStream(is);
                is.close();
                conn.disconnect();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (ProtocolException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return bm;
        }

        @Override
        protected void onPostExecute(ArrayList<Bitmap> result) {
            super.onPostExecute(result);
        }
    }
}

class MyImage {
    private String caption;
    private String url;

    public Bitmap getBitmap() {
        return bitmap;
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }

    private Bitmap bitmap;

    public MyImage(String caption, String url) {
        this.caption = caption;
        this.url = url;
    }

    public String getCaption() {
        return caption;
    }

    public void setCaption(String caption) {
        this.caption = caption;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}