package ro.upt.backenddegree.mapper

import org.springframework.stereotype.Service
import ro.upt.backenddegree.dto.OrderItemDto
import ro.upt.backenddegree.dto.PrintedItem
import ro.upt.backenddegree.dto.ReceiptItemDto
import ro.upt.backenddegree.entity.OrderItem
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

    fun toPrintedReceiptItem(orderItem: OrderItem): ReceiptItemDto {
        val menuProduct = menuProductRepository.findById(orderItem.product.id!!).get()
        return ReceiptItemDto(
            name = menuProduct.name,
            quantity = orderItem.quantity,
            price = menuProduct.price * orderItem.quantity
        )
    }

}