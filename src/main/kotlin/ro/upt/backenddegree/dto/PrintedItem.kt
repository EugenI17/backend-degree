package ro.upt.backenddegree.dto

data class PrintedItem(
    val name: String,
    val extra: String? = null,
    val without: String? = null,
    val specification: String? = null,
    val quantity: Int? = null,
)