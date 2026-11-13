package com.mala.digital_joper_mala.Model

import android.content.Context
import com.mala.digital_joper_mala.Database.AchievementDatabase

data class Achievement(
    val id : Int = 0,
    val malaName : String = "",
    val targetAchievementCount : String = "",
    val isCompleted : Boolean = false,
    val userCount : Int = 0,
    val isDialogShowed : Boolean = false
)

class AchievementModel(
    private val context : Context
) {

    private val db = AchievementDatabase(context)

    fun insertUserCount(malaName : String, count : String){

        db.insertAchievementCount(malaName, count)

    }

    fun updateDialogStatus(malaName: String, count: String, isDialogShowed : Boolean): Boolean{

        if (malaName.isEmpty() || count.isEmpty() || !isDialogShowed) return false

        return db.updateDialogShowedStatus(malaName, count, isDialogShowed)

    }

    fun getAchievement(malaName: String) : List<Achievement>{

        if (malaName.isEmpty()) return emptyList()

        return db.getAchievement(malaName)

    }

    fun getAllAchievements() : List<Achievement>{

        return db.getAllAchievement()

    }

    fun getLastCompletedAchievement() : Pair<String, String>{

        val data = db.getLastCompleteAchievement()

        val first = data.first
        val second = data.second

        return Pair(first, second)

    }

}