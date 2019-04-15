package com.siyixian.project2;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashSet;
import java.util.Set;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        populateListViewer();

        handleButtonClickinJava();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == Activity.RESULT_CANCELED) {
            Log.i("ERROR_BACK", "Return Canceled");
            Toast.makeText(MainActivity.this, "Error!", Toast.LENGTH_SHORT).show();
            return;
        }

        switch (requestCode) {
            case 40 :
                populateListViewer();
                break;
        }

    }

    private void handleButtonClickinJava() {
        Button nextButton = findViewById(R.id.addButton);

        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Go to second activity
                Intent myIntent = new Intent(MainActivity.this, AddItems.class);
                //startActivityForResult(myIntent, 40);
                startActivityForResult(myIntent, 40);
            }
        });

        Button dataLog = findViewById(R.id.dataLog);

        dataLog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                populateListLogViewer();
            }
        });

        Button clear = findViewById(R.id.clear);

        clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences myshprefs = getSharedPreferences("SPREF_APP", MODE_PRIVATE);
                SharedPreferences.Editor editor = myshprefs.edit();
                editor.clear();
                editor.commit();

                populateListViewer();
            }
        });
    }

    private void populateListLogViewer() {
        SharedPreferences myshprefs = getSharedPreferences("SPREF_APP", MODE_PRIVATE);
        final Set<String> itemsLog = myshprefs.getStringSet("Items Log", new HashSet<String>());
        final String[] itemsLogs = itemsLog.toArray(new String[itemsLog.size()]);
        //Create an adapter to convert items into Views (ViewItems, UI for each)
        ArrayAdapter<String> myListAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, itemsLogs);
        //Configure listView to accept views through adapter
        final ListView myListViewer = findViewById(R.id.listView);
        myListViewer.setAdapter(myListAdapter);
    }

    private void populateListViewer() {
        //Create a list of items (Strings for Colors)
        final SharedPreferences myshprefs = getSharedPreferences("SPREF_APP", MODE_PRIVATE);
        final Set<String> item = myshprefs.getStringSet("Items", new HashSet<String>());
        final String[] items = item.toArray(new String[item.size()]);
        final Set<String> itemsLog = myshprefs.getStringSet("Items Log", new HashSet<String>());
        final String[] itemsLogs = itemsLog.toArray(new String[itemsLog.size()]);

        final String[] myItems = items;


        //Create an adapter to convert items into Views (ViewItems, UI for each)
        ArrayAdapter<String> myListAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, myItems);
        //Configure listView to accept views through adapter
        final ListView myListViewer = findViewById(R.id.listView);
        myListViewer.setAdapter(myListAdapter);

        Button minusBotton = findViewById(R.id.minusButton);

        minusBotton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Setup onItemClickListener for listview items
                myListViewer.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                        SharedPreferences.Editor editor = myshprefs.edit();
                        String[] newItems = new String[items.length - 1];
                        String[] newItemsLogs = new String[itemsLogs.length - 1];
                        for (int i = 0, j = 0; i < items.length; i++, j++) {
                            if (i == position)
                                j--;
                            else {
                                newItems[j] = items[i];
                                newItemsLogs[j] = itemsLogs[i];
                            }
                        }
                        editor.clear();

                        Set<String> newItem = new HashSet<String>();
                        Set<String> newItemsLog = new HashSet<String>();
                        for (String newItemData : newItems)
                            newItem.add(newItemData);
                        for (String newItemData : newItemsLogs)
                            newItemsLog.add(newItemData);

                        editor.putStringSet("Items", newItem);
                        editor.putStringSet("Items Log", newItemsLog);
                        editor.commit();

                        populateListViewer();
                    }
                });
            }
        });

    }
}

