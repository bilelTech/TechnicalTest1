package com.exercice.technicaltest.ui.main

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.exercice.technicaltest.constants.Constants
import com.exercice.technicaltest.domain.usecases.AddProductUseCase
import com.exercice.technicaltest.domain.usecases.GetProductsUseCase
import com.exercice.technicaltest.models.Product
import com.exercice.technicaltest.utils.getOrAwaitValue
import com.nhaarman.mockitokotlin2.any
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.setMain
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

@ExperimentalCoroutinesApi
@RunWith(JUnit4::class)
class MainViewModelTest {

    private val testDispatcher = TestCoroutineDispatcher()

    lateinit var mainViewModel: MainViewModel

    @Mock
    private lateinit var getProductsUseCase: GetProductsUseCase

    @Mock
    private lateinit var addProductUseCase: AddProductUseCase

    @get:Rule
    val instantTaskExecutionRule: InstantTaskExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
        Dispatchers.setMain(testDispatcher)
        mainViewModel = MainViewModel(getProductsUseCase, addProductUseCase)
    }

    @Test
    fun getProductsSuccess() {
        runBlocking {
            val list = ArrayList<Product>()
            val product = Product(
                1, "title", "description", 10, 1.0f, 1f, 10, "brand", "category", "th"
            )
            list.add(product)
            val flow = flow {
                emit(Result.success(list))
            }
            Mockito.`when`(getProductsUseCase.getProducts()).thenReturn(flow)
            mainViewModel.getProducts()
            val result = mainViewModel.products.getOrAwaitValue()
            assertEquals(
                listOf(
                    product
                ), result
            )
        }
    }

    @Test
    fun getProductsFailed() {
        runBlocking {
            val flow = flow {
                emit(Result.failure<List<Product>>(Throwable("error")))
            }
            Mockito.`when`(getProductsUseCase.getProducts()).thenReturn(flow)
            mainViewModel.getProducts()
            val result = mainViewModel.anError.getOrAwaitValue()
            assertEquals(result, Constants.EMPTY_PRODUCTS_ERROR_MSG)
        }
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
            Mockito.`when`(addProductUseCase.addProduct(any())).thenReturn(flow)
            mainViewModel.addProduct("test")
            val result = mainViewModel.productdetails.getOrAwaitValue()
            assertEquals(result, product)
        }
    }

    @Test
    fun addProductFailedTest() {
        runBlocking {
            val flow = flow {
                emit(Result.failure<Product>(Throwable()))
            }
            Mockito.`when`(addProductUseCase.addProduct(any())).thenReturn(flow)
            mainViewModel.addProduct("test")
            val result = mainViewModel.anError.getOrAwaitValue()
            assertEquals(result, Constants.EMPTY_PRODUCTS_ERROR_MSG)
        }
    }
}