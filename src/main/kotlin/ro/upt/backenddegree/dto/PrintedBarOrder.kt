package ro.upt.backenddegree.dto

data class PrintedBarOrder(
    var tableNumber: String? = null,
    var items: List<PrintedItem>,
)
