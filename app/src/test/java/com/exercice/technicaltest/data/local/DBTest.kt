package com.exercice.technicaltest.data.local

import androidx.arch.core.executor.testing.CountingTaskExecutorRule
import androidx.room.Room
import org.junit.After
import org.junit.Before
import org.junit.Rule
import java.util.concurrent.TimeUnit
import androidx.test.platform.app.InstrumentationRegistry


abstract class DBTest {

    @Rule
    @JvmField
    val countingTaskExecutorRule = CountingTaskExecutorRule()
    private lateinit var _db: AppDataBase
    val db: AppDataBase
        get() = _db

    @Before
    fun initDb() {
        val context = InstrumentationRegistry.getInstrumentation().targetContext
        _db = Room.inMemoryDatabaseBuilder(context, AppDataBase::class.java).allowMainThreadQueries().build()
    }

    @After
    fun closeDb() {
        countingTaskExecutorRule.drainTasks(10, TimeUnit.SECONDS)
        _db.close()
    }
}