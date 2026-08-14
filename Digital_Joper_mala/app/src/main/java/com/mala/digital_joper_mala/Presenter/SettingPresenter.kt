package com.mala.digital_joper_mala.Presenter

import android.content.Context
import com.mala.digital_joper_mala.Model.SettingModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel

interface Setting{

    fun themeCache(value : String)
    fun vibrationCache(value: String)

}

class SettingPresenter(
    private val view : Setting,
    private val context: Context
) {

    private val model = SettingModel(context)

    private val scopeIO = CoroutineScope(Dispatchers.IO + SupervisorJob())
    private val scopeMain = CoroutineScope(Dispatchers.Main + SupervisorJob())

    fun setThemeCache(key : String, value: String){

        model.setCache(key, value)

    }

    fun getThemeCache(key: String){

        val data = model.getModeCache(key, "0")

        view.themeCache(data)

    }

    fun setVibrationCache(key: String, value: String){

        model.setCache(key, value)

        view.vibrationCache(model.getModeCache(key, "true"))

    }

    fun getVibrationCache(key: String){

        view.vibrationCache(model.getModeCache(key, "true"))

    }

    fun onDestroy(){

        scopeIO.cancel()
        scopeMain.cancel()

    }

}