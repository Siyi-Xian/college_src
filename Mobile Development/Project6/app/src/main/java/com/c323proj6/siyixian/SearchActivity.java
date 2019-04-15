package com.c323proj6.siyixian;

import android.Manifest;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class SearchActivity extends AppCompatActivity {

    private List<MyNote> myNotes = new ArrayList<>();
    SQLiteDatabase itemsDb = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        itemsDb = this.openOrCreateDatabase("MyNotes", MODE_PRIVATE, null);

        populateMyNote();
        displayDbItems();
    }

    private void populateMyNote() {

        ArrayAdapter<MyNote> adapter = new MyListAdapter();
        ListView listView = findViewById(R.id.note_list);
        listView.setAdapter(adapter);
    }

    public void displayDbItems() {

        //Let's use Cursor to get read/write access to db results
        Cursor cursor = itemsDb.rawQuery("SELECT * from noteTable", null);
        int indexColumn_id = cursor.getColumnIndex("id");
        int indexColumn_message = cursor.getColumnIndex("message");
        int indexColumn_date = cursor.getColumnIndex("date");
        int indexColumn_location = cursor.getColumnIndex("location");
        cursor.moveToFirst();

        if (cursor != null && cursor.getCount() > 0) {
            do {
                int id = cursor.getInt(indexColumn_id);
                String message = cursor.getString(indexColumn_message);
                String date = cursor.getString(indexColumn_date);
                String location = cursor.getString(indexColumn_location);
                SimpleDateFormat sdf = new SimpleDateFormat("hh:mm:ss a, MM/dd/yyyy");

                Log.i("DBITEMS", date);

                try {
                    myNotes.add(new MyNote(message, sdf.parse(date), location, id));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            } while (cursor.moveToNext());
        } else {
            Toast.makeText(this, "No items to display!", Toast.LENGTH_SHORT).show();
        }
    }

    public void populateDatabaseKeyWords(String column, String key_words) {
        //Let's use Cursor to get read/write access to db results
        Cursor cursor = itemsDb.rawQuery("SELECT * from noteTable WHERE " + column + " LIKE '%" + key_words + "%'", null);
        int indexColumn_id = cursor.getColumnIndex("id");
        int indexColumn_message = cursor.getColumnIndex("message");
        int indexColumn_date = cursor.getColumnIndex("date");
        int indexColumn_location = cursor.getColumnIndex("location");
        cursor.moveToFirst();

        if (cursor != null && cursor.getCount() > 0) {
            do {
                int id = cursor.getInt(indexColumn_id);
                String message = cursor.getString(indexColumn_message);
                String date = cursor.getString(indexColumn_date);
                String location = cursor.getString(indexColumn_location);
                SimpleDateFormat sdf = new SimpleDateFormat("hh:mm:ss a, MM/dd/yyyy");

                Log.i("DBITEMS", date);

                try {
                    myNotes.add(new MyNote(message, sdf.parse(date), location, id));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            } while (cursor.moveToNext());
        }
    }

    public void searchValueButtonHandler(View view) {

        EditText key_words = findViewById(R.id.search_value);
        String key = key_words.getText().toString();

        myNotes = new ArrayList<>();

        populateDatabaseKeyWords("date", key);
        populateDatabaseKeyWords("location", key);

        if (myNotes.size() == 0)
            Toast.makeText(this, "No items to display!", Toast.LENGTH_SHORT).show();

        populateMyNote();
    }

    private class MyListAdapter extends ArrayAdapter<MyNote> {

        public MyListAdapter() {
            super(SearchActivity.this, R.layout.list_view_layout, myNotes);
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            //return super.getView(position, convertView, parent);

            View itemView = convertView;
            if (itemView == null)
                itemView = getLayoutInflater().inflate(R.layout.list_view_layout, parent, false);

            MyNote currentNote = myNotes.get(position);

            TextView message = itemView.findViewById(R.id.list_message);
            message.setText(currentNote.getMessage());
            TextView location = itemView.findViewById(R.id.list_location);
            location.setText(currentNote.getLocation());
            TextView date = itemView.findViewById(R.id.list_date_time);
            SimpleDateFormat sdf = new SimpleDateFormat("hh:mm:ss a, MM-dd-yyyy");
            date.setText(sdf.format(currentNote.getDate()));

            if (ActivityCompat.checkSelfPermission(SearchActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(SearchActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
            } else {
                try {
                    ImageView photo = itemView.findViewById(R.id.list_picture);
                    File mediaFile = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/" + (currentNote.getId() - 1) + "" + ".jpg");
                    Uri imageURI = Uri.fromFile(mediaFile);
                    photo.setImageURI(imageURI);
                } catch (Exception e) {

                }
            }

            return itemView;
        }

    }
}

class MyNote {
    private String message;
    private Date date;
    private String location;
    private int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public MyNote(String message, Date date, String location, int id) {
        this.message = message;
        this.date = date;
        this.location = location;
        this.id = id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}
