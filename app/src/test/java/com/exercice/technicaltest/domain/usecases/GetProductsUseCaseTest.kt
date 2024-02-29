package com.exercice.technicaltest.domain.usecases

import com.exercice.technicaltest.domain.repository.ProductRepository
import com.exercice.technicaltest.models.Product
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

class GetProductsUseCaseTest {

    @Mock
    private lateinit var productRepository: ProductRepository
    private lateinit var getProductsUseCase: GetProductsUseCase

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
        getProductsUseCase = GetProductsUseCase(productRepository)
    }

    @Test
    fun getProductDetailsSuccessTest() {
        runBlocking {
            val list = ArrayList<Product>()
            val product = Product(
                1, "title", "description", 10, 1.0f, 1f, 10, "brand", "category", "th"
            )
            list.add(product)
            val flow = flow {
                emit(Result.success(list))
            }
            Mockito.`when`(productRepository.getProducts()).thenReturn(flow)
            val result = getProductsUseCase.getProducts()
            result.collect { result: Result<List<Product>> ->
                assert(result.isSuccess)
            }
        }
    }

    @Test
    fun getProductDetailsFailedTest() {
        runBlocking {
            val flow = flow {
                emit(Result.failure<List<Product>>(Throwable()))
            }
            Mockito.`when`(productRepository.getProducts()).thenReturn(flow)
            val result = getProductsUseCase.getProducts()
            result.collect { result: Result<List<Product>> ->
                assert(result.isFailure)
            }
        }
    }
}