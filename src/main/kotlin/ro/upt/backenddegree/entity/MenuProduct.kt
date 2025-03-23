package ro.upt.backenddegree.entity

import jakarta.persistence.*
import lombok.AllArgsConstructor
import lombok.Getter
import lombok.NoArgsConstructor
import lombok.Setter
import ro.upt.backenddegree.enums.ProductType

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
data class MenuProduct(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    val name: String,

    val price: Double,

    @ElementCollection
    @CollectionTable(name = "menu_product_ingredients", joinColumns = [JoinColumn(name = "menu_product_id")])
    @Column(name = "ingredient")
    val ingredients: List<String>? = listOf(),

    @Enumerated(EnumType.STRING)
    val type: ProductType

)
