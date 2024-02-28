package com.exercice.technicaltest.domain.usecases

import com.exercice.technicaltest.domain.repository.ProductRepository

class GetProductDetailsUseCase(private val productRepository: ProductRepository) {
    suspend fun getProductDetails(productId: Int) = productRepository.getProductDetails(productId)
}