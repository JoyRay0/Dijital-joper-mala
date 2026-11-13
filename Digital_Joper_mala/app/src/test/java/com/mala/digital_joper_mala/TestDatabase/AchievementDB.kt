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

    @Test
    fun test_empty_mala_name_update_dialog_status(){

        val malaName = ""
        val count = "1000"
        val showed = true

        every { db.updateDialogShowedStatus(malaName, count, showed) } returns false

    }

    @Test
    fun test_empty_count_update_dialog_status(){

        val malaName = "easy_mala"
        val count = ""
        val showed = true

        every { db.updateDialogShowedStatus(malaName, count, showed) } returns false

    }

    @Test
    fun test_showed_false_update_dialog_status(){

        val malaName = "easy_mala"
        val count = "1000"
        val showed = false

        every { db.updateDialogShowedStatus(malaName, count, showed) } returns false

    }

    @Test
    fun test_update_dialog_status(){

        val malaName = "easy_mala"
        val count = "1000"
        val showed = true

        every { db.updateDialogShowedStatus(malaName, count, showed) } returns true

    }

}