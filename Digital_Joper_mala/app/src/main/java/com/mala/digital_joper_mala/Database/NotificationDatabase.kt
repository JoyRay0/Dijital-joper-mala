package com.mala.digital_joper_mala.Database

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.mala.digital_joper_mala.Model.NotificationItem

class NotificationDatabase(
    val context : Context
) : SQLiteOpenHelper(
    context,
    "notification.db",
    null,
    1
) {

    private companion object{

        const val ID = "id"
        const val TITLE = "title"
        const val DESCRIPTION = "description"
        const val IS_SHOWED = "is_showed"
        const val TABLE_NAME = "notification_table"

    }

    override fun onCreate(db: SQLiteDatabase?) {

        val createSql = """
CREATE TABLE IF NOT EXISTS $TABLE_NAME (
$ID INTEGER PRIMARY KEY AUTOINCREMENT,
$TITLE TEXT NOT NULL UNIQUE,
$DESCRIPTION TEXT NOT NULL UNIQUE,
$IS_SHOWED INTEGER DEFAULT 0
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

    fun insertNotification(title : String, description : String) : Long {

        if (title.isEmpty() || description.isEmpty()) return -1L

        val db = dbOpen(true)
        val cv = ContentValues()

        var inserted = 0L

        try {

            cv.put(TITLE, title)
            cv.put(DESCRIPTION, description)
            cv.put(IS_SHOWED, false)

            inserted =  db.insert(TABLE_NAME, null, cv)

        }catch ( e : Exception){

            e.printStackTrace()

        }

        return inserted

    }

    fun getAllNotification(page : Int) : List<NotificationItem>{

        if (page < 0) return emptyList()

        val limit = 20
        val offset = (page - 1) * limit

        val notificationList = mutableListOf<NotificationItem>()

        val db = dbOpen()

        var cursor : Cursor? = null

        try {

            cursor = db.rawQuery("SELECT * FROM $TABLE_NAME ORDER BY id DESC LIMIT $limit OFFSET $offset", null)

            while (cursor.moveToNext()){

                val id = cursor.getLong(cursor.getColumnIndexOrThrow(ID))
                val title = cursor.getString(cursor.getColumnIndexOrThrow(TITLE))
                val description = cursor.getString(cursor.getColumnIndexOrThrow(DESCRIPTION))
                val isShowed = cursor.getInt(cursor.getColumnIndexOrThrow(IS_SHOWED))

                notificationList.add(NotificationItem(
                    id = id,
                    title = title,
                    description = description,
                    isShowed = if (isShowed == 0) false else true
                ))

            }

        }catch ( e : Exception){

            e.printStackTrace()

        }finally {
            cursor?.close()
        }

        return notificationList.toList()

    }

    fun deleteAllNotification() : Boolean{

        val db = dbOpen(true)

        var isDeleted = false

        try {

            isDeleted =  db.delete(TABLE_NAME, null, null) > 0

        }catch (e : Exception){
            e.printStackTrace()
        }

        return isDeleted

    }

    fun hasSeenNotification(title: String) : Boolean{

        if (title.isEmpty()) return false

        val db = dbOpen(true)

        var isUpdated = false

        val cv = ContentValues()

        try {

            cv.put(IS_SHOWED, 1)

            isUpdated = db.update(TABLE_NAME, cv, "$TITLE = ?", arrayOf(title)) > 0

        }catch (e : Exception){

            e.printStackTrace()

        }

        return isUpdated
    }

    fun unseenNotificationCount() : Int{

        val db = dbOpen()

        var countNotification = 0

        var cursor : Cursor? = null

        try {

            cursor = db.rawQuery("SELECT $IS_SHOWED FROM $TABLE_NAME WHERE $IS_SHOWED = ?", arrayOf("0"))

            while (cursor.moveToNext()){

                countNotification++

            }

        }catch ( e : Exception){

            e.printStackTrace()

        }finally {
            cursor?.close()
        }

        return countNotification

    }


    private fun dbOpen(writable : Boolean = false) : SQLiteDatabase{

        return if (writable) writableDatabase else readableDatabase

    }

}