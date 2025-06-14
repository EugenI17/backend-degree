package ro.upt.backenddegree.dto

data class OrderDto (
    var tableNumber: String,
    var orderItemDtos: List<OrderItemDto>,
    var status: String? = null,
)