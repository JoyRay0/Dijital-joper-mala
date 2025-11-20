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

public class History extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "history.db";
    public static final String TABLE_NAME = "history";
    public static final int VERSION = 2;
    SQLiteDatabase db;

    public History(@Nullable Context context) {
        super(context, DATABASE_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String create_sql = "CREATE TABLE " +TABLE_NAME+"(id INTEGER PRIMARY KEY AUTOINCREMENT , title TEXT, counter TEXT)";

        db.execSQL(create_sql);


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        String update_sql = "DROP TABLE IF EXISTS "+TABLE_NAME;

        db.execSQL(update_sql);
        onCreate(db);

    }

    public void insert(String title, String count){

        if (db == null || !db.isOpen()){

            db = this.getWritableDatabase();

        }

        ContentValues cv = new ContentValues();
        cv.put("title", title);
        cv.put("counter", count);

        db.insert(TABLE_NAME, null, cv);

    }

    public List<HashMap<String, String>> getAll(){

        if (db == null || !db.isOpen()){

            db = this.getReadableDatabase();

        }

        List<HashMap<String, String>> list = new ArrayList<>();

        String raw_sql = "SELECT title, counter FROM "+TABLE_NAME;

        try {

            Cursor cursor = db.rawQuery(raw_sql, null);

            while (cursor.moveToNext()){

                HashMap<String, String> map = new HashMap<>();
                map.put("title", cursor.getString(cursor.getColumnIndexOrThrow("title")));
                map.put("counter", cursor.getString(cursor.getColumnIndexOrThrow("counter")));
                list.add(map);
            }

            cursor.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;

    }

    public boolean DeleteOne(String title){

        if (db == null || !db.isOpen()){

            db = this.getWritableDatabase();

        }

        String deleteOne_sql = "DELETE FROM "+TABLE_NAME+" WHERE title = '" +title+ "'" ;

        db.execSQL(deleteOne_sql);

        return true;
    }

    public void DeleteAll(){

        if (db == null || !db.isOpen()){

            db = this.getWritableDatabase();

        }

        String delete_sql = "DELETE FROM " +TABLE_NAME;

        db.execSQL(delete_sql);

    }

    public void CloseDB(){

        if (db != null && db.isOpen()){

            db.close();

        }

    }
}
