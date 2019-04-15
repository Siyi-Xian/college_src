package com.c323finalproj.siyixian;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class SearchFriendsActivity extends AppCompatActivity implements TextDialogFragment.Callback{

    ArrayList<User> friends = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_firends);
        populateFriends();
    }

    public void returnButton(View view) {
        Intent myIntent = new Intent();
        setResult(Activity.RESULT_OK, myIntent);
        finish();
    }

    private void populateFriendsUser(String keywords) {
        friends.clear();
        Log.i("KEYWORDS", keywords);
        if (keywords.length() > 0) {
            MyDBHandler myDBHandler = new MyDBHandler(this, null);
            friends.addAll(myDBHandler.searchFriends(keywords));
        }
        Log.i("FRIENDS_LIST", friends.size() + "");
    }

    private void populateFriends() {
        ArrayAdapter<User> adapter = new FriendsListAdapter();
        ListView listView = findViewById(R.id.friends_list);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                ButtonDialogFragment buttonDialogFragment = new ButtonDialogFragment();
                buttonDialogFragment.show("Add Friends", "Do you want to add " + friends.get(position).getName() + " as your new friend?", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        SharedPreferences sharedPreferences = getSharedPreferences("SPREF_APP", MODE_PRIVATE);
                        String username = sharedPreferences.getString("USERNAME", "admin");
                        MyDBHandler myDBHandler = new MyDBHandler(SearchFriendsActivity.this, null);
                        myDBHandler.addFriends(friends.get(position).getUsername(), username);
                        Toast.makeText(SearchFriendsActivity.this, "Friend Added!", Toast.LENGTH_SHORT).show();

                    }
                }, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(SearchFriendsActivity.this, "Friend Adding Canceled!", Toast.LENGTH_SHORT).show();
                    }
                }, getSupportFragmentManager());
            }
        });
    }

    public void searchFriendButton(View view) {
        String keyword = ((EditText) findViewById(R.id.search_firends_keyword)).getText().toString();
        populateFriendsUser(keyword);
        populateFriends();
    }

    public void inviteFriend(View view) {
        TextDialogFragment textDialogFragment = new TextDialogFragment();
        textDialogFragment.show("Invite Friend", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(SearchFriendsActivity.this, "Friend Invitation Canceled!", Toast.LENGTH_SHORT).show();
            }
        }, getSupportFragmentManager());
    }

    @Override
    public void onClick(String phone_number) {
        Toast.makeText(SearchFriendsActivity.this, "Message Send to " + phone_number, Toast.LENGTH_SHORT).show();
        SmsManager smsManager = SmsManager.getDefault();

        String SENT_SMS_ACTION = "SENT_SMS_ACTION";
        Intent sentIntent = new Intent(SENT_SMS_ACTION);
        PendingIntent sentPI = PendingIntent.getBroadcast(this, 0, sentIntent, 0);
        registerReceiver(new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                if (getResultCode() == Activity.RESULT_OK)
                    Toast.makeText(SearchFriendsActivity.this, "SMS send successful!", Toast.LENGTH_SHORT).show();
            }
        }, new IntentFilter(SENT_SMS_ACTION));

        String DELIVERED_SMS_ACTION = "DELIVERED_SMS_ACTION";
        Intent deliverIntent = new Intent(DELIVERED_SMS_ACTION);
        PendingIntent deliverPI = PendingIntent.getBroadcast(this, 0, deliverIntent, 0);
        registerReceiver(new BroadcastReceiver() {
            @Override
            public void onReceive(Context _context, Intent _intent) {
                Toast.makeText(SearchFriendsActivity.this, "Receiver receive SMS successful!", Toast.LENGTH_SHORT).show();
            }
        }, new IntentFilter(DELIVERED_SMS_ACTION));

        List<String> divideContents = smsManager.divideMessage("Hi! I am using YouTube Lite application. Join Me \uD83D\uDE0A\uD83D\uDE0A");
        for (String text : divideContents) {
            smsManager.sendTextMessage(phone_number, null, text, sentPI, deliverPI);
        }
    }

    private class FriendsListAdapter extends ArrayAdapter<User> {
        public FriendsListAdapter() {
            super(SearchFriendsActivity.this, R.layout.list_layout_friends, friends);
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            View itemView = convertView;
            if (itemView == null)
                itemView = getLayoutInflater().inflate(R.layout.list_layout_friends, parent, false);

            User currentUser = friends.get(position);

            ((TextView) itemView.findViewById(R.id.friends_name)).setText(currentUser.getName());
            ((ImageView) itemView.findViewById(R.id.friends_iamge)).setImageBitmap(currentUser.getImage());

            return itemView;
        }
    }
}
