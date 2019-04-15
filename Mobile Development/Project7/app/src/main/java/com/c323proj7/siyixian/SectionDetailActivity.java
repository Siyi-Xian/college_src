package com.c323proj7.siyixian;

import android.content.Context;
import android.content.Intent;
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;

public class SectionDetailActivity extends AppCompatActivity {

    ArrayList<Article> myArticles = new ArrayList<>();
    JSONArray jsonArray = new JSONArray();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_section_detail);

        Intent myIntent = getIntent();
        String section = myIntent.getStringExtra("SECTION");

        try {
            FileInputStream fis = openFileInput("article.json");
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
            e.printStackTrace();
        }

        populateMySection();
        populateArticleData(section);
    }

    private void populateArticleData(String section) {
        try {
            Log.i("ARTICLE", jsonArray.length() + "");
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
//                Log.i("ARTICLE", jsonObject.toString());
                if (jsonObject.getString("section").compareTo(section) == 0) {
                    String title = jsonObject.getString("title");
                    String abstra = jsonObject.getString("abstract");
                    String picture = jsonObject.getJSONArray("multimedia").toString();
                    myArticles.add(new Article(title, abstra, picture));
//                    Log.i("ARTICLE", title);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void populateMySection() {

        ArrayAdapter<Article> adapter = new SectionDetailActivity.MyListAdapter();
        ListView listView = findViewById(R.id.article_list);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //Go to second activity
                Intent myIntent = new Intent(SectionDetailActivity.this, ImageActivity.class);
                myIntent.putExtra("IMAGE", myArticles.get(position).getJsonArray().toString());
                startActivityForResult(myIntent, 40);
            }
        });
    }

    private class MyListAdapter extends ArrayAdapter<Article> {

        public MyListAdapter() {
            super(SectionDetailActivity.this, R.layout.article_list_layout, myArticles);
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            //return super.getView(position, convertView, parent);

            View itemView = convertView;
            if (itemView == null)
                itemView = getLayoutInflater().inflate(R.layout.article_list_layout, parent, false);

            Article currentSection = myArticles.get(position);

            TextView itemName = itemView.findViewById(R.id.article_title);
            itemName.setText(currentSection.getName());
            TextView itemAbstract = itemView.findViewById(R.id.abstract_text);
            itemAbstract.setText(currentSection.getAbstra());
            return itemView;
        }

    }
}

class Article {
    private String name;
    private String abstra;

    public String getJsonArray() {
        return jsonArray;
    }

    public void setJsonArray(String jsonArray) {
        this.jsonArray = jsonArray;
    }

    private String jsonArray;

    public Article(String name, String abstra, String jsonArray) {
        this.name = name;
        this.abstra = abstra;
        this.jsonArray = jsonArray;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAbstra() {
        return abstra;
    }

    public void setAbstra(String abstra) {
        this.abstra = abstra;
    }
}
