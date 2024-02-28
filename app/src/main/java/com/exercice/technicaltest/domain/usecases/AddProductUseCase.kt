package com.exercice.technicaltest.domain.usecases

import com.exercice.technicaltest.domain.repository.ProductRepository

class AddProductUseCase(private val productRepository: ProductRepository) {
    suspend fun AddProduct(image: String) = productRepository.addProduct(image)
}