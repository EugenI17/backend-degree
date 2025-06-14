package ro.upt.backenddegree.service

import org.springframework.stereotype.Service
import ro.upt.backenddegree.dto.*
import ro.upt.backenddegree.entity.MenuProduct
import ro.upt.backenddegree.entity.Order
import ro.upt.backenddegree.entity.OrderItem
import ro.upt.backenddegree.enums.OrderStatus
import ro.upt.backenddegree.enums.ProductType
import ro.upt.backenddegree.mapper.OrderMapper
import ro.upt.backenddegree.mapper.PrintedItemMapper
import ro.upt.backenddegree.repository.MenuProductRepository
import ro.upt.backenddegree.repository.OrderRepository
import java.util.Optional

@Service
class OrderService(
    private val orderRepository: OrderRepository,
    private val menuProductRepository: MenuProductRepository,
    private val printedItemMapper: PrintedItemMapper,
    private val printRequestService: PrintRequestService,
    private val orderMapper: OrderMapper,
    private val statisticsService: StatisticsService
) {

    fun createOrder(orderDto: OrderDto) {
        val order = Order(
            tableNumber = orderDto.tableNumber,
            status = OrderStatus.IN_PROGRESS
        )
        val orderItems = convertToOrderItems(orderDto.orderItemDtos, order)
        order.items.addAll(orderItems)
        orderRepository.save(order)
        statisticsService.addOrderToStatistics(order)
        handleOrderForPrint(orderDto)
    }

    fun addItemsToExistingOrder(orderDto: OrderDto) {
        val order = orderRepository.findByTableNumber(orderDto.tableNumber)
            ?: throw RuntimeException("Order not found for table number ${orderDto.tableNumber}")
        val orderItems = convertToOrderItems(orderDto.orderItemDtos, order)

        order.items.addAll(orderItems)
        orderRepository.save(order)
        statisticsService.addOrderToStatistics(order)
        handleOrderForPrint(orderDto)
    }

    private fun handleOrderForPrint( orderDto: OrderDto) {
        val kitchenOrder = prepareKitchenOrderForPrint(orderDto)
        val barOrder = prepareBarOrderForPrint(orderDto)

        if (kitchenOrder.isEmpty && barOrder.isEmpty) {
            throw RuntimeException("No items in the order for table number ${orderDto.tableNumber}")
        }
        kitchenOrder.ifPresent {
            printRequestService.printKitchenOrder(it)
        }
        barOrder.ifPresent {
            printRequestService.printBarOrder(it)
        }
    }

    fun completeOrder(orderDto: OrderDto) {
        val order = orderRepository.findByTableNumber(orderDto.tableNumber)
            ?: throw RuntimeException("Order not found for table number ${orderDto.tableNumber}")

        order.status = OrderStatus.COMPLETED
        orderRepository.save(order)

        val receiptItems = order.items.map{ printedItemMapper.toPrintedReceiptItem(it) }

        val receipt = PrintedReceipt(
            menuItems = receiptItems,
        )
        printRequestService.printReceipt(receipt)
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

    private fun prepareKitchenOrderForPrint(orderDto: OrderDto): Optional<PrintedKitchenOrder> {

        val printedStarters = getStarters(orderDto).map { printedItemMapper.toPrintedKitchenItem(it) }
        val printedMainCourses = getMainCourses(orderDto).map { printedItemMapper.toPrintedKitchenItem(it) }
        val printedDesserts = getDesserts(orderDto).map { printedItemMapper.toPrintedKitchenItem(it) }

        if (printedStarters.isEmpty() && printedMainCourses.isEmpty() && printedDesserts.isEmpty()) {
            return Optional.empty()
        }
        return Optional.of(
            PrintedKitchenOrder(
                tableNumber = orderDto.tableNumber,
                starters = printedStarters,
                main = printedMainCourses,
                desserts = printedDesserts,
            )
        )
    }

    private fun prepareBarOrderForPrint(orderDto: OrderDto): Optional<PrintedBarOrder> {

        val drinksGrouped = getDrinks(orderDto).groupBy { it.productId }
        val printedDrinks = drinksGrouped.map { (_, items) ->
            printedItemMapper.toPrintedBarItem(items.first(), items.size)
        }

        if (printedDrinks.isEmpty()) {
            return Optional.empty()
        }
        return Optional.of(
            PrintedBarOrder(
            tableNumber = orderDto.tableNumber,
            items = printedDrinks
            )
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

    fun getAllOrders(): List<OrderDto> {
        val orders = orderRepository.findAll()
        return orders.map { orderMapper.toDto(it) }
    }
}