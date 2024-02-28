package com.exercice.technicaltest.di

import com.exercice.technicaltest.domain.repository.ProductRepository
import com.exercice.technicaltest.domain.usecases.GetProductDetailsUseCase
import com.exercice.technicaltest.domain.usecases.GetProductsUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object UseCaseModule {
    @Singleton
    @Provides
    fun provideGetProductDetailsUseCase(
        productRepository: ProductRepository
    ): GetProductDetailsUseCase {
        return GetProductDetailsUseCase(productRepository)
    }

    @Singleton
    @Provides
    fun provideGetProductsUseCase(
        productRepository: ProductRepository
    ): GetProductsUseCase {
        return GetProductsUseCase(productRepository)
    }
}