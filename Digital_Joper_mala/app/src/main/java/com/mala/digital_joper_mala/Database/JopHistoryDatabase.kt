package com.mala.digital_joper_mala.Database

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.mala.digital_joper_mala.Model.JopHistory

class JopHistoryDatabase(
    val context: Context
) : SQLiteOpenHelper(context, "jop_history.db", null, 3) {

    private companion object{

        const val ID = "id"
        const val DAY = "day"
        const val DATE = "date"
        const val COUNT = "count"
        const val YEAR = "year"
        const val TABLE_NAME = "jop_count_history"

    }

    override fun onCreate(db: SQLiteDatabase?) {

        val createSql = """
CREATE TABLE IF NOT EXISTS $TABLE_NAME 
($ID INTEGER PRIMARY KEY AUTOINCREMENT, 
$DAY TEXT, 
$DATE TEXT,
$COUNT INTEGER,
$YEAR INTEGER
)
            
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

    fun insertJopCount(day : String, date: String, year : Int, jopCount : Long = 0L){

        if (day.isEmpty() || date.isEmpty() || jopCount < 0L || year < 2026) return

        val db = dbOpen(true)

        val cv = ContentValues()

        try {

            if (isDuplicate(date)){

                val oldCount = getOneJopCount(date)

                cv.put(COUNT, jopCount + oldCount)

                db.update(TABLE_NAME, cv, "$DATE = ?", arrayOf(date))

            }else{

                cv.put(DAY, day)
                cv.put(DATE, date)
                cv.put(COUNT, jopCount)
                cv.put(YEAR, year)

                db.insert(TABLE_NAME, null, cv)

            }

        }catch (e : Exception){

            e.printStackTrace()

        }

    }

    fun getOneJopCount(date: String) : Long{

        if (date.isEmpty()) return 0L

        val db = dbOpen()

        var cursor : Cursor? = null

        var count = 0L

        try {

            cursor = db.rawQuery("SELECT $COUNT FROM $TABLE_NAME WHERE $DATE = ?", arrayOf(date))

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

                val day = cursor.getString(cursor.getColumnIndexOrThrow(DAY))
                val date = cursor.getString(cursor.getColumnIndexOrThrow(DATE))
                val count = cursor.getLong(cursor.getColumnIndexOrThrow(COUNT))

                list.add(JopHistory(
                    day = day,
                    date = date,
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

    fun deleteAllJopCount(year : Int){

        if (year < 2026) return

        val db = dbOpen(true)

        db.delete(TABLE_NAME, "$YEAR != ?", arrayOf(year.toString()))

    }

    private fun isDuplicate(date : String) : Boolean{

        val db = dbOpen()

        var cursor : Cursor? = null

        var isExists = false

        try {

            cursor = db.rawQuery("SELECT 1 FROM $TABLE_NAME WHERE $DATE = ?", arrayOf(date))

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