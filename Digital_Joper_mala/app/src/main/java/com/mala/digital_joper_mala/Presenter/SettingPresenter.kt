package com.mala.digital_joper_mala.Presenter

import com.mala.digital_joper_mala.Helper.CacheHelper_
import com.mala.digital_joper_mala.Model.SettingModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel

interface Setting{

    fun cache(value : String)

}

class SettingPresenter(
    private val view : Setting,
    private val cacheHelper: CacheHelper_
) {

    private val model = SettingModel(cacheHelper)

    private val scopeIO = CoroutineScope(Dispatchers.IO + SupervisorJob())
    private val scopeMain = CoroutineScope(Dispatchers.Main + SupervisorJob())

    fun setCache(key : String, value: String){

        model.setCache(key, value)

    }

    fun getCache(key: String, defaultValue : String){

        val data = model.getModeCache(key, defaultValue)

        view.cache(data)

    }

    fun delete(key: String){

        model.deleteCache(key)

    }

    fun onDestroy(){

        scopeIO.cancel()
        scopeMain.cancel()

    }

}