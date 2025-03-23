package ro.upt.backenddegree.entity

import jakarta.persistence.*
import lombok.AllArgsConstructor
import lombok.Getter
import lombok.NoArgsConstructor
import lombok.Setter
import ro.upt.backenddegree.dto.MenuProductDto

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
data class Invoice(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    @ManyToMany
    val menuProducts: MutableList<MenuProductDto>,

    val discount: Double = 0.0
)