package com.mala.digital_joper_mala.Presenter

import android.content.Context
import com.mala.digital_joper_mala.Model.Achievement
import com.mala.digital_joper_mala.Model.AchievementModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

interface Achievements{
    fun achievementCountList(list: List<Achievement>){}
    fun achievementList(list: List<Achievement>){}
    fun achievementStatus(status : String){}
    fun achievementListUpdated(isUpdated : Boolean){}
}

enum class AchievementStatus(val value : String){
    Pending("achievement_pending"),
    Success("achievement_success"),
    Failed("achievement_failed")
}

class AchievementPresenter(
    private val view : Achievements,
    private val context : Context
) {

    private val model = AchievementModel(context)

    private val scopeIO = CoroutineScope(Dispatchers.IO + SupervisorJob())
    private val scopeMain = CoroutineScope(Dispatchers.Main + SupervisorJob())

    fun updateAchievement(malaName : String, count : String){

        scopeIO.launch {

            val isUpdated = model.updateAchievement(malaName, count)

            val updateList = model.getAchievement(malaName)

            withContext(Dispatchers.Main){

                view.achievementCountList(updateList)
                view.achievementListUpdated(isUpdated)

            }

        }

    }

    fun getAchievement(malaName: String){

        scopeIO.launch {

            val list = model.getAchievement(malaName)

            withContext(Dispatchers.Main){

                view.achievementCountList(list)

            }

        }

    }

    fun getAllAchievements(){

        view.achievementStatus(AchievementStatus.Pending.value)

        scopeIO.launch {

            val data = model.getAllAchievements()

            withContext(Dispatchers.Main){

                if (data.isNotEmpty()){

                    view.achievementStatus(AchievementStatus.Success.value)
                    view.achievementList(data)

                }else{

                    view.achievementStatus(AchievementStatus.Failed.value)

                }

            }

        }

    }


    fun onDestroy(){

        scopeIO.cancel()
        scopeMain.cancel()

    }

}