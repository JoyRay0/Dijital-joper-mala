package com.mala.digital_joper_mala.Database

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.mala.digital_joper_mala.Model.UserMantra

class UserMantraDatabase(
    context: Context
) : SQLiteOpenHelper(context, "user_mantra.db", null, 10) {

    private companion object{

        const val TABLE_NAME = "user_mantra_table"
        const val TITLE = "title"
        const val MANTRA = "mantra"
        const val ID = "id"

    }

    override fun onCreate(db: SQLiteDatabase?) {

        val sql = """CREATE TABLE IF NOT EXISTS $TABLE_NAME 
            ($ID INTEGER PRIMARY KEY AUTOINCREMENT, 
            $TITLE TEXT, 
            $MANTRA TEXT)""".trimIndent()

        db?.execSQL(sql)

    }

    override fun onUpgrade(
        db: SQLiteDatabase?,
        oldVersion: Int,
        newVersion: Int
    ) {

        db?.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
        onCreate(db)

    }

    fun insert(title : String, mantra : String){

        if (title.isEmpty() || mantra.isEmpty()) return

        if (checkDuplicate(title, mantra)) return

        val cv = ContentValues()

        val db = dbOpen(true)

        try {

            cv.put(TITLE, title)
            cv.put(MANTRA, mantra)

            db.insert(TABLE_NAME, null, cv)

        }catch (e : Exception){
            e.printStackTrace()
        }

    }

    fun getAllMantra(page : Int = 0) : List<UserMantra>{

        val limit = 20
        val offset = (page - 1) * limit

        val list : MutableList<UserMantra> = mutableListOf()

        val db = dbOpen()

        var cursor : Cursor? = null

        try {

            cursor = db.rawQuery("SELECT * FROM $TABLE_NAME ORDER BY id DESC LIMIT $limit OFFSET $offset", null)

            while (cursor.moveToNext()){

                val id = cursor.getInt(cursor.getColumnIndexOrThrow(ID))
                val title = cursor.getString(cursor.getColumnIndexOrThrow(TITLE))
                val mantra = cursor.getString(cursor.getColumnIndexOrThrow(MANTRA))

                list.add(UserMantra(
                    id = id,
                    title = title,
                    mantra = mantra
                ))

            }

        }catch (e : Exception){
            e.printStackTrace()
        }finally {
            cursor?.close()
        }

        return list

    }

    fun deleteOne(mantra : String) : Boolean{

        if (mantra.isEmpty()) return false

        val db = dbOpen(true)

       return try {

           db.delete(TABLE_NAME, "$MANTRA = ?", arrayOf(mantra)) > 0

       }catch (e : Exception) {
           e.printStackTrace()
           false
       }

    }

    private fun dbOpen(writeable : Boolean = false) : SQLiteDatabase{

        return if (writeable) writableDatabase else readableDatabase

    }


    private fun checkDuplicate(title: String, mantra: String) : Boolean{

        val db = dbOpen()

        var cursor : Cursor? = null

        var isExists = false

        try {

            cursor = db.rawQuery("SELECT 1 FROM $TABLE_NAME WHERE $TITLE = ? AND $MANTRA = ?", arrayOf(title, mantra))

            if (cursor.moveToFirst()) isExists = true

        }catch (e : Exception){

            e.printStackTrace()

        }finally {
            cursor?.close()
        }

        return isExists

    }

}//class