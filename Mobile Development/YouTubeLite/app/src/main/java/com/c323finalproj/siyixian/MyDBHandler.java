package com.c323finalproj.siyixian;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class MyDBHandler extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "userInfo.db";
    public static final String TABLE_USER = "userInfoTable";
    public static final String COLUMN_USERNAME = "_username";
    public static final String COLUMN_PASSWORD = "_password";
    public static final String COLUMN_NAME = "_name";
    public static final String COLUMN_EMAIL = "_email";
    public static final String COLUMN_PHONE_NUMBER = "_phone_number";
    public static final String COLUMN_FRIENDS = "_friends";
    public static final String COLUMN_LOCATION = "_location";
    public static final String COLUMN_TIME = "_time";
    public static final String COLUMN_VIDEO = "_video";
    public static final String COLUMN_IMAGE = "_image";

    public MyDBHandler(Context context, SQLiteDatabase.CursorFactory factory) {
        super(context, DATABASE_NAME, factory, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_USER_TABLE = "CREATE TABLE " + TABLE_USER + "("
                + COLUMN_USERNAME + " TEXT PRIMARY KEY UNIQUE NOT NULL,"
                + COLUMN_PASSWORD + " TEXT NOT NULL,"
                + COLUMN_NAME + " TEXT,"
                + COLUMN_EMAIL + " TEXT,"
                + COLUMN_PHONE_NUMBER + " TEXT,"
                + COLUMN_FRIENDS + " TEXT,"
                + COLUMN_LOCATION + " TEXT,"
                + COLUMN_TIME + " TEXT,"
                + COLUMN_VIDEO + " TEXT,"
                + COLUMN_IMAGE + " BLOB" + ")";
        db.execSQL(CREATE_USER_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USER);
        onCreate(db);
    }

    public void signUpUser(String username, String password, String name, String email, String phone_number) {
        ContentValues values = new ContentValues();
        values.put(COLUMN_USERNAME, username);
        values.put(COLUMN_PASSWORD, password);
        values.put(COLUMN_NAME, name);
        values.put(COLUMN_EMAIL, email);
        values.put(COLUMN_PHONE_NUMBER, phone_number);
        values.put(COLUMN_VIDEO, "0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0");
        SQLiteDatabase db = this.getWritableDatabase();
        db.insert(TABLE_USER, null, values);
//        db.close();
    }

    public void updateLocationTrue(String... username) {
        SQLiteDatabase db = getWritableDatabase();
        User user = findUserDB(username[0]);
        ContentValues values = new ContentValues();
        values.put(COLUMN_LOCATION, "TRUE");
        db.update(TABLE_USER, values, COLUMN_USERNAME + " = ?", username);
    }

    public void updateLocationFalse(String... username) {
        SQLiteDatabase db = getWritableDatabase();
        User user = findUserDB(username[0]);
        ContentValues values = new ContentValues();
        values.put(COLUMN_LOCATION, "FALSE");
        db.update(TABLE_USER, values, COLUMN_USERNAME + " = ?", username);
    }

    public void updateLocation(String location, String... username) {
        SQLiteDatabase db = getWritableDatabase();
        User user = findUserDB(username[0]);
        ContentValues values = new ContentValues();
        if (!user.getLocation().equals("FALSE")) {
            values.put(COLUMN_LOCATION, location);
            db.update(TABLE_USER, values, COLUMN_USERNAME + " = ?", username);
        }
    }

    public void updateTimeTrue(String... username) {
        SQLiteDatabase db = getWritableDatabase();
        User user = findUserDB(username[0]);
        ContentValues values = new ContentValues();
        values.put(COLUMN_TIME, "0");
        db.update(TABLE_USER, values, COLUMN_USERNAME + " = ?", username);
    }

    public void updateTimeFalse(String... username) {
        SQLiteDatabase db = getWritableDatabase();
        User user = findUserDB(username[0]);
        ContentValues values = new ContentValues();
        values.put(COLUMN_TIME, "FALSE");
        db.update(TABLE_USER, values, COLUMN_USERNAME + " = ?", username);
    }

    public void updateTime(String time, String... username) {
        SQLiteDatabase db = getWritableDatabase();
        User user = findUserDB(username[0]);
        ContentValues values = new ContentValues();
        if (!user.getLocation().equals("FALSE")) {
            values.put(COLUMN_TIME, time);
            db.update(TABLE_USER, values, COLUMN_USERNAME + " = ?", username);
        }
    }

    public void setColumnImage(Bitmap bitmap, String... username) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
        byte[] columnImage = baos.toByteArray();
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_IMAGE, columnImage);
        db.update(TABLE_USER, values, COLUMN_USERNAME + " = ?", username);
    }

    public User findUserDB(String username) {
        String query = "Select * FROM " + TABLE_USER + " WHERE " + COLUMN_USERNAME + " = \"" + username + "\"";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        User user = new User();
        if (cursor.moveToFirst()) {
            cursor.moveToFirst();
            user.setUsername(cursor.getString(cursor.getColumnIndex(COLUMN_USERNAME)));
            user.setPassword(cursor.getString(cursor.getColumnIndex(COLUMN_PASSWORD)));
            user.setName(cursor.getString(cursor.getColumnIndex(COLUMN_NAME)));
            user.setEmail(cursor.getString(cursor.getColumnIndex(COLUMN_EMAIL)));
            user.setPhone_number(cursor.getString(cursor.getColumnIndex(COLUMN_PHONE_NUMBER)));
            try {
                user.setFriends(cursor.getString(cursor.getColumnIndex(COLUMN_FRIENDS)).split(","));
            } catch (Exception e) {
                user.setFriends(new String[0]);
            }
            user.setLocation(cursor.getString(cursor.getColumnIndex(COLUMN_LOCATION)));
            user.setTime(cursor.getString(cursor.getColumnIndex(COLUMN_TIME)));
            try {
                user.setVideos(cursor.getString(cursor.getColumnIndex(COLUMN_VIDEO)).split(","));
            } catch (Exception e) {
                user.setFriends(new String[0]);
            }
            byte[] image = cursor.getBlob(cursor.getColumnIndex(COLUMN_IMAGE));
            user.setImage(BitmapFactory.decodeByteArray(image, 0, image.length));
            Log.i("USER_NAME", user.getName());
            cursor.close();
        } else {
            user = null;
        }
//        db.close();
        return user;
    }

    public void addFriends(String friend, String... username) {
        User user = findUserDB(username[0]);
        if (!user.getFriends().contains(friend))
            user.getFriends().add(friend);
        StringBuilder stringBuilder = new StringBuilder();
        for (String currentFriend : user.getFriends()) {
            stringBuilder.append(currentFriend);
            stringBuilder.append(",");
        }
        ContentValues values = new ContentValues();
        values.put(COLUMN_FRIENDS, stringBuilder.toString());
        SQLiteDatabase db = this.getWritableDatabase();
        db.update(TABLE_USER, values, COLUMN_USERNAME + " = ?", username);
    }

    public void addVideo(int video, String... username) {
        User user = findUserDB(username[0]);
        user.getVideos().set(video, true);
        StringBuilder stringBuilder = new StringBuilder();
        for (Boolean currentFriend : user.getVideos()) {
            if (currentFriend)
                stringBuilder.append(1);
            else
                stringBuilder.append(0);
            stringBuilder.append(",");
        }
        Log.i("VIDEO_LIST", stringBuilder.toString());
        ContentValues values = new ContentValues();
        values.put(COLUMN_VIDEO, stringBuilder.toString());
        SQLiteDatabase db = this.getWritableDatabase();
        db.update(TABLE_USER, values, COLUMN_USERNAME + " = ?", username);
    }

    public void updateProfile(String name, String email, String password, String phone_number, String... username) {
        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME, name);
        values.put(COLUMN_EMAIL, email);
        values.put(COLUMN_PASSWORD, password);
        values.put(COLUMN_PHONE_NUMBER, phone_number);
        SQLiteDatabase db = this.getWritableDatabase();
        db.update(TABLE_USER, values, COLUMN_USERNAME + " = ?", username);
    }

    public void deleteVideo(int video, String... username) {
        User user = findUserDB(username[0]);
        user.getVideos().set(video, false);
        StringBuilder stringBuilder = new StringBuilder();
        for (Boolean currentFriend : user.getVideos()) {
            if (currentFriend)
                stringBuilder.append(1);
            else
                stringBuilder.append(0);
            stringBuilder.append(",");
        }
        Log.i("VIDEO_LIST", stringBuilder.toString());
        ContentValues values = new ContentValues();
        values.put(COLUMN_VIDEO, stringBuilder.toString());
        SQLiteDatabase db = this.getWritableDatabase();
        db.update(TABLE_USER, values, COLUMN_USERNAME + " = ?", username);
    }

    public ArrayList<User> searchFriends(String name) {
        String query = "Select * FROM " + TABLE_USER + " WHERE " + COLUMN_NAME + " LIKE \'%" + name + "%\'";
        Log.i("SEARCH_SQL", query);

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        Log.i("CURSOR_SIZE", cursor.getCount() + "");
        ArrayList<User> users = new ArrayList<>();
        cursor.moveToFirst();
        if (cursor != null && cursor.getCount() > 0) {
            do {
                User user = new User();
                Log.i("CURSOR_INDEX", cursor.getString(0));
                user.setUsername(cursor.getString(cursor.getColumnIndex(COLUMN_USERNAME)));
                user.setName(cursor.getString(cursor.getColumnIndex(COLUMN_NAME)));
                byte[] image = cursor.getBlob(cursor.getColumnIndex(COLUMN_IMAGE));
                user.setImage(BitmapFactory.decodeByteArray(image, 0, image.length));
                Log.i("SEARCH_SQL", user.getName());
                users.add(user);
            } while (cursor.moveToNext());
        }

//        db.close();
        return users;
    }
}
