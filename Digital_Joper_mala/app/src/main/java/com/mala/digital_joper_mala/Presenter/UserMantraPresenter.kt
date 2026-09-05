package com.mala.digital_joper_mala.Presenter

import android.content.Context
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
    fun mantraStatus(status : String)
    fun loading(isLoading : Boolean)

}

enum class UserAddMantra(val value : String){

    UserMantraPending("user_mantra_pending"),
    UserMantraSuccess("user_mantra_success"),
    UserMantraFailed("user_mantra_failed")

}

class UserMantraPresenter(
    private val context : Context,
    private val view : UserMantras
) {

    private val model = UserMantraModel(context)

    private val scopeIO = CoroutineScope(Dispatchers.IO + SupervisorJob())
    private val scopeMain = CoroutineScope(Dispatchers.Main + SupervisorJob())
    private var currentPage = 1
    private var isLoading = false
    private var isLastPage = false
    private val userMantraList = mutableListOf<UserMantra>()


    fun insert(title : String, mantra : String){

        scopeIO.launch {

            model.insert(title = title, mantra = mantra)

            currentPage = 1
            isLastPage = false
            userMantraList.clear()

            getAllMantras()

        }

    }

    fun getAllMantras(){

        scopeIO.launch {

            if (isLoading || isLastPage) return@launch

            withContext(Dispatchers.Main){

                isLoading = true
                view.loading(true)

                if (currentPage == 1) view.mantraStatus(UserAddMantra.UserMantraPending.value)

            }

            val newData = model.getMantra(currentPage)

            withContext(Dispatchers.Main){

                if (newData.isEmpty()){

                    isLastPage = true
                    isLoading = false
                    view.loading(false)

                }else{

                    userMantraList.addAll(newData)

                    if (currentPage == 1) view.mantraStatus(UserAddMantra.UserMantraSuccess.value)

                    view.getMantra(userMantraList.toList())

                    isLastPage = false
                    currentPage++

                }

                isLoading = false
                view.loading(false)

                if (userMantraList.isEmpty()) view.mantraStatus(UserAddMantra.UserMantraFailed.value)

            }

        }

    }

    fun deleteOne(mantra: String){

        scopeIO.launch {

            val isDeleted =  model.delete(mantra)

            withContext(Dispatchers.Main){

                if (isDeleted){

                    val index = userMantraList.indexOfFirst { it.mantra == mantra }

                    if (index != -1) userMantraList.removeAt(index)

                    view.status("ডিলিট হয়েছে")
                    view.getMantra(userMantraList.toList())

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