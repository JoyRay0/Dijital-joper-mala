package com.mala.digital_joper_mala

import android.content.Context
import com.mala.digital_joper_mala.Database.JopaChartDB
import org.junit.*

import org.junit.Assert.*
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class SqliteTest{


    @Mock
    private lateinit var context : Context

    private lateinit var jopachartDB : JopaChartDB

    private val monthList = arrayOf("জানুয়ারি", "ফেব্রুয়ারি", "মার্চ", "এপ্রিল", "মে", "জুন", "জুলাই", "আগস্ট", "সেপ্টেম্বর", "অক্টোবর", "নভেম্বর", "ডিসেম্বর")

    @Before
    fun setDB(){

        jopachartDB = JopaChartDB(context)

    }

    @Test
    fun insertData(){

        jopachartDB.insert("2026", monthList[0], 10)

        assertTrue(true)

    }

}