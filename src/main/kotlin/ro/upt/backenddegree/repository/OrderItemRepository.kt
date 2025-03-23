package ro.upt.backenddegree.repository

import org.springframework.data.jpa.repository.JpaRepository
import ro.upt.backenddegree.entity.OrderItem

interface OrderItemRepository : JpaRepository<OrderItem, Long>