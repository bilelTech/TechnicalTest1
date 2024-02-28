package com.exercice.technicaltest.domain.usecases

import com.exercice.technicaltest.domain.repository.ProductRepository

class GetProductsUseCase (private val productRepository: ProductRepository) {
    suspend fun getProducts() = productRepository.getProducts()
}