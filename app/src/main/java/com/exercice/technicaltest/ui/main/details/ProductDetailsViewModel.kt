package com.exercice.technicaltest.ui.main.details

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.exercice.technicaltest.constants.Constants
import com.exercice.technicaltest.domain.usecases.GetProductDetailsUseCase
import com.exercice.technicaltest.models.Product
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductDetailsViewModel @Inject constructor(
    private val getProductDetailsUseCase: GetProductDetailsUseCase,
) : ViewModel() {

    /**
     * variables
     */
    private val _productdetails = MutableLiveData<Product>()
    val productdetails: LiveData<Product> = _productdetails

    private val _anError = MutableLiveData<String>()
    val anError: LiveData<String> = _anError

    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean>
        get() = _loading

    /**
     * get product details
     */
    fun getProductDetails(productId: Int) {
        viewModelScope.launch {
            getProductDetailsUseCase.getProductDetails(productId)
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