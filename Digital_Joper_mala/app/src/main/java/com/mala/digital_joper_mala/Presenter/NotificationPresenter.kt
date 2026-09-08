package com.mala.digital_joper_mala.Presenter

import android.content.Context
import com.mala.digital_joper_mala.Model.NotificationItem
import com.mala.digital_joper_mala.Model.NotificationModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

interface Notification{

    fun notificationList(list : List<NotificationItem>)
    fun notificationStatus(status : String)
    fun notificationLoading(isLoading : Boolean)

}

enum class NotificationStatus(val value : String){

    Pending("notification_pending"),
    Success("notification_success"),
    Failed("notification_failed")

}

class NotificationPresenter(
    private val context: Context,
    private val view : Notification
) {

    private val model = NotificationModel(context)

    private val scopeIO = CoroutineScope(Dispatchers.IO + SupervisorJob())
    private val scopeMain = CoroutineScope(Dispatchers.Main + SupervisorJob())
    private var currentPage = 1
    private var isLoading = false
    private var isLastPage = false
    private val notificationList = mutableListOf<NotificationItem>()

    fun insertNotification(title : String, description : String){

        scopeIO.launch {

            model.insertNotification(title, description)

            currentPage = 1
            isLastPage = false
            notificationList.clear()

            getAllNotification()

        }

    }

    fun getAllNotification(){

        scopeIO.launch {

            if (isLoading || isLastPage) return@launch

            withContext(Dispatchers.Main){

                isLoading = true
                view.notificationLoading(true)

                if (currentPage == 1) view.notificationStatus(NotificationStatus.Pending.value)

            }

            val newData = model.getAllNotification(currentPage)

            withContext(Dispatchers.Main){

                if (newData.isEmpty()){

                    isLastPage = true

                }else{

                    notificationList.addAll(newData)

                    view.notificationList(notificationList.toList())

                    if (currentPage == 1) view.notificationStatus(NotificationStatus.Success.value)

                    isLastPage = false
                    currentPage++

                }

                isLoading = false
                view.notificationLoading(false)

                if (notificationList.isEmpty()) view.notificationStatus(NotificationStatus.Failed.value)

            }

        }

    }

    fun deleteAllNotification(){

        scopeIO.launch {

            val isDeleted = model.deleteAllNotification()

            withContext(Dispatchers.Main){

                if (isDeleted){

                    notificationList.clear()
                    view.notificationList(notificationList.toList())

                }

            }

        }

    }

    fun onDestroy(){

        scopeIO.cancel()
        scopeMain.cancel()

    }
}