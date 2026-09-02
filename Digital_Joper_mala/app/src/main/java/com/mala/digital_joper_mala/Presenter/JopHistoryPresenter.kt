package com.mala.digital_joper_mala.Presenter

import android.content.Context
import com.mala.digital_joper_mala.Helper.ComposeHelper
import com.mala.digital_joper_mala.Model.JopHistory
import com.mala.digital_joper_mala.Model.JopHistoryModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

interface JopCountHistory{

    fun historyList(list: List<JopHistory>)
    fun historyStatus(status : String)
    fun singleCountHistory(count : Long)

}

enum class History(val value : String){

    HistoryPending("history_pending"),
    HistorySuccess("history_success"),
    HistoryFailed("history_failed")

}

class JopHistoryPresenter(
    private val context : Context,
    private val view : JopCountHistory? = null
) {

    private val model = JopHistoryModel(context)
    private val scopeIO = CoroutineScope(Dispatchers.IO + SupervisorJob())
    private val scopeMain = CoroutineScope(Dispatchers.Main + SupervisorJob())

    fun insertJopCount(count : Long){

        var currentDay = ""
        var currentDate = ""
        val currentYear = SimpleDateFormat("yyyy", Locale.getDefault()).format(Date()).toIntOrNull()

        ComposeHelper().getDate { bDay, bDate ->

            currentDay = bDay
            currentDate = bDate

        }

        scopeIO.launch {

            if (currentYear != null){

                model.deleteAllJopCount(currentYear)

                model.jopInsert(currentDay, currentDate, currentYear, count)

            }

        }

    }

    fun getAllJopCountHistory(){

        view?.historyStatus(History.HistoryPending.value)

        scopeIO.launch {

            val data = model.getAllJopCount()

            withContext(Dispatchers.Main){

                if (data.isEmpty()){

                    view?.historyStatus(History.HistoryFailed.value)

                }else{

                    view?.historyStatus(History.HistorySuccess.value)
                    view?.historyList(data)

                }

            }

        }

    }

    fun getOneJopCount(){

        view?.historyStatus(History.HistoryPending.value)

        var currentDay = ""
        var currentDate = ""

        ComposeHelper().getDate { bDay, bDate ->

            currentDay = bDay
            currentDate = bDate

        }

        scopeIO.launch {

            val countData = model.getOneJopCount(currentDate)

            if (countData <= 0L){

                view?.historyStatus(History.HistoryFailed.value)

            }else{

                view?.historyStatus(History.HistorySuccess.value)
                view?.singleCountHistory(countData)

            }

        }

    }

    fun onDestroy(){

        scopeIO.cancel()
        scopeMain.cancel()

    }

}