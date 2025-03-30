package ro.upt.backenddegree.controller

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
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

    @DeleteMapping("/product/{id}")
    fun deleteProduct(@PathVariable id: Long): ResponseEntity<Any> {
        return try {
            menuProductService.deleteProduct(id)
            ResponseEntity.ok("Product deleted successfully")
        } catch (e: Exception) {
            ResponseEntity.badRequest().body(e.message)
        }

    }

    @PutMapping("/product")
    fun updateProduct(@RequestBody productDto: MenuProductDto): ResponseEntity<Any> {
        return try {
            menuProductService.updateProduct(productDto)
            ResponseEntity.ok("Product updated successfully")
        } catch (e: Exception) {
            ResponseEntity.badRequest().body(e.message)
        }
    }

}