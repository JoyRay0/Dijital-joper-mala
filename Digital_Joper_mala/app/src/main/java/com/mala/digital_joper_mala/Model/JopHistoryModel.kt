package com.mala.digital_joper_mala.Model

import android.content.Context
import com.mala.digital_joper_mala.Database.JopHistoryDatabase

data class JopHistory(
    val id : Int = 0,
    val day : String = "",
    val date : String = "",
    val count : Long = 0L
)

class JopHistoryModel(
    private val context: Context
) {

    private val jopHistoryDB = JopHistoryDatabase(context)


    fun jopInsert(day: String, date : String, year : Int, count : Long){

        if (day.isEmpty() || date.isEmpty() || count < 0L || year < 2026) return

        jopHistoryDB.insertJopCount(day, date, year,count)

    }

    fun getOneJopCount(date: String) : Long{

        if (date.isEmpty()) return 0L

        return jopHistoryDB.getOneJopCount(date)

    }

    fun getAllJopCount(page : Int) : List<JopHistory>{

        return jopHistoryDB.getAllJopCount(page)

    }

    fun deleteAllJopCount(year: Int){

        if (year < 2026) return

        jopHistoryDB.deleteAllJopCount(year)

    }


}