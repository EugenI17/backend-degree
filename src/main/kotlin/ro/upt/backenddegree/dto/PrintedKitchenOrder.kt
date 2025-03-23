package ro.upt.backenddegree.dto

data class PrintedKitchenOrder(
    val tableNumber: String,
    val starters: List<PrintedItem>,
    val main: List<PrintedItem>,
    val desserts: List<PrintedItem>,
)