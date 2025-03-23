package ro.upt.backenddegree.dto

data class PrintedKitchenItem(
    private val name: String,
    val extra: String? = null,
    val without: String? = null,
    val specification: String? = null,
)