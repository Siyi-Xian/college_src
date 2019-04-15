package com.c323finalproj.siyixian;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import java.util.ArrayList;

public class FriendsFragment extends Fragment {

    View myView;
    ArrayList<User> friends = new ArrayList<>();

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        populateFriendsUser();
        populateFriends();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        myView = inflater.inflate(R.layout.fragment_main, container, false);

        FloatingActionButton fab = getActivity().findViewById(R.id.fab);

        fab.setImageDrawable(getActivity().getDrawable(R.drawable.ic_add_black_24dp));
        Log.i("FAB", "FRIENDS_ADD");

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), SearchFriendsActivity.class);
                startActivityForResult(intent, 40);
            }
        });

        populateFriendsUser();
        populateFriends();

        return myView;
    }

    private void populateFriendsUser() {
        MyDBHandler myDBHandler = new MyDBHandler(getContext(), null);
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("SPREF_APP", getActivity().MODE_PRIVATE);
        String username = sharedPreferences.getString("USERNAME", "admin");

        friends.clear();
        User user = myDBHandler.findUserDB(username);
        for (String friend : user.getFriends())
            friends.add(myDBHandler.findUserDB(friend));
    }

    private void populateFriends() {
        ArrayAdapter<User> adapter = new FriendsListAdapter();
        ListView listView = myView.findViewById(R.id.video_list);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getActivity(), FriendInfoActivity.class);
                intent.putExtra("FRIEND_USERNAME", friends.get(position).getUsername());
                startActivityForResult(intent, 40);
            }
        });
    }

    private class FriendsListAdapter extends ArrayAdapter<User> {
        public FriendsListAdapter() {
            super(getActivity(), R.layout.list_layout_friends, friends);
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            View itemView = convertView;
            if (itemView == null)
                itemView = getActivity().getLayoutInflater().inflate(R.layout.list_layout_friends, parent, false);

            User currentUser = friends.get(position);

            ((TextView) itemView.findViewById(R.id.friends_name)).setText(currentUser.getName());
            ((ImageView) itemView.findViewById(R.id.friends_iamge)).setImageBitmap(currentUser.getImage());

            return itemView;
        }
    }

}
