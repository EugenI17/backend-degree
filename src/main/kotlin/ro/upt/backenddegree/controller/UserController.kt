package ro.upt.backenddegree.controller

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import ro.upt.backenddegree.entity.User
import ro.upt.backenddegree.service.UserService

@RestController
@RequestMapping("/api/user")
class UserController(
    private val userService: UserService
) {

    @PostMapping
    fun createEmployeeAccount(@RequestBody user: User): ResponseEntity<Any> {
        return try {
            userService.createEmployee(user)
            ResponseEntity.ok("Account created.")
        } catch (e: Exception) {
            ResponseEntity.badRequest().body(e.message)
        }
    }

    @GetMapping
    fun getAllEmployees(): ResponseEntity<Any> {
        return try {
            ResponseEntity.ok(userService.getAllEmployees())
        } catch (e: Exception) {
            ResponseEntity.badRequest().body(e.message)
        }
    }

    @DeleteMapping("/{id}")
    fun deleteEmployeeAccount(@PathVariable id: Long): ResponseEntity<Any> =
        try {
            userService.deleteById( id)
            ResponseEntity.ok("Account deleted.")
        } catch (e: Exception) {
            ResponseEntity.badRequest().body(e.message)
        }

}
