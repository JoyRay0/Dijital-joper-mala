package com.mala.digital_joper_mala.TestDatabase

import com.mala.digital_joper_mala.Database.AchievementDatabase
import com.mala.digital_joper_mala.Model.Achievement
import io.mockk.*
import org.junit.Test

class AchievementDB {

    val db = mockk<AchievementDatabase>(relaxed = true)

    @Test
    fun test_insert_user_count_easy_mala(){

        val insert = "10"
        val mala_name = "easy_mala"

        verify(exactly = 0) { db.insertAchievementCount(mala_name, insert) }

    }

    @Test
    fun test_insert_user_count_boishnon_mala(){

        val insert = "20"
        val mala_name = "boishnob_mala"

        verify(exactly = 0) { db.insertAchievementCount(mala_name, insert) }

    }

    @Test
    fun test_insert_user_count_shiv_mala(){

        val insert = "30"
        val mala_name = "shiv_mala"

        verify(exactly = 0) { db.insertAchievementCount(mala_name, insert) }

    }

    @Test
    fun test_update_achievement_easy_mala_return_true(){

        val mala_name = "easy_mala"
        val achievement_count = "1000"

        //every { db.updateAchievement(mala_name, achievement_count) } returns true

    }

    @Test
    fun test_update_achievement_boishnob_mala_return_true(){

        val mala_name = "boishnob_mala"
        val achievement_count = "5000"

       // every { db.updateAchievement(mala_name, achievement_count) } returns true

    }

    @Test
    fun test_update_achievement_shiv_mala_return_true(){

        val mala_name = "shiv_mala"
        val achievement_count = "1000"

        //every { db.updateAchievement(mala_name, achievement_count) } returns true

    }

    @Test
    fun test_update_achievement_easy_mala_return_false(){

        val mala_name = ""
        val achievement_count = "1000"

        //every { db.updateAchievement(mala_name, achievement_count) } returns false

    }

    @Test
    fun test_update_achievement_boishnob_mala_return_false(){

        val mala_name = ""
        val achievement_count = "5000"

        //every { db.updateAchievement(mala_name, achievement_count) } returns false

    }

    @Test
    fun test_update_achievement_shiv_mala_return_false(){

        val mala_name = ""
        val achievement_count = "1000"

        //every { db.updateAchievement(mala_name, achievement_count) } returns false

    }

    @Test
    fun test_get_achievement_easy_mala(){

        val mala_name = "easy_mala"

        val list = mutableListOf<Achievement>()

        list.add(Achievement(
            targetAchievementCount = "1000",
            isCompleted = true
        ))

        every { db.getAchievement(mala_name) } returns list

    }

    @Test
    fun test_get_achievement_boishnob_mala(){

        val mala_name = "boishnob_mala"

        val list = mutableListOf<Achievement>()

        list.add(Achievement(
            targetAchievementCount = "5000",
            isCompleted = true
        ))

        every { db.getAchievement(mala_name) } returns list

    }

    @Test
    fun test_get_achievement_shiv_mala(){

        val mala_name = "shiv_mala"

        val list = mutableListOf<Achievement>()

        list.add(Achievement(
            targetAchievementCount = "10000",
            isCompleted = true
        ))

        every { db.getAchievement(mala_name) } returns list

    }

}