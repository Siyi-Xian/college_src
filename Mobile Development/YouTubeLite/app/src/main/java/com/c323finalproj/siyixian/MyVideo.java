package com.c323finalproj.siyixian;

public class MyVideo {
    private int url;
    private int view_number;

    public MyVideo(int url, int view_number) {
        this.url = url;
        this.view_number = view_number;
    }

    public int getUrl() {

        return url;
    }

    public int getView_number() {
        return view_number;
    }
}
