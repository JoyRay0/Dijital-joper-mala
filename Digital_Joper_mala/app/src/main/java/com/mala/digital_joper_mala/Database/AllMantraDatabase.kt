package com.mala.digital_joper_mala.Database

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import com.google.gson.internal.GsonTypes.arrayOf
import com.mala.digital_joper_mala.Model.MantraItem
import androidx.core.database.sqlite.transaction

class AllMantraDatabase(
    val context: Context
) : SQLiteOpenHelper(context, "all_mantra.db", null, 3) {

    private companion object{

        const val ID = "id"
        const val TITLE = "title"
        const val MANTRA = "mantra"
        const val ALL_MANTRA_TABLE = "all_mantra_table"
        const val USER_FAVORITE_MANTRA_TABLE = "user_favorite_table"

    }

    override fun onCreate(db: SQLiteDatabase?) {

        val fetchSql = """
CREATE TABLE IF NOT EXISTS $ALL_MANTRA_TABLE (
$ID INTEGER PRIMARY KEY AUTOINCREMENT,
$TITLE TEXT,
$MANTRA TEXT
) """.trimIndent()

        val favoriteSql = """
CREATE TABLE IF NOT EXISTS $USER_FAVORITE_MANTRA_TABLE (
$ID INTEGER PRIMARY KEY AUTOINCREMENT,
$TITLE TEXT,
$MANTRA TEXT
) """.trimIndent()


        db?.execSQL(fetchSql)
        db?.execSQL(favoriteSql)

    }

    override fun onUpgrade(
        db: SQLiteDatabase?,
        oldVersion: Int,
        newVersion: Int
    ) {

        db?.execSQL("DROP TABLE IF EXISTS $ALL_MANTRA_TABLE")
        db?.execSQL("DROP TABLE IF EXISTS $USER_FAVORITE_MANTRA_TABLE")

        onCreate(db)

    }

    fun allMantraInsert(list: List<MantraItem>, onSuccess : (Boolean) -> Unit){

        if (list.isEmpty()) return

        val db = dbOpen(true)

        db.beginTransaction()

        try {

            db.delete(ALL_MANTRA_TABLE, null, null)

            list.forEach { it ->

                val cv = ContentValues()

                cv.put(TITLE, it.title)
                cv.put(MANTRA, it.mantra)

                 db.insertOrThrow(ALL_MANTRA_TABLE, null, cv)

                /*
                if (result != -1L) {

                    throw Exception("Insert Failed")
                }

                 */

            }

            db.setTransactionSuccessful()
            onSuccess(true)

        } catch (e: Exception) {

            e.printStackTrace()
            onSuccess(false)

        }finally {
            db.endTransaction()
        }

    }

    fun getAllMantra() : List<MantraItem>{

        val mantraList : MutableList<MantraItem> = mutableListOf()

        val db = dbOpen()

        var cursor : Cursor? = null

        try {

            cursor = db.rawQuery("SELECT * FROM $ALL_MANTRA_TABLE", null)

            while (cursor.moveToNext()){

                val title = cursor.getString(cursor.getColumnIndexOrThrow(TITLE))
                val mantra = cursor.getString(cursor.getColumnIndexOrThrow(MANTRA))

                mantraList.add(
                    MantraItem(
                        title = title,
                        mantra = mantra
                    )
                )

            }

        }catch (e : Exception){
            e.printStackTrace()
        }finally {
            cursor?.close()
        }

        return mantraList
    }

    fun searchALlMantra(title: String) : List<MantraItem>{

        if (title.isEmpty()) return emptyList()

        val searchList : MutableList<MantraItem> = mutableListOf()

        val db = dbOpen()

        var cursor : Cursor? = null

        try {

            cursor = db.rawQuery("SELECT * FROM $ALL_MANTRA_TABLE WHERE $TITLE LIKE ?", arrayOf("%$title%"))

            while (cursor.moveToNext()){

                val title = cursor.getString(cursor.getColumnIndexOrThrow(TITLE))
                val mantra = cursor.getString(cursor.getColumnIndexOrThrow(MANTRA))

                searchList.add(
                    MantraItem(
                        title = title,
                        mantra = mantra
                    )
                )

            }

        }catch (e : Exception){
            e.printStackTrace()
        }finally {
            cursor?.close()
        }

        return searchList
    }

    /*

     - User Favorite Mantras

    */

    fun favoriteInsert(title: String, mantra: String, isInserted : (Boolean) -> Unit) {

        if (title.isEmpty() || mantra.isEmpty()) return

        if (isDuplicate(title, mantra, USER_FAVORITE_MANTRA_TABLE)) return

        val db = dbOpen(true)

        val cv = ContentValues()

        try {

            cv.put(TITLE, title)
            cv.put(MANTRA, mantra)

            val inserted =  db.insert(USER_FAVORITE_MANTRA_TABLE, null, cv)

            isInserted( if (inserted != -1L) true else false )

        }catch (e : Exception){

            e.printStackTrace()

        }

    }

    fun getFavoriteMantra() : List<MantraItem>{

        val favoriteMantraList : MutableList<MantraItem> = mutableListOf()

        val db = dbOpen()

        var cursor : Cursor? = null

        try {

            cursor = db.rawQuery("SELECT * FROM $USER_FAVORITE_MANTRA_TABLE ORDER BY id DESC", null)

            while (cursor.moveToNext()){

                val title = cursor.getString(cursor.getColumnIndexOrThrow(TITLE))
                val mantra = cursor.getString(cursor.getColumnIndexOrThrow(MANTRA))

                favoriteMantraList.add(
                    MantraItem(
                        title = title,
                        mantra = mantra
                    )
                )

            }

        }catch (e : Exception){
            e.printStackTrace()
        }finally {
            cursor?.close()
        }

        return favoriteMantraList
    }

    fun deleteFavoriteMantra(mantra: String) : Boolean{

        if (mantra.isEmpty()) return false

        val db = dbOpen(true)

        return try {

            db.delete(USER_FAVORITE_MANTRA_TABLE, "$MANTRA = ?", arrayOf(mantra)) > 0

        }catch (e : Exception){

            e.printStackTrace()
            false

        }

    }

    private fun isDuplicate(title: String, mantra: String, tableName : String) : Boolean{

        val db = dbOpen()

        var cursor : Cursor? = null

        var isExists = false

        try {

            cursor = db.rawQuery("SELECT 1 FROM $tableName WHERE $TITLE = ? AND $MANTRA = ?", arrayOf(title, mantra))

            if (cursor.moveToFirst()) isExists = true

        }catch (e : Exception){

            e.printStackTrace()

        }finally {
            cursor?.close()
        }

        return isExists

    }

    private fun dbOpen(writable : Boolean = false) : SQLiteDatabase{

        return if (writable) writableDatabase else readableDatabase

    }

}