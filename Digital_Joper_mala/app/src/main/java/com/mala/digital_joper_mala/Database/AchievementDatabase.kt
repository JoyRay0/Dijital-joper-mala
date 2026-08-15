package com.mala.digital_joper_mala.Database

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.mala.digital_joper_mala.Model.Achievement

class AchievementDatabase(
    val context : Context
) : SQLiteOpenHelper(context, "achievement.db", null, 1) {

    private companion object{

        const val ID = "id"
        const val TABLE_NAME = "achievement_table"
        const val MALA_NAME = "mala_name"
        const val ACHIEVEMENT_COUNT = "achievement_count"

    }

    override fun onCreate(db: SQLiteDatabase?) {

        val create_sql = """
CREATE TABLE IF NOT EXISTS $TABLE_NAME (
$ID INTEGER PRIMARY KEY AUTOINCREMENT,
$MALA_NAME TEXT NOT NULL UNIQUE,
$ACHIEVEMENT_COUNT TEXT NOT NULL UNIQUE)
""".trimIndent()

        db?.execSQL(create_sql)

    }

    override fun onUpgrade(
        db: SQLiteDatabase?,
        oldVersion: Int,
        newVersion: Int
    ) {
        db?.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
    }

    fun achievementInsert(malaName : String, achievementCount : String){

        if (malaName.isEmpty() || achievementCount.isEmpty()) return

        val db = dbOpen(true)

        val cv = ContentValues()

        try {

            cv.put(MALA_NAME, malaName)
            cv.put(ACHIEVEMENT_COUNT, achievementCount)

            db.insert(TABLE_NAME, null, cv)

        }catch (e : Exception){

            e.printStackTrace()

        }

    }

    fun getAchievement(malaName : String) : List<Achievement> {

        if (malaName.isEmpty()) return emptyList()

        val achievementList : MutableList<Achievement> = mutableListOf()

        val db = dbOpen()

        var cursor : Cursor? = null

        try {

            cursor = db.rawQuery("SELECT * FROM $TABLE_NAME WHERE $MALA_NAME = ?", arrayOf(malaName))

            while (cursor.moveToNext()){

                val malaName = cursor.getString(cursor.getColumnIndexOrThrow(MALA_NAME))
                val count = cursor.getString(cursor.getColumnIndexOrThrow(ACHIEVEMENT_COUNT))

                achievementList.add(Achievement(
                    malaName = malaName,
                    achievementCount = count
                ))

            }

        }catch (e : Exception){

            e.printStackTrace()

        }finally {

            cursor?.close()

        }

        return achievementList
    }

    private fun dbOpen(writable : Boolean = false) : SQLiteDatabase{

        return if (writable) writableDatabase else readableDatabase

    }

}