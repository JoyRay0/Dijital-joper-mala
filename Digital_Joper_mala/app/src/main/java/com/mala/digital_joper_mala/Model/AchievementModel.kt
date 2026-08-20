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

    fun insertAchievement(malaName : String, count : String, isInserted : (Boolean) -> Unit){

        val countList = listOf("1000", "5000", "10000", "50000", "100000", "500000")

        if (malaName.isEmpty() || count.isEmpty()) return

        if (count !in countList){
            isInserted(false)
            return
        }

        db.achievementInsert(malaName, count, isInserted = {isInserted(it)})

    }

    fun getAchievement(malaName: String) : List<Achievement>{

        if (malaName.isEmpty()) return emptyList()

        return db.getAchievement(malaName)

    }

}