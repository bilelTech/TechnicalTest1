package com.exercice.technicaltest.data.repository

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import com.exercice.technicaltest.data.local.dao.ProductDao
import com.exercice.technicaltest.data.remote.RemoteApi
import com.exercice.technicaltest.models.Product
import com.exercice.technicaltest.utils.MainCoroutineScopeRule
import com.nhaarman.mockitokotlin2.any
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.TestCoroutineDispatcher
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

@RunWith(JUnit4::class)
class ProductRepositoryImplTest {

    /**
     * variables
     */
    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val coroutineScope = MainCoroutineScopeRule()

    @Mock
    private lateinit var remoteApi: RemoteApi

    @Mock
    private lateinit var productDao: ProductDao

    private lateinit var productRepositoryImpl: ProductRepositoryImpl

    private var TestCoroutineDispatcher = TestCoroutineDispatcher()

    /**
     * called before start the testing method
     */
    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
        productRepositoryImpl =
            ProductRepositoryImpl(remoteApi, productDao, TestCoroutineDispatcher)
    }

    /**
     * test for add product successfully
     */
    @Test
    fun addProductSuccess() = runBlocking {
        val products = ArrayList<Product>();
        val productsLiveData = MutableLiveData<List<Product>>()
        val product = Product(
            1,
            "title",
            "description",
            10,
            1.0f,
            1f,
            10,
            "brand",
            "category",
            "th"
        )
        products.add(
            product
        )
        Mockito.`when`(productDao.getProducts()).thenReturn(productsLiveData)
        Mockito.`when`(remoteApi.getProductDetails(any())).thenReturn(product)
        val flow = productRepositoryImpl.addProduct("test")
        flow.collect { result: Result<Product> ->
            assert(result.isSuccess)
        }
    }

    /**
     * test for add product failed
     */
    @Test
    fun addProductFailed() = runBlocking {
        val products = ArrayList<Product>();
        val productsLiveData = MutableLiveData<List<Product>>()
        val product = Product(
            1,
            "title",
            "description",
            10,
            1.0f,
            1f,
            10,
            "brand",
            "category",
            "th"
        )
        products.add(
            product
        )
        Mockito.`when`(productDao.getProducts()).thenReturn(productsLiveData)
        Mockito.`when`(remoteApi.getProductDetails(any())).thenReturn(null)
        val flow = productRepositoryImpl.addProduct("test")
        flow.collect { result: Result<Product> ->
            assert(result.isFailure)
        }
    }

    /**
     * test for get product details successfully
     */
    @Test
    fun getProductDetailsSuccess() = runBlocking {
        Mockito.`when`(productDao.getProductById(1)).thenReturn(
            Product(
                1,
                "title",
                "description",
                10,
                1.0f,
                1f,
                10,
                "brand",
                "category",
                "th"
            )
        )
        // WHEN
        val flow = productRepositoryImpl.getProductDetails(1)
        flow.collect { result: Result<Product> ->
            assert(result.isSuccess)
        }
    }


    /**
     * test for get product details failed
     */
    @Test
    fun getProductDetailsFailed() = runBlocking {
        Mockito.`when`(productDao.getProductById(1)).thenReturn(null)
        // WHEN
        val flow = productRepositoryImpl.getProductDetails(1)
        flow.collect { result: Result<Product> ->
            assert(result.isFailure)
        }
    }

    /**
     * test for get products successfully
     */
    @Test
    fun getProductsSuccess() = runBlocking {
        val products = ArrayList<Product>();
        val productsLiveData = MutableLiveData<List<Product>>()
        products.add(
            Product(
                1,
                "title",
                "description",
                10,
                1.0f,
                1f,
                10,
                "brand",
                "category",
                "th"
            )
        )
        productsLiveData.value = products
        Mockito.`when`(productDao.getProducts()).thenReturn(productsLiveData)
        // WHEN
        val flow = productRepositoryImpl.getProducts()
        flow.collect { result: Result<List<Product>> ->
            assert(result.isSuccess)
        }

    }

    /**
     * test for get products failed
     */
    @Test
    fun getProductsFailed() = runBlocking {
        val products = ArrayList<Product>();
        val productsLiveData = MutableLiveData<List<Product>>()
        productsLiveData.value = products
        Mockito.`when`(productDao.getProducts()).thenReturn(productsLiveData)
        // WHEN
        val flow = productRepositoryImpl.getProducts()
        flow.collect { result: Result<List<Product>> ->
            assert(result.isFailure)
        }
    }


}
