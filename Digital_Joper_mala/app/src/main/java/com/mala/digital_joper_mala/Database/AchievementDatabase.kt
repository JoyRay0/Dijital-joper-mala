package com.mala.digital_joper_mala.Database

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

import com.mala.digital_joper_mala.Model.Achievement

class AchievementDatabase(
    val context : Context
) : SQLiteOpenHelper(context, "achievement.db", null, 2) {

    private companion object{

        const val ID = "id"
        const val TABLE_NAME = "achievement_table"
        const val MALA_NAME = "mala_name"
        const val ACHIEVEMENT_COUNT = "achievement_count"
        const val IS_COMPLETE = "is_complete"

    }

    private val malaNameList = listOf(
        "easy_mala", "boishnob_mala", "shiv_mala"
    )
    private val countList = listOf(
        "1000", "5000", "10000", "50000", "100000", "500000"
    )

    override fun onCreate(db: SQLiteDatabase?) {

        val create_sql = """
CREATE TABLE IF NOT EXISTS $TABLE_NAME (
$ID INTEGER PRIMARY KEY AUTOINCREMENT,
$MALA_NAME TEXT NOT NULL,
$ACHIEVEMENT_COUNT TEXT NOT NULL,
$IS_COMPLETE INTEGER DEFAULT 0
)
""".trimIndent()

        db?.execSQL(create_sql)

        achievementInsert(db)

    }

    override fun onUpgrade(
        db: SQLiteDatabase?,
        oldVersion: Int,
        newVersion: Int
    ) {
        db?.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
    }

    private fun achievementInsert(db : SQLiteDatabase?){

        val cv = ContentValues()

        try {

            for (name in malaNameList){

                for (count in countList){

                    cv.put(MALA_NAME, name)
                    cv.put(ACHIEVEMENT_COUNT, count)
                    cv.put(IS_COMPLETE, 0)

                    db?.insert(TABLE_NAME, null, cv)

                }

            }

        }catch (e : Exception){

            e.printStackTrace()

        }

    }

    fun updateAchievement(malaName: String, achievementCount: String) : Boolean{

        if (malaName.isEmpty() || achievementCount.isEmpty()) return false

        if (malaName !in malaNameList || achievementCount !in countList) return false

        if (!isDuplicate(malaName, achievementCount)) return false

        val db = dbOpen(true)

        val cv = ContentValues()

        try {

            cv.put(IS_COMPLETE, 1)

            return db.update(TABLE_NAME, cv, "$MALA_NAME = ? AND $ACHIEVEMENT_COUNT = ?", arrayOf(malaName, achievementCount)) > 0

        }catch (e : Exception){

            e.printStackTrace()

            return false

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

                val count = cursor.getString(cursor.getColumnIndexOrThrow(ACHIEVEMENT_COUNT))

                achievementList.add(Achievement(
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

    fun getAllAchievement() : List<Achievement> {

        val achievementList = mutableListOf<Achievement>()

        val db = dbOpen()

        var cursor : Cursor? = null

        try {

            cursor = db.rawQuery("SELECT * FROM $TABLE_NAME", null)

            while (cursor.moveToNext()){

                val id = cursor.getInt(cursor.getColumnIndexOrThrow(ID))
                val malaName = cursor.getString(cursor.getColumnIndexOrThrow(MALA_NAME))
                val count = cursor.getString(cursor.getColumnIndexOrThrow(ACHIEVEMENT_COUNT))
                val is_complete = cursor.getInt(cursor.getColumnIndexOrThrow(IS_COMPLETE))

                achievementList.add(Achievement(
                    id = id,
                    malaName = malaName,
                    achievementCount = count,
                    isCompleted = if (is_complete == 0) false else true
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

    private fun isDuplicate(malaName: String, count: String) : Boolean{

        val db = dbOpen()

        var cursor : Cursor? = null

        var isExists = false

        try {

            cursor = db.rawQuery("SELECT * FROM $TABLE_NAME WHERE $MALA_NAME = ? AND $ACHIEVEMENT_COUNT = ?", arrayOf(malaName, count))

            if (cursor.moveToFirst()) isExists = true

        }catch (e : Exception){

            e.printStackTrace()

        }finally {
            cursor?.close()
        }

        return isExists

    }

}