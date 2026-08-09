package com.mala.digital_joper_mala.Presenter

import android.content.Context
import com.mala.digital_joper_mala.Model.AllMantraModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

interface AllMantra{

    fun dialogStatus(value : Boolean)

}

class AllMantraPresenter(
    private val context : Context,
    private val view : AllMantra
) {

    private val model = AllMantraModel(context)
    private val scopeIO = CoroutineScope(Dispatchers.IO + SupervisorJob())
    private val scopeMain = CoroutineScope(Dispatchers.Main + SupervisorJob())


    fun setAllMantraCache(value : Boolean){

        scopeIO.launch {

            model.setAllMantraCache(value)

        }

    }

    fun getAllMantraCache(){

        scopeIO.launch {

            val data = model.getAllMantraCache()

            withContext(Dispatchers.Main){

                view.dialogStatus(data)

            }

        }

    }

    fun onDestroy(){

        scopeIO.cancel()
        scopeMain.cancel()

    }

}