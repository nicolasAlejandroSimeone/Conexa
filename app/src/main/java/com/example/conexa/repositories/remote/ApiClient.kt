package com.example.conexa.repositories.remote

import com.example.conexa.models.Cart
import com.example.conexa.models.Product
import kotlinx.coroutines.Deferred
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path


interface ApiClient {

    @GET("/products")
    fun getProductsListAsync(): Deferred<Response<MutableList<Product>>>

    @GET("/products/categories")
    fun getCategoriesListAsync(): Deferred<Response<MutableList<String>>>

    @GET("/products/category/{id}")
    fun getProductFilteredAsync(@Path("id") id:String) : Deferred<Response<MutableList<Product>>>

    @GET("/carts/user/{id}")
    fun getCartsListAsync(@Path("id") id:Int): Deferred<Response<MutableList<Cart>>>

    @POST("/carts")
    fun addToCartAsync(@Body body: Cart): Deferred<Response<Boolean>>
}