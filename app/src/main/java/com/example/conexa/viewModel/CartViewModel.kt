package com.example.conexa.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.conexa.models.Cart
import com.example.conexa.models.CartProduct
import com.example.conexa.models.Product
import com.example.conexa.repositories.ProductRepository
import kotlinx.coroutines.launch

class CartViewModel(private val productRepository: ProductRepository) : ViewModel()  {
    private val _results = MutableLiveData<MutableList<CartProduct>>()
    val results: LiveData<MutableList<CartProduct>>
        get() = _results


    fun getAllAddedProducts() {
        viewModelScope.launch {
            _results.value = productRepository.getProductsAdded()
        }
    }

    fun deleteAddedProduct(id:Int) {
        viewModelScope.launch {
            productRepository.deleteProductAdded(id)
        }
    }

    fun deleteAllProducts(){
        viewModelScope.launch {
            productRepository.deleteAllProducts()
        }
    }

}