package com.hanif.lumoslogicpractical.data.remote.dto

data class ProductResponse(
    val products: List<ProductDto>,
    val total: Int,
    val skip: Int,
    val limit: Int
)

data class ProductDto(
    val id: Int,
    val title: String,
    val description: String,
    val price: Double,
    val category: String,
    val thumbnail: String,
    val rating: Double
)
