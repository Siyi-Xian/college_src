package com.c323finalproj.siyixian;

import android.graphics.Bitmap;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class User {
    private String username = "";
    private String password = "";
    private String name = "Not Provide";
    private String email = "Not Provide";
    private String phone_number = "Not Provide";
    private String Location = "Not Provide";
    private String time = "0";
    private ArrayList<String> friends = new ArrayList<>();

    private Bitmap image;

    public ArrayList<Boolean> videos = new ArrayList<>();

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        if (username != null) this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        if (password != null) this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        if (name != null) this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        if (email != null) this.email = email;
    }

    public String getPhone_number() {
        return phone_number;
    }

    public void setPhone_number(String phone_number) {
        if (username != null) this.phone_number = phone_number;
    }

    public ArrayList<String> getFriends() {
        return friends;
    }

    public void setFriends(String[] friends) {
        for (String friend : friends)
            if (friend.length() > 0)
                this.friends.add(friend);
    }

    public String getLocation() {
        return Location;
    }

    public void setLocation(String location) {
        if (location != null) Location = location;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        if (time != null) this.time = time;
    }

    public ArrayList<Boolean> getVideos() {
        return videos;
    }

    public void setVideos(String[] videos) {
        for (String video : videos)
            if (Integer.parseInt(video) == 1)
                this.videos.add(true);
            else
                this.videos.add(false);
    }

    public Bitmap getImage() {
        return image;
    }

    public void setImage(Bitmap image) {
        if (image != null) this.image = image;
    }
}
