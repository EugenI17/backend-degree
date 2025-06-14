package ro.upt.backenddegree.mapper

import org.springframework.stereotype.Service
import ro.upt.backenddegree.dto.OrderItemDto
import ro.upt.backenddegree.entity.OrderItem

@Service
class OrderItemMapper {

    fun toDto(orderItem: OrderItem): OrderItemDto {
        return OrderItemDto(
            productId = orderItem.product.id!!,
            extra = null,
            without = null,
            specification = null,
        )
    }
}
