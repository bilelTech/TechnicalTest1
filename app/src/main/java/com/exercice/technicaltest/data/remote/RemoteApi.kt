package com.exercice.technicaltest.data.remote

import com.exercice.technicaltest.models.Product
import retrofit2.http.GET
import retrofit2.http.Path

interface RemoteApi {

    @GET("products/{productId}")
    suspend fun getProductDetails(@Path("productId") productId: Int): Product
}