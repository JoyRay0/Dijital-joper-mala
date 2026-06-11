package com.mala.digital_joper_mala.Model

import com.mala.digital_joper_mala.Database.UserMantraDatabase

data class UserMantra(
    val id : Int = 0,
    val title : String = "",
    val mantra : String = ""
)

class UserMantraModel(
    private val db : UserMantraDatabase
) {

    fun insert(title: String, mantra : String){

        println("INSERT CALLED")

        db.insert(title, mantra)

    }

    fun getMantra() : List<UserMantra>{

        println("GET MANTRA CALLED")

        val data = db.getAllMantra()

        return data

    }

    fun delete(mantra: String) : Boolean{

        return db.deleteOne(mantra)

    }


}