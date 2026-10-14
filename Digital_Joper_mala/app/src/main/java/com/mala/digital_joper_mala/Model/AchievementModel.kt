package com.mala.digital_joper_mala.Model

import android.content.Context
import com.mala.digital_joper_mala.Database.AchievementDatabase

data class Achievement(
    val id : Int = 0,
    val malaName : String = "",
    val achievementCount : String = "",
    val isCompleted : Boolean = false
)

class AchievementModel(
    private val context : Context
) {

    private val db = AchievementDatabase(context)

    fun updateAchievement(malaName : String, count : String) : Boolean{

        val countList = listOf("1000", "5000", "10000", "50000", "100000", "500000")

        if (malaName.isEmpty() || count.isEmpty()) return false

        if (count !in countList){
            return false
        }

        return db.updateAchievement(malaName, count)

    }

    fun getAchievement(malaName: String) : List<Achievement>{

        if (malaName.isEmpty()) return emptyList()

        return db.getAchievement(malaName)

    }

    fun getAllAchievements() : List<Achievement>{

        return db.getAllAchievement()

    }

}