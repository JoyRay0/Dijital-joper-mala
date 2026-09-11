package com.mala.digital_joper_mala.TestDatabase

import com.mala.digital_joper_mala.Database.NotificationDatabase
import com.mala.digital_joper_mala.Model.NotificationItem
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.Test

class NotificationDB {

    private val mockDB = mockk<NotificationDatabase>(relaxed = true)

    @Test
    fun test_empty_insert(){

        val title = ""
        val description = ""

        verify (exactly = 0){ mockDB.insertNotification(title, description) }

    }

    @Test
    fun test_insert_with_data(){

        val title = "TTT"
        val description = "DDDD"

        verify (exactly = 0){ mockDB.insertNotification(title, description) }
    }

    @Test
    fun test_empty_page_getAllNotification(){

        val page = -1

        every { mockDB.getAllNotification(page) } returns emptyList()

    }

    @Test
    fun test_getAllNotification_with_page_is_showed_false(){

        val page = 1
        val title = "TTT"
        val description = "DDD"
        val list = mutableListOf<NotificationItem>()

        list.add(NotificationItem(
            id = 1L,
            title = title,
            description = description,
            isShowed = false
        ))

        mockDB.insertNotification(title, description)

        every { mockDB.getAllNotification(page) } returns list

    }

    @Test
    fun test_getAllNotification_with_page_is_showed_true(){

        val page = 1
        val title = "TTT"
        val description = "DDD"
        val list = mutableListOf<NotificationItem>()

        list.add(NotificationItem(
            id = 1L,
            title = title,
            description = description,
            isShowed = true
        ))

        mockDB.insertNotification(title, description)

        mockDB.hasSeenNotification(title)

        every { mockDB.getAllNotification(page) } returns list

    }

    @Test
    fun test_deleteAllNotification_with_data(){

        val title = "TTT"
        val description = "DDD"

        mockDB.insertNotification(title, description)

        every { mockDB.deleteAllNotification() } returns true
    }

    @Test
    fun test_deleteAllNotification_empty_table(){

        every { mockDB.deleteAllNotification() } returns false

    }

    @Test
    fun test_hasSeenNotification_empty_data(){

        val title = ""

        every { mockDB.hasSeenNotification(title) } returns false

    }

    @Test
    fun test_hasSeenNotification_with_data(){

        val title = "TTT"
        val description = "DDD"

        mockDB.insertNotification(title, description)

        every { mockDB.hasSeenNotification(title) } returns true

    }

    @Test
    fun test_notification_count_zero(){

        every { mockDB.unseenNotificationCount() } returns 0

    }

    @Test
    fun test_notification_count(){

        val title = "TTT"
        val description = "DDD"

        mockDB.insertNotification(title, description)

        every { mockDB.unseenNotificationCount() } returns 1

    }

}