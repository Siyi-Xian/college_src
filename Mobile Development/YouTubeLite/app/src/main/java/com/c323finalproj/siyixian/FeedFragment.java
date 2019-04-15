package com.c323finalproj.siyixian;

import android.net.Uri;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.VideoView;

import java.util.ArrayList;

public class FeedFragment extends Fragment {

    View myView;
    ArrayList<MyVideo> myVideos = new ArrayList<>();

    MyDBHandler myDBHandler;
    User user;
    String username;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        myView = inflater.inflate(R.layout.fragment_main, container, false);

        ((FloatingActionButton) getActivity().findViewById(R.id.fab)).setImageDrawable(getActivity().getDrawable(R.drawable.ic_sync_black));
        Log.i("FAB", "TRENDING_SYNC");

        myDBHandler = new MyDBHandler(getActivity(), null);
        username = getActivity().getSharedPreferences("SPREF_APP", getActivity().MODE_PRIVATE).getString("USERNAME", "admin");
        user = myDBHandler.findUserDB(username);

        populateVideos();
        populateDataBaseVideos();

        return myView;
    }

    private void populateDataBaseVideos() {
        myVideos.clear();
        String[] viewed = getActivity().getSharedPreferences("SPREF_VIEWED", getActivity().MODE_PRIVATE).getString("VIEWED", "").split(",");
        for (int i = 1; i <= 20; i++)
            if (user.getVideos().get(i - 1))
                myVideos.add(new MyVideo(i, Integer.parseInt(viewed[i - 1])));
    }

    private void populateVideos() {
        ArrayAdapter<MyVideo> adapter = new VideoListAdapter();
        ListView listView = myView.findViewById(R.id.video_list);
        listView.setAdapter(adapter);
    }

    private class VideoListAdapter extends ArrayAdapter<MyVideo> {
        public VideoListAdapter() {
            super(getActivity(), R.layout.list_layout_video, myVideos);
        }

        @NonNull
        @Override
        public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            View itemView = convertView;
            if (itemView == null)
                itemView = getActivity().getLayoutInflater().inflate(R.layout.list_layout_video, parent, false);

            MyVideo currentVideo = myVideos.get(position);

            VideoView videoView = itemView.findViewById(R.id.videoView);

            MediaController mediaController = new MediaController(getActivity());
            videoView.setVideoURI(Uri.parse(String.format(getContext().getString(R.string.video_url), currentVideo.getUrl())));
            videoView.setMediaController(mediaController);
            videoView.requestFocus();

            CheckBox checkBox = itemView.findViewById(R.id.checkBox);
            checkBox.setChecked(true);
            checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (!isChecked)
                        myDBHandler.deleteVideo(myVideos.get(position).getUrl() - 1, user.getUsername());
                    user = myDBHandler.findUserDB(username);
                }
            });
            TextView textView = itemView.findViewById(R.id.viewed_number);
            textView.setText(currentVideo.getView_number() + "" + " Views");
//            textView.setText((new ConstantValue()).getVideo_number()[position] + "");

            return itemView;
        }
    }
}
