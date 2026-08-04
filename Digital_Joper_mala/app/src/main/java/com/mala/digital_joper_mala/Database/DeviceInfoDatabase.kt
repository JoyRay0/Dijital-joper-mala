package com.mala.digital_joper_mala.Database

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.mala.digital_joper_mala.Model.Tracker

class DeviceInfoDatabase(val context: Context) : SQLiteOpenHelper(
    context, "device_info.db", null, 1
) {

    private companion object{

        const val DEVICE_INFO_TABLE_NAME = "device_info"
        const val ID = "id"
        const val COUNTRY = "country"
        const val SDK_VERSION = "sdk"
        const val ANDROID_VERSION = "android_version"

    }

    override fun onCreate(db: SQLiteDatabase?) {

        val deviceInfo = """
CREATE TABLE IF NOT EXISTS $DEVICE_INFO_TABLE_NAME (
$ID INTEGER PRIMARY KEY AUTOINCREMENT,
$COUNTRY TEXT DEFAULT NULL,
$ANDROID_VERSION TEXT DEFAULT NULL,
$SDK_VERSION TEXT DEFAULT NULL
)
""".trimIndent()

        db?.execSQL(deviceInfo)

        db?.execSQL("INSERT INTO $DEVICE_INFO_TABLE_NAME DEFAULT VALUES")

    }

    override fun onUpgrade(
        db: SQLiteDatabase?,
        oldVersion: Int,
        newVersion: Int
    ) {
        db?.execSQL("DROP TABLE IF EXISTS $DEVICE_INFO_TABLE_NAME")
        onCreate(db)
    }

    fun insertDeviceInfo(androidVersion : String, sdkVersion : Int, countryCode : String){

        if (androidVersion.isEmpty() || sdkVersion <= 0 || countryCode.isEmpty()) return

        val db = dbOpen(true)

        val cv = ContentValues()

        try {

            cv.put(ANDROID_VERSION, androidVersion)
            cv.put(SDK_VERSION, sdkVersion.toString())
            cv.put(COUNTRY, countryCode)

            db.update(DEVICE_INFO_TABLE_NAME, cv, "$ID = ?", arrayOf("1"))

        }catch (e : Exception){

            e.printStackTrace()

        }

    }

    fun getDeviceInfo() : Tracker?{

        val db = dbOpen()

        var cursor : Cursor? = null

        var deviceData : Tracker? = null

        try {

            cursor = db.rawQuery("SELECT * FROM $DEVICE_INFO_TABLE_NAME WHERE $ID = ?", arrayOf("1"))

            if (cursor.moveToFirst()){

                val androidVersion = cursor.getString(cursor.getColumnIndexOrThrow(ANDROID_VERSION)) ?: ""
                val sdkVersion = cursor.getString(cursor.getColumnIndexOrThrow(SDK_VERSION)) ?: ""
                val countryCode = cursor.getString(cursor.getColumnIndexOrThrow(COUNTRY)) ?: ""

                deviceData = Tracker(
                    androidVersion = androidVersion,
                    sdkVersion = sdkVersion,
                    countryCode = countryCode
                )

            }

        }catch (e : Exception){

            e.printStackTrace()

        }finally {
            cursor?.close()
        }

        return deviceData

    }

    fun resetDeviceInfo(){

        val db = dbOpen(true)

        val cv = ContentValues()

        try {

            cv.putNull(ANDROID_VERSION)
            cv.putNull(SDK_VERSION)
            cv.putNull(COUNTRY)

            db.update(DEVICE_INFO_TABLE_NAME, cv, "$ID = ?", arrayOf("1"))

        }catch (e : Exception){
            e.printStackTrace()
        }

    }

    private fun dbOpen(writable : Boolean = false) : SQLiteDatabase{

        return if (writable) writableDatabase else readableDatabase

    }

}