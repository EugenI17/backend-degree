package ro.upt.backenddegree.controller

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import ro.upt.backenddegree.dto.MenuProductDto
import ro.upt.backenddegree.service.MenuService

@RestController
@RequestMapping("/api/menu")
class MenuController {

    @Autowired
    private lateinit var menuProductService: MenuService

    @GetMapping
    fun getProducts(): ResponseEntity<Any> {
        return try {
            ResponseEntity.ok(menuProductService.getProducts())
        } catch (e: Exception) {
            ResponseEntity.badRequest().body(e.message)
        }
    }

    @PostMapping("/product")
    fun addProduct(@RequestBody productDto: MenuProductDto): ResponseEntity<Any> {
        return try {
            menuProductService.addProduct(productDto)
            ResponseEntity.ok("Product added successfully")
        } catch (e: Exception) {
            ResponseEntity.badRequest().body(e.message)
        }
    }



}