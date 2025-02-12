package com.example.retrofitnagibation.network

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query
import com.example.retrofitnagibation.model.Login
import com.example.retrofitnagibation.model.Product
import uz.itteacher.myapi.model.Products
import com.example.retrofitnagibation.model.User

interface ApiService{
    //      https://dummyjson.com/products
    @GET("/products")
    fun getProducts(): Call<Products>

    //    https://dummyjson.com/products/6
    @GET("/products/{id}")
    fun getProductById(@Path("id") id: Int): Call<Product>

    //    https://dummyjson.com/products/search?q=phone
    @GET("/products/search")
    fun searchProducts(@Query("q") q: String): Call<Products>


    //    https://dummyjson.com/auth/login
    @POST("/auth/login")
    fun login(@Body login: Login): Call<User>
}