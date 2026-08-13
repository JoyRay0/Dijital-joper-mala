package com.mala.digital_joper_mala.Presenter

import com.mala.digital_joper_mala.Model.BoishnobItem
import com.mala.digital_joper_mala.Model.BoishnobMalaModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel

interface BoishnobMala{

    fun malaList (list: List<BoishnobItem>)

}

class BoishnobMalaPresenter(
    private val view : BoishnobMala
) {

    private val model = BoishnobMalaModel()
    private val scopeIO = CoroutineScope(Dispatchers.IO + SupervisorJob())
    private val scopeMain = CoroutineScope(Dispatchers.Main + SupervisorJob())


    fun getBoishnobMala(){

        view.malaList(model.getBoishnobMala())

    }

    fun onDestroy(){

        scopeIO.cancel()
        scopeMain.cancel()

    }

}