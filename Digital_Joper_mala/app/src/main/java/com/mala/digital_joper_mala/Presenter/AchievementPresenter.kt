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

    fun achievementList(list: List<Achievement>)

}

class AchievementPresenter(
    private val view : Achievements,
    private val context : Context
) {

    private val model = AchievementModel(context)

    private val scopeIO = CoroutineScope(Dispatchers.IO + SupervisorJob())
    private val scopeMain = CoroutineScope(Dispatchers.Main + SupervisorJob())

    fun insertAchievement(malaName : String, count : String){

        scopeIO.launch {

            model.insertAchievement(malaName, count)

            val updateList = model.getAchievement(malaName)

            withContext(Dispatchers.Main){

                view.achievementList(updateList)

            }

        }

    }

    fun getAchievement(malaName: String){

        scopeIO.launch {

            val list = model.getAchievement(malaName)

            withContext(Dispatchers.Main){

                view.achievementList(list)

            }

        }

    }


    fun onDestroy(){

        scopeIO.cancel()
        scopeMain.cancel()

    }

}