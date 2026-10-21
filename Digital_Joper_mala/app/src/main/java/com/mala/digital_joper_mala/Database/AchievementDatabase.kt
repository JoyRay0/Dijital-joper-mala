package com.mala.digital_joper_mala.Database

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

import com.mala.digital_joper_mala.Model.Achievement

class AchievementDatabase(
    val context : Context
) : SQLiteOpenHelper(context, "achievement.db", null, 5) {

    private companion object{

        const val ID = "id"
        const val TABLE_NAME = "achievement_table"
        const val MALA_NAME = "mala_name"
        const val ACHIEVEMENT_TARGET = "achievement_target"
        const val IS_COMPLETE = "is_complete"
        const val TOTAL_USER_COUNT = "total_user_count"

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
$ACHIEVEMENT_TARGET TEXT NOT NULL,
$IS_COMPLETE INTEGER DEFAULT 0,
$TOTAL_USER_COUNT INTEGER DEFAULT 0
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

        if (oldVersion < newVersion){

            db?.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")

        }

        onCreate(db)

    }

    fun insertAchievementCount(malaName: String, count : String){

        val addCount = count.toIntOrNull() ?: 0

        if (malaName.isEmpty() || addCount <= 0) return

        if (malaName !in malaNameList) return

        val db = dbOpen(true)

        try {

            /* Insert Achievement count */

            val newCount = getOneAchievement(malaName) + addCount

            val insertCv = ContentValues().apply {

                put(TOTAL_USER_COUNT, newCount)

            }

            db.update(TABLE_NAME, insertCv, "$MALA_NAME = ? AND $IS_COMPLETE = ?", arrayOf(malaName, "0"))

            /* Update achievement status when target achievement is completed */

            for (target in countList){

                if (newCount >= target.toInt()){

                    val completeValues = ContentValues().apply {

                        put(IS_COMPLETE, 1)
                        put(TOTAL_USER_COUNT, newCount)

                    }

                    db.update(
                        TABLE_NAME,
                        completeValues,
                        "$IS_COMPLETE = 0 AND $ACHIEVEMENT_TARGET = ? AND $MALA_NAME = ?",
                        arrayOf(target, malaName)
                    )

                }

            }

        }catch (e : Exception){

            e.printStackTrace()

        }

    }

    fun getAchievement(malaName : String) : List<Achievement> {

        if (malaName.isEmpty()) return emptyList()

        val list = mutableListOf<Achievement>()

        val db = dbOpen()

        var cursor : Cursor? = null

        try {

            cursor = db.rawQuery("SELECT $ACHIEVEMENT_TARGET, $IS_COMPLETE FROM $TABLE_NAME WHERE $MALA_NAME = ?", arrayOf(malaName))

            while (cursor.moveToNext()){

                val count = cursor.getString(cursor.getColumnIndexOrThrow(ACHIEVEMENT_TARGET))
                val is_complete = cursor.getInt(cursor.getColumnIndexOrThrow(IS_COMPLETE))
                val user_count = cursor.getInt(cursor.getColumnIndexOrThrow(TOTAL_USER_COUNT))

                list.add(Achievement(
                    targetAchievementCount = count,
                    isCompleted = is_complete > 0,
                    userCount = user_count
                ))
            }

        }catch (e : Exception){

            e.printStackTrace()

        }finally {

            cursor?.close()

        }

        return list

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
                val count = cursor.getString(cursor.getColumnIndexOrThrow(ACHIEVEMENT_TARGET))
                val is_complete = cursor.getInt(cursor.getColumnIndexOrThrow(IS_COMPLETE))
                val user_count = cursor.getInt(cursor.getColumnIndexOrThrow(TOTAL_USER_COUNT))

                achievementList.add(Achievement(
                    id = id,
                    malaName = malaName,
                    targetAchievementCount = count,
                    isCompleted = if (is_complete == 0) false else true,
                    userCount = user_count
                ))

            }

        }catch (e : Exception){

            e.printStackTrace()

        }finally {

            cursor?.close()

        }

        return achievementList
    }

    fun getOneAchievement(malaName : String) : Int {

        if (malaName.isEmpty()) return 0

        val db = dbOpen()

        var cursor : Cursor? = null

        var userCount = 0

        try {

            cursor = db.rawQuery("SELECT $TOTAL_USER_COUNT, $ACHIEVEMENT_TARGET FROM $TABLE_NAME WHERE $MALA_NAME = ? AND $ACHIEVEMENT_TARGET = ? ORDER BY CAST($ACHIEVEMENT_TARGET AS INTEGER) ASC LIMIT 1", arrayOf(malaName, "500000"))

            if (cursor.moveToFirst()){

                val count = cursor.getInt(cursor.getColumnIndexOrThrow(TOTAL_USER_COUNT))

                userCount = count

            }

        }catch (e : Exception){

            e.printStackTrace()

        }finally {

            cursor?.close()

        }

        return userCount

    }

    /*
    private fun isAlreadyUpdated(malaName : String, count: String) : Boolean {

        var isUpdated = false

        val db = dbOpen()

        var cursor : Cursor? = null

        try {

            cursor = db.rawQuery("SELECT $IS_COMPLETE FROM $TABLE_NAME WHERE $MALA_NAME = ? AND $ACHIEVEMENT_TARGET = ?", arrayOf(malaName, count))

            if (cursor.moveToFirst()){

                val is_complete = cursor.getInt(cursor.getColumnIndexOrThrow(IS_COMPLETE))

                isUpdated = is_complete > 0
            }

        }catch (e : Exception){

            e.printStackTrace()

        }finally {

            cursor?.close()

        }

        return isUpdated

    }
    */
    private fun achievementInsert(db : SQLiteDatabase?){

        try {

            for (name in malaNameList){

                for (count in countList){
                    val cv = ContentValues()

                    cv.put(MALA_NAME, name)
                    cv.put(ACHIEVEMENT_TARGET, count)
                    cv.put(IS_COMPLETE, 0)

                    db?.insert(TABLE_NAME, null, cv)

                }

            }

        }catch (e : Exception){

            e.printStackTrace()

        }

    }

    private fun dbOpen(writable : Boolean = false) : SQLiteDatabase{

        return if (writable) writableDatabase else readableDatabase

    }


}