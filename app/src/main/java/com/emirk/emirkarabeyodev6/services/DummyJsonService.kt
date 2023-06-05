package com.emirk.emirkarabeyodev6.services

import com.emirk.emirkarabeyodev6.model.JWTUser
import com.emirk.emirkarabeyodev6.model.Product
import com.emirk.emirkarabeyodev6.model.Products
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface DummyJsonService {

    @POST("/auth/login")
    fun login(
        @Body JWTUser: JWTUser
    ): Call<JWTUser>

    @GET("/products")
    fun getProducts(
        @Query("limit") limit: Int,
    ): Call<Products>

    @GET("products/search")
    fun searchProducts(@Query("q") query: String): Call<Products>
}