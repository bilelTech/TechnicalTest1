package com.exercice.technicaltest.domain.usecases

import com.exercice.technicaltest.domain.repository.ProductRepository
import com.exercice.technicaltest.models.Product
import com.nhaarman.mockitokotlin2.any
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

class GetProductDetailsUseCaseTest {

    @Mock
    private lateinit var productRepository: ProductRepository
    private lateinit var getProductDetailsUseCase: GetProductDetailsUseCase

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
        getProductDetailsUseCase = GetProductDetailsUseCase(productRepository)
    }

    @Test
    fun getProductDetailsSuccessTest() {
        runBlocking {
            val product = Product(
                1, "title", "description", 10, 1.0f, 1f, 10, "brand", "category", "th"
            )
            val flow = flow {
                emit(Result.success(product))
            }
            Mockito.`when`(productRepository.getProductDetails(any())).thenReturn(flow)
            val result = getProductDetailsUseCase.getProductDetails(1)
            result.collect { result: Result<Product> ->
                assert(result.isSuccess)
            }
        }
    }

    @Test
    fun getProductDetailsFailedTest() {
        runBlocking {
            val flow = flow {
                emit(Result.failure<Product>(Throwable()))
            }
            Mockito.`when`(productRepository.getProductDetails(any())).thenReturn(flow)
            val result = getProductDetailsUseCase.getProductDetails(1)
            result.collect { resultat: Result<Product> ->
                assert(resultat.isFailure)
            }
        }
    }
}