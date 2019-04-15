package com.c323finalproj.siyixian;

import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.VideoView;

import java.util.ArrayList;

public class FriendInfoActivity extends AppCompatActivity {

    ArrayList<MyVideo> myVideos = new ArrayList<>();
    User friend;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_firend_info);

        MyDBHandler myDBHandler = new MyDBHandler(this, null);
        String username = getIntent().getStringExtra("FRIEND_USERNAME");
        friend = myDBHandler.findUserDB(username);

        populateVideos();
        populateVideosList();
        populateInfo();
    }

    private void populateVideosList() {
        myVideos.clear();
        String[] viewed = getSharedPreferences("SPREF_VIEWED", MODE_PRIVATE).getString("VIEWED", "").split(",");
        for (int i = 1; i <= 20; i++)
            if (friend.getVideos().get(i - 1))
                myVideos.add(new MyVideo(i, Integer.parseInt(viewed[i - 1])));
    }

    private void populateInfo() {
        String time = String.format(((TextView) findViewById(R.id.friend_fragment_usage_time)).getText().toString(), friend.getTime());
        ((TextView) findViewById(R.id.friend_fragment_usage_time)).setText(time);
        ((TextView) findViewById(R.id.friend_fragment_name)).setText(friend.getName());
        ((TextView) findViewById(R.id.firend_fragment_location)).setText(friend.getLocation());
        ((TextView) findViewById(R.id.firend_fragment_phone_number)).setText(friend.getPhone_number());
        ((ImageView) findViewById(R.id.firend_fragment_image)).setImageBitmap(friend.getImage());
    }

    private void populateVideos() {
        ArrayAdapter<MyVideo> adapter = new VideoListAdapter();
        ListView listView = findViewById(R.id.firend_fragment_video_list);
        listView.setAdapter(adapter);
    }

    private class VideoListAdapter extends ArrayAdapter<MyVideo> {
        public VideoListAdapter() {
            super(FriendInfoActivity.this, R.layout.list_layout_video, myVideos);
        }

        @NonNull
        @Override
        public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            View itemView = convertView;
            if (itemView == null)
                itemView = getLayoutInflater().inflate(R.layout.list_layout_video, parent, false);

            MyVideo currentVideo = myVideos.get(position);

            VideoView videoView = itemView.findViewById(R.id.videoView);

            MediaController mediaController = new MediaController(FriendInfoActivity.this);
            videoView.setVideoURI(Uri.parse(String.format(getContext().getString(R.string.video_url), currentVideo.getUrl())));
            videoView.setMediaController(mediaController);
            videoView.requestFocus();

            CheckBox checkBox = itemView.findViewById(R.id.checkBox);
            checkBox.setChecked(true);
            TextView textView = itemView.findViewById(R.id.viewed_number);
            textView.setText(currentVideo.getView_number() + "" + " Views");

            return itemView;
        }
    }
}
