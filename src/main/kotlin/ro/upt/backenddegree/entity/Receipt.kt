package ro.upt.backenddegree.entity

import jakarta.persistence.*
import lombok.AllArgsConstructor
import lombok.Getter
import lombok.NoArgsConstructor
import lombok.Setter

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
data class Receipt(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    @ElementCollection
    @CollectionTable(name = "receipt_items", joinColumns = [JoinColumn(name = "receipt_id")])
    val items: MutableList<ReceiptItem> = mutableListOf(),

    val discount: Double = 0.0
)