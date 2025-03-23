package ro.upt.backenddegree.repository

import org.springframework.data.jpa.repository.JpaRepository
import ro.upt.backenddegree.entity.Order

interface OrderRepository : JpaRepository<Order, Long> {
    fun findByTableNumber(tableNumber: String): Order?
}