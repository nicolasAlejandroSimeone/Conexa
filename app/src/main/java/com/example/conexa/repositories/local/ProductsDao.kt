package com.example.conexa.repositories.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.conexa.models.CartProduct

@Dao
interface ProductsDao {
    @Insert
    fun insertProductsAdded(product: CartProduct)

    @Query("SELECT * FROM productsAdded")
    fun getAddedProducts():MutableList<CartProduct>

    @Query("DELETE FROM productsAdded WHERE productId LIKE :param")
    fun deleteAddedProduct(param:Int)

    @Query("DELETE FROM productsAdded")
    fun deleteAllProducts()
}