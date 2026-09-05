package com.mala.digital_joper_mala.Model

import android.content.Context
import com.mala.digital_joper_mala.Database.UserMantraDatabase

data class UserMantra(
    val id : Int = 0,
    val title : String = "",
    val mantra : String = ""
)

class UserMantraModel(
    private val context: Context
) {

    private val db = UserMantraDatabase(context)


    fun insert(title: String, mantra : String){

        db.insert(title, mantra)

    }

    fun getMantra(page : Int) : List<UserMantra>{

        val data = db.getAllMantra(page)

        return data

    }

    fun delete(mantra: String) : Boolean{

        return db.deleteOne(mantra)

    }

}