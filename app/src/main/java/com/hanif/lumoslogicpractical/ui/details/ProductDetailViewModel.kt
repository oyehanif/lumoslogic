package com.hanif.lumoslogicpractical.ui.details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hanif.lumoslogicpractical.domain.model.Product
import com.hanif.lumoslogicpractical.domain.repository.ProductRepository
import com.hanif.lumoslogicpractical.ui.util.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductDetailViewModel @Inject constructor(
    private val repository: ProductRepository
) : ViewModel() {

    private val _productState = MutableStateFlow<UiState<Product>>(UiState.Loading)
    val productState: StateFlow<UiState<Product>> = _productState

    fun getProduct(id: Int) {
        viewModelScope.launch {
            _productState.value = UiState.Loading
            val product = repository.getProductById(id)
            if (product != null) {
                _productState.value = UiState.Success(product)
            } else {
                _productState.value = UiState.Error("Product not found")
            }
        }
    }
}
