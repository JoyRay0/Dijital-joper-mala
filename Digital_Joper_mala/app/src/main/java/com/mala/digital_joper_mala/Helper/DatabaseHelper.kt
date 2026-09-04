package com.mala.digital_joper_mala.Helper

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

abstract class DatabaseHelper<T>(
    val context : Context,
    val dbName : String,
    val dbVersion : Int
) : SQLiteOpenHelper(context, dbName, null, dbVersion
) {
    
    abstract fun onInsert() : Long
    abstract fun onGetAll(page : Int) : List<T>
    abstract fun onGetOne() : T
    abstract fun onDelete() : Boolean
    abstract fun onDeleteAll() : Boolean

    /* common tools */

    protected fun createTable(
        tableName: String,
        columnName : String
    ) : String{

        val sql = """
            CREATE TABLE IF NOT EXISTS $tableName 
            (id INTEGER PRIMARY KEY AUTOINCREMENT,
            $columnName
            )
        """.trimIndent()

        return sql
    }

    protected fun upgradeTable(
        tableName: String
    ) : String{

        return "DROP TABLE IF EXISTS $tableName"

    }

    protected fun insertData(
        tableName : String,
        values : ContentValues
    ) : Long{

       return writableDatabase.insert(tableName, null, values)

    }

    protected fun getData(
        tableName: String,
        otherSQL : String,
        selectionArgs : Array<String>?
    ) : Cursor{

        val result = if (otherSQL.isEmpty()){

            readableDatabase.rawQuery("SELECT * FROM $tableName", selectionArgs)

        }else{

            readableDatabase.rawQuery("SELECT * FROM $tableName $otherSQL", selectionArgs)

        }

        return result

    }

    protected fun deleteOne(
        tableName: String,
        whereClause : String,
        whereArgs : Array<String>?
    ) : Boolean{

        return writableDatabase.delete(tableName, whereClause, whereArgs) > 0

    }

    protected fun deleteAll(
        tableName: String
    ) : Boolean{

        return writableDatabase.delete(tableName, null, null) > 0

    }

    protected fun updateData(
        tableName: String,
        values: ContentValues,
        whereClause: String?,
        whereArgs : Array<String>?
    ) : Boolean{

        return writableDatabase.update(
            tableName,
            values,
            whereClause,
            whereArgs
        ) > 0

    }

    protected fun isExits(
        tableName: String,
        columName : String,
        whereClause: String,
        whereArgs: Array<String>?,

    ) : Boolean{

        val sql = readableDatabase.query(
            tableName,
            arrayOf(columName),
            whereClause,
            whereArgs,
            null,
            null,
            null,
            "1"
        )

        return sql.use { it.moveToFirst() }

    }

}