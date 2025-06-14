package ro.upt.backenddegree.mapper

import lombok.AllArgsConstructor
import org.springframework.stereotype.Service
import ro.upt.backenddegree.dto.OrderDto
import ro.upt.backenddegree.entity.Order

@Service
@AllArgsConstructor
class OrderMapper (private val orderItemMapper: OrderItemMapper) {

    fun toDto(order: Order): OrderDto =
        OrderDto(
            tableNumber = order.tableNumber,
            orderItemDtos = order.items.map { orderItemMapper.toDto(it) },
            status = order.status?.name,
        )


}

