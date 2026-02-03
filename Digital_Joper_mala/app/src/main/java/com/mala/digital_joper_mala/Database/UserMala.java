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

public class UserMala extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "Mala.db";
    public static final String TABLE_NAME = "user_mala";
    public static final int VERSION = 9;
    SQLiteDatabase db;

    public UserMala(@Nullable Context context) {
        super(context, DATABASE_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String create_sql = "CREATE TABLE "+TABLE_NAME+ "(id INTEGER PRIMARY KEY AUTOINCREMENT, mala_name TEXT NOT NULL, image_uri TEXT , mantra1 TEXT, mantra2 TEXT, mantra3 TEXT, mantra4 TEXT)";
        db.execSQL(create_sql);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        String update_sql = "DROP TABLE IF EXISTS " +TABLE_NAME;

        db.execSQL(update_sql);
        onCreate(db);

    }

    public void insert(
            String mala_name,
            String imageUri,
            String mantra1,
            String mantra2,
            String mantra3,
            String mantra4){

        if (db == null || !db.isOpen()){

            db = this.getWritableDatabase();

        }

        ContentValues values = new ContentValues();
        values.put("mala_name", mala_name);

        if (imageUri != null &&!imageUri.isEmpty()){

            values.put("image_uri", imageUri);

        }else {

            values.putNull("image_uri");
        }

        if (mantra1 == null || mantra1.isEmpty()){

            values.putNull("mantra1");

        }else {

            values.put("mantra1", mantra1);

        }

        if (mantra2 == null || mantra2.isEmpty()){

            values.putNull("mantra2");

        }else {

            values.put("mantra2", mantra2);

        }

        if (mantra3 == null || mantra3.isEmpty()){

            values.putNull("mantra3");

        }else {

            values.put("mantra3", mantra3);

        }

        if (mantra4 == null || mantra4.isEmpty()){

            values.putNull("mantra4");

        }else {

            values.put("mantra4", mantra4);

        }

        /*
        if (image == null || image.isEmpty()){

            values.putNull("image");

        }else {

            values.put("image", image);

        }


         */
        db.insert(TABLE_NAME, null, values);

    }

    public List<HashMap<String, String>> getAll(){

        if (db == null || !db.isOpen()){

            db = this.getReadableDatabase();
        }

        List<HashMap<String, String>> list = new ArrayList<>();

        Cursor cursor = db.rawQuery("SELECT * FROM " +TABLE_NAME, null);

        while (cursor.moveToNext()){

            HashMap<String, String> map = new HashMap<>();
            map.put("mala_name", cursor.getString(cursor.getColumnIndexOrThrow("mala_name")));
            map.put("mantra1", cursor.getString(cursor.getColumnIndexOrThrow("mantra1")));
            map.put("mantra2", cursor.getString(cursor.getColumnIndexOrThrow("mantra2")));
            map.put("mantra3", cursor.getString(cursor.getColumnIndexOrThrow("mantra3")));
            map.put("mantra4", cursor.getString(cursor.getColumnIndexOrThrow("mantra4")));
            map.put("image_uri", cursor.getString(cursor.getColumnIndexOrThrow("image_uri")));
            list.add(map);

            Log.d("map", map.keySet().toString());
        }



        cursor.close();

        return list;
    }

    public List<HashMap<String, String>> getSearchItem(String mala_name){

        List<HashMap<String, String>> searchList = new ArrayList<>();

        if (db == null || !db.isOpen()) db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT * FROM " +TABLE_NAME+ " WHERE mala_name = ?", new String[]{mala_name});

        while (cursor.moveToNext()){

            HashMap<String, String> hashMap = new HashMap<>();
            hashMap.put("mantra1", cursor.getString(cursor.getColumnIndexOrThrow("mantra1")));
            hashMap.put("mantra2", cursor.getString(cursor.getColumnIndexOrThrow("mantra2")));
            hashMap.put("mantra3", cursor.getString(cursor.getColumnIndexOrThrow("mantra3")));
            hashMap.put("mantra4", cursor.getString(cursor.getColumnIndexOrThrow("mantra4")));
            hashMap.put("image_uri", cursor.getString(cursor.getColumnIndexOrThrow("image_uri")));
            searchList.add(hashMap);
        }
        cursor.close();

        return searchList;

    }

    public boolean deleteOne(String title){

        if (db == null || !db.isOpen()){

            db = this.getWritableDatabase();

        }

        db.delete(TABLE_NAME, "mala_name = ?", new String[]{title});

        return true;

    }

    public void deleteAll(){

        if (db == null || !db.isOpen()){

            db = this.getWritableDatabase();

        }

        db.execSQL("DELETE FROM " +TABLE_NAME);

    }

    public void closeDB(){

        if (db != null && db.isOpen()){

            db.close();

        }

    }
}
