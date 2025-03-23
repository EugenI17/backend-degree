package ro.upt.backenddegree.dto

data class KitchenProductDto(
    val name: String,
    val extra: String? = null,
    val without: String? = null,
    val specification: String? = null,
)
