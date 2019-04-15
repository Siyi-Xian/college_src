package com.c323proj8.siyixian;

import android.Manifest;
import android.content.ContentProvider;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity {

    List<Contact> myContact = new ArrayList<>();
    SQLiteDatabase itemsDb = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.READ_CONTACTS}, 1);
            recreate();
        }
        if (ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.WRITE_CONTACTS) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.WRITE_CONTACTS}, 1);
            recreate();
        }
        if (ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.CALL_PHONE}, 1);
            recreate();
        }

        populateMyContact();
        populateContactInformation(null);
    }

    public void searchContact(View view) {
        EditText seach_key = findViewById(R.id.contact_search_text);
        String key = seach_key.getText().toString();

        if (key.matches("")) {
            myContact = new ArrayList<>();
            populateContactInformation(null);
        } else {
            List<Contact> tempContact = new ArrayList<>();
            tempContact.addAll(myContact);
            for (Contact contact : myContact)
                if (!contact.getName().contains(key) && !contact.getPhone_number().contains(key))
                    tempContact.remove(contact);
            myContact = new ArrayList<>();
            myContact.addAll(tempContact);

            if (myContact.size() == 0) {
                saveAsNewContact(key);
                populateContactInformation(null);
            }
        }

        populateMyContact();
    }

    private void saveAsNewContact(String phone_number) {

        ContentValues values = new ContentValues();
        Uri rawContactUri = getContentResolver().insert(ContactsContract.RawContacts.CONTENT_URI, values);
        long rawContactId = ContentUris.parseId(rawContactUri);
        values.clear();
        values.put(ContactsContract.RawContacts.Data.RAW_CONTACT_ID, rawContactId);
        values.put(ContactsContract.RawContacts.Data.MIMETYPE, ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE);
        values.put(ContactsContract.CommonDataKinds.Phone.NUMBER, phone_number);
        values.put(ContactsContract.CommonDataKinds.Phone.TYPE, ContactsContract.CommonDataKinds.Phone.TYPE_MOBILE);
        getContentResolver().insert(android.provider.ContactsContract.Data.CONTENT_URI, values);

        saveInMyDB(phone_number);

        Toast.makeText(MainActivity.this, "New Contact have been saved!", Toast.LENGTH_SHORT).show();
    }

    private void saveInMyDB(String phone_number) {
        myDBHandler dbHandler = new myDBHandler(this, null, null, 1);
        dbHandler.addPhoneNumberDB(phone_number);
    }

    private void populateContactInformation(String slection) {

        ContentResolver cr = getContentResolver();
        Cursor cursor = cr.query(ContactsContract.Contacts.CONTENT_URI, null, slection, null, null);
        while (cursor.moveToNext()) {
            int nameFieldColumnIndex = cursor.getColumnIndex(ContactsContract.PhoneLookup.DISPLAY_NAME);
            String contact_name = cursor.getString(nameFieldColumnIndex);
            String contact_id = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts._ID));
            Cursor phone = cr.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, ContactsContract.CommonDataKinds.Phone.CONTACT_ID + "=" + contact_id, null, null);

            String phone_number = "";
            while (phone.moveToNext())
                phone_number = phone.getString(phone.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
            phone_number = Pattern.compile("[^0-9]").matcher(phone_number).replaceAll("").trim();

            Contact contact = new Contact(contact_name, phone_number);

            myContact.add(contact);
        }

    }

    public void callLogButton(View view) {
        Intent myIntent = new Intent(MainActivity.this, CallLogActivity.class);
        startActivityForResult(myIntent, 40);
    }

    private void populateMyContact() {

        ArrayAdapter<Contact> adapter = new MyListAdapter();
        ListView listView = findViewById(R.id.contact_list);
        listView.setAdapter(adapter);
    }

    private class MyListAdapter extends ArrayAdapter<Contact> {

        public MyListAdapter() {
            super(MainActivity.this, R.layout.contact_list_layout, myContact);
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            //return super.getView(position, convertView, parent);

            View itemView = convertView;
            if (itemView == null)
                itemView = getLayoutInflater().inflate(R.layout.contact_list_layout, parent, false);

            final Contact currentContact = myContact.get(position);

            TextView name = itemView.findViewById(R.id.contact_name);
            name.setText(currentContact.getName());
            TextView phone_number = itemView.findViewById(R.id.contact_phone);
            phone_number.setText(currentContact.getPhone_number());

            ImageButton call_button = itemView.findViewById(R.id.call_button);
            call_button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                        ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.CALL_PHONE}, 1);
                        recreate();
                    }
                    startActivity(new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + currentContact.getPhone_number())));
                }
            });
            ImageButton text_button = itemView.findViewById(R.id.text_button);
            text_button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent sendIntent = new Intent(Intent.ACTION_VIEW);
                    sendIntent.setData(Uri.parse("sms:" + currentContact.getPhone_number()));
                    startActivity(sendIntent);
                }
            });

            return itemView;
        }

    }
}

class Contact {
    private String name;
    private String phone_number;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone_number() {
        return phone_number;
    }

    public void setPhone_number(String phone_number) {
        this.phone_number = phone_number;
    }

    public Contact(String name, String phone_number) {

        this.name = name;
        this.phone_number = phone_number;
    }
}
