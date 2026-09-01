package com.mala.digital_joper_mala.Database

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.mala.digital_joper_mala.Model.JopHistory

class JopHistoryDatabase(
    val context: Context
) : SQLiteOpenHelper(context, "jop_history.db", null, 1) {

    private companion object{

        const val ID = "id"
        const val DATE_STAMP = "date_stamp"
        const val COUNT = "count"
        const val TABLE_NAME = "jop_count_history"

    }

    override fun onCreate(db: SQLiteDatabase?) {

        val createSql = """
CREATE TABLE IF NOT EXISTS $TABLE_NAME 
(id INTEGER PRIMARY KEY AUTOINCREMENT, 
$DATE_STAMP TEXT, 
$COUNT INTEGER)
            
        """.trimIndent()

        db?.execSQL(createSql)

    }

    override fun onUpgrade(
        db: SQLiteDatabase?,
        oldVersion: Int,
        newVersion: Int
    ) {

        db?.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
        onCreate(db)

    }

    fun insertJopCount(dayDate: String, jopCount : Long = 0L){

        if (dayDate.isEmpty() || jopCount < 0L) return

        val db = dbOpen(true)

        val cv = ContentValues()

        try {

            if (isDuplicate(dayDate)){

                val oldCount = getOneJopCount(dayDate)

                cv.put(COUNT, jopCount + oldCount)

                db.update(TABLE_NAME, cv, "$DATE_STAMP = ?", arrayOf(dayDate))

            }else{

                cv.put(DATE_STAMP, dayDate)
                cv.put(COUNT, jopCount)

                db.insert(TABLE_NAME, null, cv)

            }

        }catch (e : Exception){

            e.printStackTrace()

        }

    }

    fun getOneJopCount(dayDate: String) : Long{

        if (dayDate.isEmpty()) return 0L

        val db = dbOpen()

        var cursor : Cursor? = null

        var count = 0L

        try {

            cursor = db.rawQuery("SELECT $COUNT FROM $TABLE_NAME WHERE $DATE_STAMP = ?", arrayOf(dayDate))

            if (cursor.moveToFirst()){

                count = cursor.getLong(cursor.getColumnIndexOrThrow(COUNT))

            }

        }catch (e : Exception){

            e.printStackTrace()

        }finally {
            cursor?.close()
        }

        return count
    }

    fun getAllJopCount() : List<JopHistory>{

        val list : MutableList<JopHistory> = mutableListOf()

        val db = dbOpen()

        var cursor : Cursor? = null

        try {

            cursor = db.rawQuery("SELECT * FROM $TABLE_NAME ORDER BY id DESC", null)

            while (cursor.moveToNext()){

                val dateDay = cursor.getString(cursor.getColumnIndexOrThrow(DATE_STAMP))
                val count = cursor.getLong(cursor.getColumnIndexOrThrow(COUNT))

                list.add(JopHistory(
                    dayDate = dateDay,
                    count = count
                ))

            }

        }catch (e : Exception){

            e.printStackTrace()

        }finally {
            cursor?.close()
        }

        return list

    }

    fun deleteAllJopCount(){

        val db = dbOpen(true)

        db.delete(TABLE_NAME, null, null)

    }

    private fun isDuplicate(dayDate : String) : Boolean{

        val db = dbOpen()

        var cursor : Cursor? = null

        var isExists = false

        try {

            cursor = db.rawQuery("SELECT 1 FROM $TABLE_NAME WHERE $DATE_STAMP = ?", arrayOf(dayDate))

            if (cursor.moveToFirst()) isExists = true

        }catch (e : Exception){

            e.printStackTrace()

        }finally {
            cursor?.close()
        }

        return isExists

    }

    private fun dbOpen(writeable : Boolean = false) : SQLiteDatabase{

        return if (writeable) writableDatabase else readableDatabase

    }
}