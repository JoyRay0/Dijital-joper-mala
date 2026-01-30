package com.mala.digital_joper_mala.Database

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.mala.digital_joper_mala.Model.JopaChartModel

class JopaChartDB(context: Context) : SQLiteOpenHelper(context, "chart.db", null, 3) {

    private lateinit var db : SQLiteDatabase
    private val TABLE_NAME = "chart"

    override fun onCreate(db: SQLiteDatabase?) {

        val create_sql = "CREATE TABLE $TABLE_NAME (id INTEGER PRIMARY KEY AUTOINCREMENT, year INTEGER, january INTEGER, february INTEGER, march INTEGER, april INTEGER, may INTEGER, june INTEGER, july INTEGER, august INTEGER, september INTEGER, october INTEGER, november INTEGER, december INTEGER)"

        db?.execSQL(create_sql)

    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {

        val update_sql = "DROP TABLE IF EXISTS $TABLE_NAME "

        db?.execSQL(update_sql)
        onCreate(db)

    }

    fun insert( year : Int , month : String, count : Int){

        val db = dbOpen(true)

        if (year == 0 || month.isEmpty()) return

        try {

           val cv = setData(year, count, month)

            val updatedRows = db.update(TABLE_NAME, cv, null, null)

            if (updatedRows == 0) {

                db.insert(TABLE_NAME, null, cv)

            }

        }catch (e : Exception){

            e.printStackTrace()
        }

    }

    fun getAll() : List<JopaChartModel>{

        val db = dbOpen()

        val list : MutableList<JopaChartModel> = mutableListOf()

        var cursor : Cursor ? = null

        try {

            cursor = db.rawQuery("SELECT * FROM $TABLE_NAME ORDER BY id DESC", null)

            while (cursor.moveToNext()){

                val year = cursor.getInt(cursor.getColumnIndexOrThrow("year"))
                val jan = cursor.getInt(cursor.getColumnIndexOrThrow("january"))
                val feb = cursor.getInt(cursor.getColumnIndexOrThrow("february"))
                val mar = cursor.getInt(cursor.getColumnIndexOrThrow("march"))
                val apr = cursor.getInt(cursor.getColumnIndexOrThrow("april"))
                val may = cursor.getInt(cursor.getColumnIndexOrThrow("may"))
                val jun = cursor.getInt(cursor.getColumnIndexOrThrow("june"))
                val jul = cursor.getInt(cursor.getColumnIndexOrThrow("july"))
                val aug = cursor.getInt(cursor.getColumnIndexOrThrow("august"))
                val sep = cursor.getInt(cursor.getColumnIndexOrThrow("september"))
                val oct = cursor.getInt(cursor.getColumnIndexOrThrow("october"))
                val nov = cursor.getInt(cursor.getColumnIndexOrThrow("november"))
                val dec = cursor.getInt(cursor.getColumnIndexOrThrow("december"))

                list.add(
                    JopaChartModel(
                        year = year,
                        january = jan,
                        february = feb,
                        march = mar,
                        april = apr,
                        may = may,
                        june = jun,
                        july = jul,
                        august = aug,
                        september = sep,
                        october = oct,
                        november = nov,
                        december = dec
                    )

                )
            }

        }catch (e : Exception){

            e.printStackTrace()

        }finally {

            cursor?.close()

        }


        return list

    }

    fun deleteAll(){

        val db = dbOpen(true)

        val delete_sql = "DELETE FROM $TABLE_NAME"

        db.execSQL(delete_sql)

    }

    fun closeDB(){

        if (::db.isInitialized && db.isOpen) db.close()

    }

    private fun dbOpen(writeable : Boolean = false) : SQLiteDatabase{

        if (!::db.isInitialized || !db.isOpen) db = if (writeable) writableDatabase else readableDatabase

        return db

    }

    private fun setData(year: Int, count: Int, month: String) : ContentValues{

        val cv = ContentValues()

        val monthList = arrayOf("january", "february", "march", "april", "may", "june", "july", "august", "september", "october", "november", "december")

        val db = dbOpen(true)

        var cursor : Cursor? = null

        try {

            var cYear = 0
            var cMonth = 0

            cursor = db.rawQuery("SELECT * FROM $TABLE_NAME", null)

            if (cursor.moveToFirst()){

                cYear = cursor.getInt(cursor.getColumnIndexOrThrow("year"))
                cMonth = cursor.getInt(cursor.getColumnIndexOrThrow(month))

                if (cYear != year) monthList.forEach { it -> cv.put(it, 0) }

                cv.put("year", year)
                cv.put(month, if (cYear == year) cMonth + count else count)

            }else{

                monthList.forEach { it -> cv.put(it, 0) }
                cv.put("year", year)
                cv.put(month, count)

            }

        }catch (e : Exception){
            e.printStackTrace()
        }finally {
            cursor?.close()
        }

        return cv

    }

}