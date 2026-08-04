package com.mala.digital_joper_mala.Database

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.mala.digital_joper_mala.Model.TrackerData
import com.mala.digital_joper_mala.Helper.ACTIVITY

class ScreenTrackerDatabase(val context: Context) : SQLiteOpenHelper(
    context, "screen_track.db", null, 4
) {

    private companion object{

        const val ACTIVITY_TABLE_NAME = "screen_tracker"
        const val ID = "id"
        const val ACTIVITY_ = "activity"
        const val DURATION = "duration"

    }

    override fun onCreate(db: SQLiteDatabase?) {

        val activityTracker = """
CREATE TABLE IF NOT EXISTS $ACTIVITY_TABLE_NAME (
$ID INTEGER PRIMARY KEY AUTOINCREMENT,
$ACTIVITY_ TEXT NOT NULL UNIQUE,
$DURATION INTEGER DEFAULT 0
)
""".trimIndent()

        db?.execSQL(activityTracker)

    }

    override fun onUpgrade(
        db: SQLiteDatabase?,
        oldVersion: Int,
        newVersion: Int
    ) {

        db?.execSQL("DROP TABLE IF EXISTS $ACTIVITY_TABLE_NAME")
        onCreate(db)

    }

    fun insert(act : ACTIVITY, duration : Long){

        if (duration <= 0) return

        val db = dbOpen(true)

        val oldTrackedData = getTrackedData(act.value)

        val cv = ContentValues()

        try {

            if (oldTrackedData == -1L){

                cv.put(ACTIVITY_, act.value)
                cv.put(DURATION, duration)

                db.insert(ACTIVITY_TABLE_NAME, null, cv)

            }else{

                cv.put(DURATION, oldTrackedData + duration)

                db.update(ACTIVITY_TABLE_NAME, cv, "$ACTIVITY_ = ?", arrayOf(act.value))

            }

        }catch (e : Exception){

            e.printStackTrace()

        }

    }

    fun getAll() : List<TrackerData>{

        val list : MutableList<TrackerData> = mutableListOf()

        val db = dbOpen()

        var cursor : Cursor? = null

        try {

            cursor = db.rawQuery("SELECT * FROM $ACTIVITY_TABLE_NAME", null)

            while (cursor.moveToNext()){

                val act = cursor.getString(cursor.getColumnIndexOrThrow(ACTIVITY_))
                val duration = cursor.getLong(cursor.getColumnIndexOrThrow(DURATION))

                list.add(TrackerData(
                    activity = act,
                    duration = duration
                ))

            }

        }catch (e : Exception){

            e.printStackTrace()

        }finally {

            cursor?.close()

        }

        return list

    }

    fun resetAll(){

        val db = dbOpen(true)

        val cv = ContentValues()

        try {

            cv.put(DURATION, 0)

            db.update(ACTIVITY_TABLE_NAME, cv, null, null)

        }catch (e : Exception){

            e.printStackTrace()

        }

    }

    private fun dbOpen(writable : Boolean = false) : SQLiteDatabase{

        return if (writable) writableDatabase else readableDatabase

    }

    private fun getTrackedData(act : String) : Long{

        val db = dbOpen()

        var trackedData = -1L

        var cursor : Cursor? = null

        try {

            cursor = db.rawQuery("SELECT $DURATION FROM $ACTIVITY_TABLE_NAME WHERE $ACTIVITY_ = ?", arrayOf(act))


            if (cursor.moveToFirst()){

                trackedData = cursor.getLong(cursor.getColumnIndexOrThrow(DURATION))

            }

        }catch (e : Exception){

            e.printStackTrace()

        }finally {
            cursor?.close()
        }

        return trackedData

    }

}