package com.mala.digital_joper_mala.Model

import android.content.Context
import com.mala.digital_joper_mala.Database.NotificationDatabase

data class NotificationItem(
    val id : Long = 0L,
    val title : String = "",
    val description : String = "",
    val isShowed : Boolean = false
)

class NotificationModel(
    private val context : Context
) {

    private val db = NotificationDatabase(context)

    fun insertNotification(title: String, description: String){

        if (title.isEmpty() || description.isEmpty()) return

        db.insertNotification(title, description)

    }

    fun getAllNotification(page : Int) : List<NotificationItem>{

        if (page < 0) return emptyList()

        return db.getAllNotification(page)

    }

    fun deleteAllNotification() : Boolean{

        return db.deleteAllNotification()

    }

    fun hasSeenNotification(title: String) : Boolean{

        if (title.isEmpty()) return false

        return db.hasSeenNotification(title)

    }

}