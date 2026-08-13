package com.mala.digital_joper_mala.Presenter

import com.mala.digital_joper_mala.Model.BoishnobItem
import com.mala.digital_joper_mala.Model.ShivItem
import com.mala.digital_joper_mala.Model.ShivMalaModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel

interface ShivMala{

    fun malaList (list: List<ShivItem>)

}

class ShivMalaPresenter(
    private val view : ShivMala
) {

    private val model = ShivMalaModel()
    private val scopeIO = CoroutineScope(Dispatchers.IO + SupervisorJob())
    private val scopeMain = CoroutineScope(Dispatchers.Main + SupervisorJob())


    fun getShivMala(){

        view.malaList(model.getShivMala())

    }

    fun onDestroy(){

        scopeIO.cancel()
        scopeMain.cancel()

    }

}