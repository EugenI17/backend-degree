package ro.upt.backenddegree.entity

import jakarta.persistence.*

@Entity
class OrderItem(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    @ManyToOne
    @JoinColumn(name = "order_id")
    val order: Order,

    @ManyToOne
    @JoinColumn(name = "product_id")
    val product: MenuProduct,

    var quantity: Int
)
