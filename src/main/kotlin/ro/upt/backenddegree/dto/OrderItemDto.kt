package ro.upt.backenddegree.dto

data class OrderItemDto(
    val productId: Long,
    val extra: String? = null,
    val without: String? = null,
    val specification: String? = null,
)
