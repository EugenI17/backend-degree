package ro.upt.backenddegree.entity

import jakarta.persistence.Entity
import jakarta.persistence.*
import ro.upt.backenddegree.enums.Role as Role

@Entity
@Table(name = "users")
data class User(

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    @Column(unique = true)
    val username: String,

    var password: String,

    @ElementCollection(fetch = FetchType.EAGER)
    @Enumerated(EnumType.STRING)
    var roles: MutableSet<Role> = mutableSetOf()
)
