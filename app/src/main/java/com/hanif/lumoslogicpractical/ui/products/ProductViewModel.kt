package com.hanif.lumoslogicpractical.ui.products

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.hanif.lumoslogicpractical.domain.model.Product
import com.hanif.lumoslogicpractical.domain.repository.ProductRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class ProductViewModel @Inject constructor(
    private val repository: ProductRepository
) : ViewModel() {

    val productsFlow: Flow<PagingData<Product>> = repository.getProducts()
        .cachedIn(viewModelScope)
}
