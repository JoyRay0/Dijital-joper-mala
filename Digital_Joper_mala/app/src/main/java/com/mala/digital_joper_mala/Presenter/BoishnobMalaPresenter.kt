package com.mala.digital_joper_mala.Presenter

import android.content.Context
import com.mala.digital_joper_mala.Model.BoishnobItem
import com.mala.digital_joper_mala.Model.BoishnobMalaModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel

interface BoishnobMala{

    fun malaList (list: List<BoishnobItem>)
    fun lastCountCache (value : String)
    fun countLimit(limit: String)

}

class BoishnobMalaPresenter(
    private val context: Context,
    private val view : BoishnobMala
) {

    private val model = BoishnobMalaModel(context)
    private val scopeIO = CoroutineScope(Dispatchers.IO + SupervisorJob())
    private val scopeMain = CoroutineScope(Dispatchers.Main + SupervisorJob())


    fun getBoishnobMala(){

        view.malaList(model.getBoishnobMala())

    }

    fun setLastCountCache(value : String){

        model.setLastCountCache(value)

    }

    fun getLastCountCache(){

        view.lastCountCache(model.getLastCountCache())

    }

    fun setCountLimit(value: String){

        model.setCountLimit(value)

        view.countLimit(model.getCountLimit())

    }

    fun getCountLimit(){

        view.countLimit(model.getCountLimit())

    }

    fun onDestroy(){

        scopeIO.cancel()
        scopeMain.cancel()

    }

}