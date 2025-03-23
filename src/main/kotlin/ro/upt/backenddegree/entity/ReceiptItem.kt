package ro.upt.backenddegree.entity

import jakarta.persistence.*

@Embeddable
data class ReceiptItem(

    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    val product: MenuProduct,

    val quantity: Int
)