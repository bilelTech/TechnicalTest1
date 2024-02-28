package com.exercice.technicaltest.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.exercice.technicaltest.data.local.dao.ProductDao
import com.exercice.technicaltest.models.Product

@Database(version = 1, entities = [Product::class])
abstract class AppDataBase : RoomDatabase() {

    abstract fun productDao(): ProductDao

    companion object{
        val DATABASE_NAME: String = "app_db"
    }
}