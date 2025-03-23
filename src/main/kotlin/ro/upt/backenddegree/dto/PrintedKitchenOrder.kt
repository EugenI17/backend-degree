package ro.upt.backenddegree.dto

data class KitchenOrderDto(
    val tableNumber: String,
    val starters: List<KitchenProductDto>,
    val main: List<KitchenProductDto>,
    val desserts: List<KitchenProductDto>,
)

data class KitchenProductDto(
    private val name: String,
    val extra: String? = null,
    val without: String? = null,
    val specification: String? = null,
)