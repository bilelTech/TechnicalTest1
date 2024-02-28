package com.exercice.technicaltest.domain.repository

import com.exercice.technicaltest.models.Product
import kotlinx.coroutines.flow.Flow

interface ProductRepository {

    suspend fun addProduct(image: String): Flow<Result<Product>>
    suspend fun getProductDetails(productId: Int): Flow<Result<Product>>
    fun getProducts(): Flow<Result<List<Product>>>
}