package com.c323proj8.siyixian;

import android.content.ContentValues;
import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.Nullable;

public class myDBHandler extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "myContacts.db";
    public static final String TABLE_CONTACTS = "contactsTable";
    public static final String COLUMN_NAME = "_name";
    public static final String COLUMN_PHONE_NUMBER = "_phone_number";

    public myDBHandler(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, DATABASE_NAME, factory, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_PRODUCTS_TABLE = "CREATE TABLE " + TABLE_CONTACTS +
                "(" + COLUMN_NAME + " TEXT," + COLUMN_PHONE_NUMBER + " TEXT" + ")";
        db.execSQL(CREATE_PRODUCTS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CONTACTS);
        onCreate(db);
    }

    public void addPhoneNumberDB(String phone_number) {
        ContentValues values = new ContentValues();
        values.put(COLUMN_PHONE_NUMBER, phone_number);
        SQLiteDatabase db = this.getWritableDatabase();
        db.insert(TABLE_CONTACTS,null, values);
        db.close();
    }
}
