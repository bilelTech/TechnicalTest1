package com.exercice.technicaltest.data.remote

import kotlinx.coroutines.runBlocking
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import okio.buffer
import okio.source
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@RunWith(JUnit4::class)
class RemoteApiTest {

    private lateinit var mockWebServer : MockWebServer
    private lateinit var service: RemoteApi

    @Before
    fun createService() {
        mockWebServer = MockWebServer()
        mockWebServer.start()
        service = Retrofit.Builder()
            .baseUrl(mockWebServer.url("/"))
            .addConverterFactory(
                GsonConverterFactory.create()
            )
            .build()
            .create(RemoteApi::class.java)
    }

    @Test
    fun `should return product details on success`() = runBlocking {
        // GIVEN
        enqueueResponse("product-details.json")

        // WHEN
        val productDetails = service.getProductDetails(1)

        // THEN
        Assert.assertEquals(1, productDetails.id)
        Assert.assertEquals("Infinix INBOOK", productDetails.title)
        Assert.assertEquals("Infinix Inbook X1 Ci3 10th 8GB...", productDetails.description)
        Assert.assertEquals(1099, productDetails.price)
        Assert.assertEquals(11.83f, productDetails.discountPercentage)
        Assert.assertEquals(4.54f, productDetails.rating)
        Assert.assertEquals(96, productDetails.stock)
        Assert.assertEquals("Infinix", productDetails.brand)
        Assert.assertEquals("laptops", productDetails.category)
        Assert.assertEquals("https://cdn.dummyjson.com/product-images/9/thumbnail.jpg", productDetails.thumbnail)
    }

    private fun enqueueResponse(fileName: String, headers: Map<String, String> = emptyMap()) {
        val inputStream = javaClass.classLoader!!
            .getResourceAsStream("api-response/$fileName")
        val source = inputStream.source().buffer()

        val mockResponse = MockResponse()

        for ((key, value) in headers) {
            mockResponse.addHeader(key, value)
        }

        mockWebServer.enqueue(
            mockResponse
                .setBody(source.readString(Charsets.UTF_8))
        )
    }

    @After
    fun stopService() {
        mockWebServer.shutdown()
    }
}