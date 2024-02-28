package com.exercice.technicaltest.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.exercice.technicaltest.constants.Constants
import com.exercice.technicaltest.domain.usecases.AddProductUseCase
import com.exercice.technicaltest.domain.usecases.GetProductsUseCase
import com.exercice.technicaltest.models.Product
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val getProductsUseCase: GetProductsUseCase,
    private val addProductUseCase: AddProductUseCase
) : ViewModel() {
    private val _products = MutableLiveData<List<Product>>()
    val products: LiveData<List<Product>> = _products

    private val _productdetails = MutableLiveData<Product>()
    val productdetails: LiveData<Product> = _productdetails

    private val _anError = MutableLiveData<String>()
    val anError: LiveData<String> = _anError

    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean>
        get() = _loading

    /**
     * get products
     */
    fun getProducts() {
        viewModelScope.launch {
            getProductsUseCase.getProducts()
                .onStart {
                    _loading.value = true
                }.onCompletion {
                    _loading.value = false
                }.catch {
                    _anError.value = it.message
                }.collect { productsList ->
                    if (productsList.isFailure) {
                        _anError.value = Constants.EMPTY_PRODUCTS_ERROR_MSG
                    } else {
                        _products.value = productsList.getOrNull()
                    }
                }
        }
    }

    /**
     * get products
     */
    fun addProduct(image: String) {
        viewModelScope.launch {
            addProductUseCase.addProduct(image)
                .onStart {
                    _loading.value = true
                }.onCompletion {
                    _loading.value = false
                }.catch {
                    _anError.value = it.message
                }.collect { product ->
                    if (product.isFailure) {
                        _anError.value = Constants.EMPTY_PRODUCTS_ERROR_MSG
                    } else {
                        _productdetails.value = product.getOrNull()
                    }
                }
        }
    }
}