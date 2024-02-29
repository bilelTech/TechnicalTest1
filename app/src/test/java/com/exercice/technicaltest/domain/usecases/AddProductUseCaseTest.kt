package com.exercice.technicaltest.domain.usecases

import com.exercice.technicaltest.domain.repository.ProductRepository
import com.exercice.technicaltest.models.Product
import com.nhaarman.mockitokotlin2.any
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

@RunWith(JUnit4::class)
class AddProductUseCaseTest {

    @Mock
    private lateinit var productRepository: ProductRepository
    private lateinit var addProductUseCase: AddProductUseCase

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
        addProductUseCase = AddProductUseCase(productRepository)
    }

    @Test
    fun addProductSuccessTest() {
        runBlocking {
            val product = Product(
                1, "title", "description", 10, 1.0f, 1f, 10, "brand", "category", "th"
            )
            val flow = flow {
                emit(Result.success(product))
            }
            Mockito.`when`(productRepository.addProduct(any())).thenReturn(flow)
            val result = addProductUseCase.addProduct("test")
            result.collect { result: Result<Product> ->
                assert(result.isSuccess)
            }
        }
    }

    @Test
    fun addProductFailedTest() {
        runBlocking {
            val flow = flow {
                emit(Result.failure<Product>(Throwable()))
            }
            Mockito.`when`(productRepository.addProduct(any())).thenReturn(flow)
            val result = addProductUseCase.addProduct("test")
            result.collect { resultat: Result<Product> ->
                assert(resultat.isFailure)
            }
        }
    }


}