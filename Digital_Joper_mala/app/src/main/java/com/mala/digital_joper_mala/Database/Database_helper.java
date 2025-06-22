package com.mala.digital_joper_mala.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Database_helper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "mantra.db";
    public static final int VERSION = 2;
    public static final String TABLE_NAME = "mantra_table";

    public Database_helper(@Nullable Context context) {
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
        ContentValues values = new ContentValues();

        values.put("title", title);
        values.put("mantra", mantra);

        db.insert(TABLE_NAME,null, values);

        db.close();

    }

    public boolean delete(String title){

        SQLiteDatabase db = this.getWritableDatabase();

        int result =   db.delete(TABLE_NAME,"title = ?", new String[]{title});
        db.close();

        return result > 0;
    }

    public List<HashMap<String, String>> getData(){

        List<HashMap<String, String>> list = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT title,mantra FROM "+TABLE_NAME, null);

        while (cursor.moveToNext()){

            HashMap<String, String> map = new HashMap<>();

            map.put("title", cursor.getString(cursor.getColumnIndexOrThrow("title")));
            map.put("mantra", cursor.getString(cursor.getColumnIndexOrThrow("mantra")));
            list.add(map);

        }

        cursor.close();
        db.close();

        return list;
    }
}
