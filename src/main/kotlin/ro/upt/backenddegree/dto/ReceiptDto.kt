package ro.upt.backenddegree.dto

data class ReceiptDto(
    val menuProducts: List<ReceiptItemDto>,
    val discount: Double = 0.0
)
