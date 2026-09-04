package com.mala.digital_joper_mala.TestDatabase

import android.content.Context
import androidx.compose.runtime.snapshots.toInt
import com.mala.digital_joper_mala.Database.JopHistoryDatabase
import com.mala.digital_joper_mala.Model.JopHistory
import io.mockk.*
import org.junit.Test
import org.junit.Assert.*

class JopHistoryDB {

    //private val mockContext = mockk<Context>(relaxed = true)

    private val mockDB = mockk<JopHistoryDatabase>(relaxed = true)


    @Test
    fun test_empty_insert(){

        val testDate = ""
        val testDay = ""
        val testCount = 0L
        val testYear = 0

        //mockDB.insertJopCount(testDate, testCount)

        verify(exactly = 0){ mockDB.insertJopCount(testDay, testDate, testYear,testCount) }

    }

    @Test
    fun test_data_insert(){

        val testDay = ""
        val testDate = "20-2-2026"
        val testCount = 10L
        val testYear = 2026

        mockDB.insertJopCount(testDay, testDate, testYear, testCount)

        every { mockDB.getOneJopCount(testDate) } returns testCount


        //verify (exactly = 1){ mockDB.getOneJopCount(testDate) }

    }

    @Test
    fun test_get_all_count(){

        val testDay = ""
        val testDate = "20-2-2026"
        val testCount = 10L
        val testYear = 2026

        mockDB.insertJopCount(testDay, testDate, testYear,testCount)

        val list : MutableList<JopHistory> = mutableListOf()

        list.add(
            JopHistory(
                day = "",
                date = "20-2-2026",
                count = 10L
            )
        )

        every { mockDB.getAllJopCount(1) } returns list
    }

    @Test
    fun test_empty_get_all_count(){

        val list : MutableList<JopHistory> = mutableListOf()

        every { mockDB.getAllJopCount(1) } returns list
    }

    @Test
    fun test_delete_all(){

        val testDay = "Mon"
        val testDate = "20-2-2026"
        val testCount = 10L
        val testYear = 2026
        val newYear = 2027

        mockDB.insertJopCount(testDay, testDate, testYear, testCount)

        verify(exactly = 0){ mockDB.deleteAllJopCount(newYear) }

    }

    @Test
    fun test_delete_2025_history(){

        val year = 2025

        verify(exactly = 0) { mockDB.deleteAllJopCount(year) }

    }

    @Test
    fun test_pagination(){

        val testDay = "Mon"
        val testDate = "20-2-2026"
        val testCount = 10L
        val testYear = 2027

        repeat(100){index ->

            mockDB.insertJopCount(
                day = "$testDay $index",
                date = "$testDate $index",
                year = testYear,
                jopCount = testCount + index
            )

        }


    }

}