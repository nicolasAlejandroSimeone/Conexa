package com.example.conexa.repositories

import com.example.conexa.models.Cart
import com.example.conexa.models.CartProduct
import com.example.conexa.models.Product
import com.example.conexa.repositories.local.AppDatabase
import com.example.conexa.repositories.services.ProductService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import com.example.conexa.utils.Result

class ProductRepository(private val remoteDataSource:ProductService, private val localDataSource:AppDatabase) {
    suspend fun getProducts(): MutableList<Product> = withContext(Dispatchers.IO)
    {
        when (val result = remoteDataSource.getProducts()) {
            is Result.Success -> result.data
            is Result.Error -> throw result.exception
        }
    }

    suspend fun getCategories(): MutableList<String> = withContext(Dispatchers.IO)
    {
        when (val result = remoteDataSource.getCategories()) {
            is Result.Success -> result.data
            is Result.Error -> throw result.exception
        }
    }

    suspend fun getProductsFiltered(path:String): MutableList<Product> = withContext(Dispatchers.IO)
    {
        when (val result = remoteDataSource.getProductsFiltered(path)) {
            is Result.Success -> result.data
            is Result.Error -> throw result.exception
        }
    }

    suspend fun getCarts(id:Int): MutableList<Cart> = withContext(Dispatchers.IO)
    {
        when (val result = remoteDataSource.getCarts(id)) {
            is Result.Success -> result.data
            is Result.Error -> throw result.exception
        }
    }

    suspend fun addToCart(request: Cart): Boolean = withContext(Dispatchers.IO)
    {
        when (val result = remoteDataSource.addCart(request)) {
            is Result.Success -> result.data
            is Result.Error -> throw result.exception
        }
    }

    suspend fun insertProductAdded(product:CartProduct) = withContext(Dispatchers.IO){
        return@withContext localDataSource.ProductDAO().insertProductsAdded(product)
    }

    suspend fun getProductsAdded(): MutableList<CartProduct> = withContext(Dispatchers.IO){
        return@withContext localDataSource.ProductDAO().getAddedProducts()
    }

    suspend fun deleteProductAdded(id:Int) = withContext(Dispatchers.IO){
        return@withContext localDataSource.ProductDAO().deleteAddedProduct(id)
    }

    suspend fun deleteAllProducts() = withContext(Dispatchers.IO)
    {
        return@withContext localDataSource.ProductDAO().deleteAllProducts()
    }
}