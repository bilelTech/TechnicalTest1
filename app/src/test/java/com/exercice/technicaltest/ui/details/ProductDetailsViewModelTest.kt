package com.exercice.technicaltest.ui.details

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.exercice.technicaltest.constants.Constants
import com.exercice.technicaltest.domain.usecases.GetProductDetailsUseCase
import com.exercice.technicaltest.models.Product
import com.exercice.technicaltest.ui.main.details.ProductDetailsViewModel
import com.exercice.technicaltest.utils.getOrAwaitValue
import com.nhaarman.mockitokotlin2.any
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.setMain
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

class ProductDetailsViewModelTest {

    @Mock
    lateinit var getProductDetailsUseCase: GetProductDetailsUseCase

    lateinit var productDetailsViewModel: ProductDetailsViewModel

    private val testDispatcher = TestCoroutineDispatcher()

    @get:Rule
    val instantTaskExecutionRule: InstantTaskExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
        Dispatchers.setMain(testDispatcher)
        productDetailsViewModel = ProductDetailsViewModel(getProductDetailsUseCase)
    }

    @Test
    fun getProductDetailsSuccess() {
        runBlocking {
            val product = Product(
                1, "title", "description", 10, 1.0f, 1f, 10, "brand", "category", "th"
            )
            val flow = flow {
                emit(Result.success(product))
            }
            Mockito.`when`(getProductDetailsUseCase.getProductDetails(any())).thenReturn(flow)
            productDetailsViewModel.getProductDetails(1)
            val result = productDetailsViewModel.productdetails.getOrAwaitValue()
            Assert.assertEquals(result, product)
        }
    }

    @Test
    fun addProductFailedTest() {
        runBlocking {
            val flow = flow {
                emit(Result.failure<Product>(Throwable()))
            }
            Mockito.`when`(getProductDetailsUseCase.getProductDetails(any())).thenReturn(flow)
            productDetailsViewModel.getProductDetails(1)
            val result = productDetailsViewModel.anError.getOrAwaitValue()
            Assert.assertEquals(result, Constants.EMPTY_PRODUCTS_ERROR_MSG)
        }
    }


}