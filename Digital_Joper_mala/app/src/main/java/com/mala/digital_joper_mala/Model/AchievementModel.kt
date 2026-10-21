package com.mala.digital_joper_mala.Model

import android.content.Context
import com.mala.digital_joper_mala.Database.AchievementDatabase

data class Achievement(
    val id : Int = 0,
    val malaName : String = "",
    val targetAchievementCount : String = "",
    val isCompleted : Boolean = false,
    val userCount : Int = 0
)

class AchievementModel(
    private val context : Context
) {

    private val db = AchievementDatabase(context)

    fun insertUserCount(malaName : String, count : String){

        db.insertAchievementCount(malaName, count)

    }

    fun getAchievement(malaName: String) : List<Achievement>{

        if (malaName.isEmpty()) return emptyList()

        return db.getAchievement(malaName)

    }

    fun getAllAchievements() : List<Achievement>{

        return db.getAllAchievement()

    }

}