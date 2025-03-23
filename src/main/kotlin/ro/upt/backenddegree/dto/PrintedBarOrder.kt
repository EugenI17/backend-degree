import ro.upt.backenddegree.dto.PrintedItem


data class PrintedBarOrder(
    var tableNumber: String? = null,
    var items: List<PrintedItem>,
)
