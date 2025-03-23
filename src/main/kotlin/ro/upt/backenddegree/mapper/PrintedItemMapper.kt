package ro.upt.backenddegree.mapper

import org.springframework.stereotype.Service
import ro.upt.backenddegree.dto.OrderItemDto
import ro.upt.backenddegree.dto.PrintedItem
import ro.upt.backenddegree.repository.MenuProductRepository

@Service
class PrintedItemMapper (
    private val menuProductRepository: MenuProductRepository
){

    fun toPrintedKitchenItem(orderItemDto: OrderItemDto): PrintedItem {
        val menuProduct = menuProductRepository.findById(orderItemDto.productId).get()
        return PrintedItem(
            name = menuProduct.name,
            extra = orderItemDto.extra,
            without = orderItemDto.without,
            specification = orderItemDto.specification
        )
    }

    fun toPrintedBarItem(orderItemDto: OrderItemDto, qunatity: Int): PrintedItem {
        val menuProduct = menuProductRepository.findById(orderItemDto.productId).get()
        return PrintedItem(
            name = menuProduct.name,
            quantity = qunatity
        )
    }

}