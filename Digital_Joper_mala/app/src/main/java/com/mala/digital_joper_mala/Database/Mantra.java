package com.mala.digital_joper_mala.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Mantra extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "my_mantra.db";
    public static final String TABLE_NAME = "my_mantra_table";
    public static final int VERSION = 2;


    public Mantra(@Nullable Context context) {
        super(context, DATABASE_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL("CREATE TABLE " +TABLE_NAME+ "(id INTEGER PRIMARY KEY AUTOINCREMENT, title TEXT, mantra TEXT)");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS " +TABLE_NAME);
        onCreate(db);

    }

    public void insert(String title, String mantra){

        SQLiteDatabase db = this.getWritableDatabase();

        String sql = " INSERT INTO " +TABLE_NAME+ "(title, mantra) VALUES (?, ?)";


        db.execSQL(sql, new Object[]{title, mantra});

/*
        ContentValues values = new ContentValues();

        values.put("title", "Test");
        values.put("mantra", "worked");

        db.insert(TABLE_NAME, null, values);

 */



        db.close();

    }
    public List<HashMap<String, String>> get_All_data(){

        List<HashMap<String, String>> list = new ArrayList<>();

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT title,mantra FROM "+TABLE_NAME, null);

        while (cursor.moveToNext()){

            HashMap<String, String> hashMap = new HashMap<>();
            hashMap.put("title", cursor.getString(cursor.getColumnIndexOrThrow("title")));
            hashMap.put("mantra", cursor.getString(cursor.getColumnIndexOrThrow("mantra")));
            list.add(hashMap);
        }

        Log.d("data", "="+list.size());

        cursor.close();
        db.close();

        return list;
    }
}
