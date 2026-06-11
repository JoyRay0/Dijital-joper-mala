package com.mala.digital_joper_mala.Presenter

import android.util.Log
import com.mala.digital_joper_mala.Database.UserMantraDatabase
import com.mala.digital_joper_mala.Model.UserMantra
import com.mala.digital_joper_mala.Model.UserMantraModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


interface UserMantras{

    fun getMantra(list: List<UserMantra>)
    fun status(message : String)

}

class UserMantraPresenter(
    private val db : UserMantraDatabase,
    private val view : UserMantras
) {

    private val model = UserMantraModel(db)

    private val scopeIO = CoroutineScope(Dispatchers.IO + SupervisorJob())
    private val scopeMain = CoroutineScope(Dispatchers.Main + SupervisorJob())

    fun insert(title : String, mantra : String){

        scopeIO.launch {

            model.insert(title = title, mantra = mantra)

            val data = model.getMantra()

            withContext(Dispatchers.Main){

                view.getMantra(data)

            }

        }

    }

    fun getAllMantras(){

        scopeIO.launch {

            val data = model.getMantra()

            withContext(Dispatchers.Main){

                view.getMantra(data)

            }

        }

    }

    fun deleteOne(mantra: String){

        scopeIO.launch {

            val isDeleted =  model.delete(mantra)
            val data = model.getMantra()

            withContext(Dispatchers.Main){

                if (isDeleted){

                    view.status("ডিলিট হয়েছে")
                    view.getMantra(data)

                }else{

                    view.status("ডিলিট হয়নি")

                }

            }

        }

    }

    fun onDestroy(){
        scopeIO.cancel()
        scopeMain.cancel()
    }

}