package com.mala.digital_joper_mala.Database

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.mala.digital_joper_mala.Model.Tracker

class DeviceInfoDatabase(val context: Context) : SQLiteOpenHelper(
    context, "device_info.db", null, 3
) {

    private companion object{

        const val DEVICE_INFO_TABLE_NAME = "device_info"
        const val ID = "id"
        const val DEVICE_ID = "device_id"
        const val COUNTRY = "country"
        const val SDK_VERSION = "sdk"
        const val ANDROID_VERSION = "android_version"
        const val LAST_OPEN = "last_open"

    }

    override fun onCreate(db: SQLiteDatabase?) {

        val deviceInfo = """
CREATE TABLE IF NOT EXISTS $DEVICE_INFO_TABLE_NAME (
$ID INTEGER PRIMARY KEY AUTOINCREMENT,
$DEVICE_ID TEXT DEFAULT NULL,
$COUNTRY TEXT DEFAULT NULL,
$ANDROID_VERSION TEXT DEFAULT NULL,
$SDK_VERSION TEXT DEFAULT NULL,
$LAST_OPEN TEXT DEFAULT NULL
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

        val info = getDeviceInfo()

        try {

            if (info?.androidVersion != androidVersion){

                cv.put(ANDROID_VERSION, androidVersion)

            }

            if (info?.sdkVersion != sdkVersion.toString()){

                cv.put(SDK_VERSION, sdkVersion.toString())

            }

            if (info?.countryCode != countryCode){

                cv.put(COUNTRY, countryCode)

            }

            if (cv.size() > 0){

                db.update(DEVICE_INFO_TABLE_NAME, cv, "$ID = ?", arrayOf("1"))

            }

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

                val deviceId = cursor.getString(cursor.getColumnIndexOrThrow(DEVICE_ID)) ?: ""
                val androidVersion = cursor.getString(cursor.getColumnIndexOrThrow(ANDROID_VERSION)) ?: ""
                val sdkVersion = cursor.getString(cursor.getColumnIndexOrThrow(SDK_VERSION)) ?: ""
                val countryCode = cursor.getString(cursor.getColumnIndexOrThrow(COUNTRY)) ?: ""
                val lastOpen = cursor.getString(cursor.getColumnIndexOrThrow(LAST_OPEN)) ?: ""

                deviceData = Tracker(
                    deviceId = deviceId,
                    androidVersion = androidVersion,
                    sdkVersion = sdkVersion,
                    countryCode = countryCode,
                    lastOpen = lastOpen
                )

            }

        }catch (e : Exception){

            e.printStackTrace()

        }finally {
            cursor?.close()
        }

        return deviceData

    }

    fun insertDeviceId(deviceId : String){

        if (deviceId.isEmpty()) return

        val db = dbOpen(true)

        val cv = ContentValues()

        try {

            cv.put(DEVICE_ID, deviceId)

            db.update(DEVICE_INFO_TABLE_NAME, cv, "$ID = ?", arrayOf("1"))

        }catch (e : Exception){

            e.printStackTrace()

        }

    }

    fun getDeviceId() : String {

        val db = dbOpen()

        var cursor : Cursor? = null

        var deviceId = ""

        try {

            cursor = db.rawQuery("SELECT $DEVICE_ID FROM $DEVICE_INFO_TABLE_NAME WHERE $ID = ?", arrayOf("1"))

            if (cursor.moveToFirst()){

                deviceId = cursor.getString(cursor.getColumnIndexOrThrow(DEVICE_ID)) ?: ""

            }

        }catch (e : Exception){

            e.printStackTrace()

        }finally {
            cursor?.close()
        }

        return deviceId

    }

    fun resetDeviceInfo(){

        val db = dbOpen(true)

        val cv = ContentValues()

        try {

            cv.putNull(DEVICE_ID)
            cv.putNull(ANDROID_VERSION)
            cv.putNull(SDK_VERSION)
            cv.putNull(COUNTRY)
            cv.putNull(LAST_OPEN)

            db.update(DEVICE_INFO_TABLE_NAME, cv, "$ID = ?", arrayOf("1"))

        }catch (e : Exception){
            e.printStackTrace()
        }

    }

    fun updateLastOpen(lastOpen : String){

        if (lastOpen.isEmpty()) return

        val db = dbOpen(true)

        val cv = ContentValues()

        try {

            cv.put(LAST_OPEN, lastOpen)

            db.update(DEVICE_INFO_TABLE_NAME, cv, "$ID = ?", arrayOf("1"))

        }catch (e : Exception){
            e.printStackTrace()
        }

    }

    private fun dbOpen(writable : Boolean = false) : SQLiteDatabase{

        return if (writable) writableDatabase else readableDatabase

    }

}