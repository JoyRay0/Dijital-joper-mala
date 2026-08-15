package com.mala.digital_joper_mala.Model

import android.content.Context
import com.mala.digital_joper_mala.Database.AchievementDatabase

data class Achievement(
    val id : Int = 0,
    val malaName : String = "",
    val achievementCount : String = ""
)

class AchievementModel(
    private val context : Context
) {

    private val db = AchievementDatabase(context)

    fun insertAchievement(malaName : String, count : String){

        if (malaName.isEmpty() || count.isEmpty()) return

        db.achievementInsert(malaName, count)

    }

    fun getAchievement(malaName: String) : List<Achievement>{

        if (malaName.isEmpty()) return emptyList()

        return db.getAchievement(malaName)

    }

}