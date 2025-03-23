package ro.upt.backenddegree.service

import PrintedBarOrder
import org.springframework.stereotype.Service
import ro.upt.backenddegree.dto.OrderDto
import ro.upt.backenddegree.dto.OrderItemDto
import ro.upt.backenddegree.dto.PrintedKitchenOrder
import ro.upt.backenddegree.entity.MenuProduct
import ro.upt.backenddegree.entity.Order
import ro.upt.backenddegree.entity.OrderItem
import ro.upt.backenddegree.enums.OrderStatus
import ro.upt.backenddegree.enums.ProductType
import ro.upt.backenddegree.mapper.PrintedItemMapper
import ro.upt.backenddegree.repository.MenuProductRepository
import ro.upt.backenddegree.repository.OrderRepository

@Service
class OrderService(
    private val orderRepository: OrderRepository,
    private val menuProductRepository: MenuProductRepository,
    private val printedItemMapper: PrintedItemMapper,
    private val printRequestService: PrintRequestService
) {

    fun createOrder(orderDto: OrderDto) {

        val order = Order(
            tableNumber = orderDto.tableNumber,
            status = OrderStatus.IN_PROGRESS
        )
        val orderItems = convertToOrderItems(orderDto.orderItemDtos, order)
        order.items.addAll(orderItems)
        orderRepository.save(order)
        printRequestService.printKitchenOrder(prepareKitchenOrderForPrint(orderDto))
        printRequestService.printBarOrder(prepareBarOrderForPrint(orderDto))
    }

    private fun convertToOrderItems(orderItemsDto : List<OrderItemDto>, order: Order): List<OrderItem> {
        val productIdToQuantity = orderItemsDto
            .groupingBy { it.productId }
            .eachCount()

        return productIdToQuantity.map { (productId, quantity) ->
            val product = menuProductRepository.findById(productId)
                .orElseThrow { RuntimeException("Product with ID $productId not found") }
            OrderItem(
                order = order,
                product = product,
                quantity = quantity
            )
        }
    }

    fun addItemsToExistingOrder(orderDto: OrderDto) {
        val order = orderRepository.findByTableNumber(orderDto.tableNumber)
            ?: throw RuntimeException("Order not found for table number ${orderDto.tableNumber}")
        val orderItems = convertToOrderItems(orderDto.orderItemDtos, order)

        order.items.addAll(orderItems)
        orderRepository.save(order)

        printRequestService.printKitchenOrder(prepareKitchenOrderForPrint(orderDto))
        printRequestService.printBarOrder(prepareBarOrderForPrint(orderDto))
    }

    private fun prepareKitchenOrderForPrint(orderDto: OrderDto): PrintedKitchenOrder {

        val printedStarters = getStarters(orderDto).map { printedItemMapper.toPrintedKitchenItem(it) }
        val printedMainCourses = getMainCourses(orderDto).map { printedItemMapper.toPrintedKitchenItem(it) }
        val printedDesserts = getDesserts(orderDto).map { printedItemMapper.toPrintedKitchenItem(it) }

        return PrintedKitchenOrder(
            tableNumber = orderDto.tableNumber,
            starters = printedStarters,
            main = printedMainCourses,
            desserts = printedDesserts,
        )
    }

    private fun prepareBarOrderForPrint(orderDto: OrderDto): PrintedBarOrder {

        val drinksGrouped = getDrinks(orderDto).groupBy { it.productId }
        val printedDrinks = drinksGrouped.map { (_, items) ->
            printedItemMapper.toPrintedBarItem(items.first(), items.size)
        }

        return PrintedBarOrder(
            tableNumber = orderDto.tableNumber,
            items = printedDrinks
        )
    }

    private fun getStarters(orderDto: OrderDto): List<OrderItemDto> =
        orderDto.orderItemDtos.filter { getProductType(it.productId) == ProductType.STARTER }

    private fun getMainCourses(orderDto: OrderDto): List<OrderItemDto> =
        orderDto.orderItemDtos.filter { getProductType(it.productId) == ProductType.MAIN }

    private fun getDesserts(orderDto: OrderDto): List<OrderItemDto> =
        orderDto.orderItemDtos.filter { getProductType(it.productId) == ProductType.DESSERT }

    private fun getDrinks(orderDto: OrderDto): List<OrderItemDto> =
        orderDto.orderItemDtos.filter { getProductType(it.productId) == ProductType.DRINK }

    private fun getProductType(productId: Long): ProductType {
        val menuProduct: MenuProduct = menuProductRepository.findById(productId).get()
        return menuProduct.type
    }
}