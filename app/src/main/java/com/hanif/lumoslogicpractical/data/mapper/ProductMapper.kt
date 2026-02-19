package com.hanif.lumoslogicpractical.data.mapper

import com.hanif.lumoslogicpractical.data.local.ProductEntity
import com.hanif.lumoslogicpractical.data.remote.dto.ProductDto
import com.hanif.lumoslogicpractical.domain.model.Product
import com.hanif.lumoslogicpractical.domain.model.Rating

fun ProductDto.toEntity(): ProductEntity {
    return ProductEntity(
        id = id,
        title = title,
        price = price,
        description = description,
        category = category,
        image = thumbnail,
        rate = rating,
        count = 0
    )
}

fun ProductEntity.toDomain(): Product {
    return Product(
        id = id,
        title = title,
        price = price,
        description = description,
        category = category,
        image = image,
        rating = Rating(rate, count)
    )
}
