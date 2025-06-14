package ro.upt.backenddegree.controller

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import ro.upt.backenddegree.dto.OrderDto
import ro.upt.backenddegree.service.OrderService

@RestController
@RequestMapping("/api/order")
class OrderController (
    private val orderService: OrderService
) {

    @PostMapping
    fun createOrder(@RequestBody orderDto: OrderDto): ResponseEntity<Any> =
        try {
            orderService.createOrder(orderDto)
            ResponseEntity.ok("Order created.")
        } catch (e: Exception) {
            ResponseEntity.badRequest().body(e.message)
        }


    @PostMapping("/update")
    fun updateOrder(@RequestBody orderDto: OrderDto): ResponseEntity<Any> =
        try {
            orderService.addItemsToExistingOrder(orderDto)
            ResponseEntity.ok("Order updated.")
        } catch (e: Exception) {
            ResponseEntity.badRequest().body(e.message)
        }

    @PostMapping("/finish")
    fun finishOrder(@RequestBody orderDto: OrderDto): ResponseEntity<Any> =
        try {
            orderService.completeOrder(orderDto)
            ResponseEntity.ok("Order completed.")
        } catch (e: Exception) {
            ResponseEntity.badRequest().body(e.message)
        }

    @GetMapping
    fun getAllOrders(): ResponseEntity<Any> =
        try {
            ResponseEntity.ok(orderService.getAllOrders())
        } catch (e: Exception) {
            ResponseEntity.badRequest().body(e.message)
        }
}
