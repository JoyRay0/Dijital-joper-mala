package com.mala.digital_joper_mala.Model

import android.content.Context
import com.mala.digital_joper_mala.Database.JopHistoryDatabase

data class JopHistory(
    val dayDate : String = "",
    val count : Long = 0L
)

class JopHistoryModel(
    private val context: Context
) {

    private val jopHistoryDB = JopHistoryDatabase(context)


    fun jopInsert(dayDate: String, count : Long){

        if (dayDate.isEmpty() || count < 0L) return

        jopHistoryDB.insertJopCount(dayDate, count)

    }

    fun getOneJopCount(dayDate: String) : Long{

        if (dayDate.isEmpty()) return 0L

        return jopHistoryDB.getOneJopCount(dayDate)

    }

    fun getAllJopCount() : List<JopHistory>{

        return jopHistoryDB.getAllJopCount()

    }

    fun deleteAllJopCount(){

        jopHistoryDB.deleteAllJopCount()

    }


}