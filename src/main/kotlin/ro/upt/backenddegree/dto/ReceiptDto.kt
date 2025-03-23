package ro.upt.backenddegree.dto

data class InvoiceDto(
    val menuProducts: List<MenuProductDto>,
    val discount: Double = 0.0
)
