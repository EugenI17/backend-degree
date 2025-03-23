package ro.upt.backenddegree.dto

import ro.upt.backenddegree.enums.ProductType

data class MenuProductDto(
    val id: Long?,
    val name: String,
    val price: Double,
    val ingredients: List<String>? = null,
    val type: ProductType,
)