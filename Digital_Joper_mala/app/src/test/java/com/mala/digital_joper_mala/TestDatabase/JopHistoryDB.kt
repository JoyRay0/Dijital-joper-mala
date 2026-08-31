package com.mala.digital_joper_mala.TestDatabase

import android.content.Context
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
        val testCount = 0L

        //mockDB.insertJopCount(testDate, testCount)

        verify(exactly = 0){ mockDB.insertJopCount(testDate, testCount) }

    }

    @Test
    fun test_data_insert(){

        val testDate = "20-2-2026"
        val testCount = 10L

        mockDB.insertJopCount(testDate, testCount)

        every { mockDB.getOneJopCount(testDate) } returns testCount


        //verify (exactly = 1){ mockDB.getOneJopCount(testDate) }

    }

    @Test
    fun test_get_all_count(){

        val testDate = "20-2-2026"
        val testCount = 10L

        mockDB.insertJopCount(testDate, testCount)

        val list : MutableList<JopHistory> = mutableListOf()

        list.add(
            JopHistory(
                dayDate = "20-2-2026",
                count = 10L
            )
        )

        every { mockDB.getAllJopCount() } returns list
    }

    @Test
    fun test_empty_get_all_count(){

        val list : MutableList<JopHistory> = mutableListOf()

        every { mockDB.getAllJopCount() } returns list
    }

    @Test
    fun test_delete_all(){

        val testDate = "20-2-2026"
        val testCount = 10L

        mockDB.insertJopCount(testDate, testCount)

        verify(exactly = 0) { mockDB.deleteAllJopCount() }

    }


}