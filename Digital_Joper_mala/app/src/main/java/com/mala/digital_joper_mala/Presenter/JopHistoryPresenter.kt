package com.mala.digital_joper_mala.Presenter

import android.content.Context
import com.mala.digital_joper_mala.Model.JopHistory
import com.mala.digital_joper_mala.Model.JopHistoryModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

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
    private val view : JopCountHistory
) {

    private val model = JopHistoryModel(context)
    private val scopeIO = CoroutineScope(Dispatchers.IO + SupervisorJob())
    private val scopeMain = CoroutineScope(Dispatchers.Main + SupervisorJob())

    fun insertJopCount(dayDate : String, count : Long){

        scopeIO.launch {

            model.jopInsert(dayDate, count)

        }

    }

    fun getAllJopCountHistory(){

        view.historyStatus(History.HistoryPending.value)

        scopeIO.launch {

            val data = model.getAllJopCount()

            withContext(Dispatchers.Main){

                if (data.isEmpty()){

                    view.historyStatus(History.HistoryFailed.value)

                }else{

                    view.historyStatus(History.HistorySuccess.value)
                    view.historyList(data)

                }

            }

        }

    }

    fun getOneJopCount(dayDate: String){

        view.historyStatus(History.HistoryPending.value)

        scopeIO.launch {

            val countData = model.getOneJopCount(dayDate)

            if (countData <= 0L){

                view.historyStatus(History.HistoryFailed.value)

            }else{

                view.historyStatus(History.HistorySuccess.value)
                view.singleCountHistory(countData)

            }

        }

    }

    fun onDestroy(){

        scopeIO.cancel()
        scopeMain.cancel()

    }

}