package com.example.conexa.repositories.services

import com.example.conexa.models.Cart
import com.example.conexa.models.Product
import com.example.conexa.repositories.remote.ApiClient
import com.example.conexa.utils.Result

class ProductService(private val api:ApiClient) {
    suspend fun getProducts(): Result<MutableList<Product>> {

        val response = api.getProductsListAsync().await()
        val body = response.body()
        body?.let {
            return Result.Success(body)
        } ?: run {
            val messageError = when(response.code()){
                401 -> "Unauthorize"
                502 -> "bad gateway"
                else -> "unexpected_error"
            }
            return Result.Error(
                Exception(messageError)
            )
        }
    }

    suspend fun getCategories(): Result<MutableList<String>> {

        val response = api.getCategoriesListAsync().await()
        val body = response.body()
        body?.let {
            return Result.Success(body)
        } ?: run {
            val messageError = when(response.code()){
                401 -> "Unauthorize"
                502 -> "bad gateway"
                else -> "unexpected_error"
            }
            return Result.Error(
                Exception(messageError)
            )
        }
    }

    suspend fun getProductsFiltered(path:String): Result<MutableList<Product>> {
        val response = api.getProductFilteredAsync(path).await()
        val body = response.body()
        body?.let {
            return Result.Success(body)
        } ?: run {
            val messageError = when(response.code()){
                401 -> "Unauthorize"
                502 -> "bad gateway"
                else -> "unexpected_error"
            }
            return Result.Error(
                Exception(messageError)
            )
        }
    }

    suspend fun getCarts(id:Int): Result<MutableList<Cart>> {

        val response = api.getCartsListAsync(id).await()
        val body = response.body()
        body?.let {
            return Result.Success(body)
        } ?: run {
            val messageError = when(response.code()){
                401 -> "Unauthorize"
                502 -> "bad gateway"
                else -> "unexpected_error"
            }
            return Result.Error(
                Exception(messageError)
            )
        }
    }

    suspend fun addCart(request:Cart): Result<Boolean> {
        val response = api.addToCartAsync(request).await()
        val body = response.body()
        body?.let {
            return Result.Success(body)
        } ?: run {
            val messageError = when(response.code()){
                401 -> "Unauthorize"
                502 -> "bad gateway"
                else -> "unexpected_error"
            }
            return Result.Error(
                Exception(messageError)
            )
        }
    }

}