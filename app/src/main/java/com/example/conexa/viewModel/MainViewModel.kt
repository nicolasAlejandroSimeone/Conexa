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

class MainViewModel(private val productRepository: ProductRepository) : ViewModel() {

    private val _results = MutableLiveData<MutableList<Product>>()
    val results: LiveData<MutableList<Product>>
        get() = _results

    private val _resultsCategories = MutableLiveData<MutableList<String>>()
    val resultsCategories: LiveData<MutableList<String>>
        get() = _resultsCategories

    private val _resultsFiltered = MutableLiveData<MutableList<Product>>()
    val resultsFiltered: LiveData<MutableList<Product>>
        get() = _resultsFiltered

    private val _response = MutableLiveData<Boolean>()
    val response: LiveData<Boolean>
        get() = _response


    fun getAllProducts() {
        viewModelScope.launch {
            _results.value = productRepository.getProducts()
        }
    }

    fun getAllCategories() {
        viewModelScope.launch {
            _resultsCategories.value = productRepository.getCategories()
        }
    }

    fun getAllProductsFiltered(path:String) {
        viewModelScope.launch {
            _resultsFiltered.value = productRepository.getProductsFiltered(path)
        }
    }

    fun addToCart(request:Cart) {
        viewModelScope.launch {
            _response.value = productRepository.addToCart(request)
        }
    }

    fun insertAddedProducts(data:CartProduct){
        viewModelScope.launch {
            productRepository.insertProductAdded(data)
        }
    }
}