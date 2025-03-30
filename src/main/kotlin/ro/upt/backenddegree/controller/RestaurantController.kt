package ro.upt.backenddegree.controller

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import ro.upt.backenddegree.service.SetupService

@RestController
@RequestMapping("/api/restaurant")
class RestaurantController (
    private val setupService: SetupService
){

    @GetMapping
    fun getRestaurantInfo(): ResponseEntity<Any> {
        return try {
            ResponseEntity.ok(setupService.loadSetupData())
        } catch (e: Exception) {
            ResponseEntity.badRequest().body(e.message)
        }
    }

    @GetMapping("/logo")
    fun getLogo(): ResponseEntity<Any> {
        return try {
            val logo = setupService.loadLogo()
            ResponseEntity.ok(logo)
        } catch (e: Exception) {
            ResponseEntity.badRequest().body(e.message)
        }
    }
}
