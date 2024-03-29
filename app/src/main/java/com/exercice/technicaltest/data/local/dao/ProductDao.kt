package com.exercice.technicaltest.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.exercice.technicaltest.models.Product

@Dao
interface ProductDao {

    // insert product on database
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(data: Product)

    // insert list of product on database
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(data: List<Product>)

    // get list of products from database
    @Query("SELECT * FROM products")
    fun getProducts(): List<Product>


    @Query("SELECT * FROM products WHERE id=:id")
    fun getProductById(id: Int): Product?

    @Query("DELETE FROM products")
    fun clear()
}