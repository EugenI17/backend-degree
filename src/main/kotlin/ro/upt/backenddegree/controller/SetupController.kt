package ro.upt.backenddegree.controller

import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile
import ro.upt.backenddegree.dto.AdminSetupDto
import ro.upt.backenddegree.service.SetupService

@RestController
@RequestMapping("api/setup")
class SetupController(private val setupService: SetupService) {

    @GetMapping("/check")
    fun setupCheck(): ResponseEntity<Map<String, Any>> {
        val initialSetup = setupService.isInitialSetupNeeded()
        return ResponseEntity.ok(mapOf("initialSetupNeeded" to initialSetup))
    }

    @PostMapping(consumes = [MediaType.MULTIPART_FORM_DATA_VALUE])
    fun initialSetup(@RequestPart adminData: AdminSetupDto, @RequestPart logo: MultipartFile): ResponseEntity<Any> {
        if (!setupService.isInitialSetupNeeded()) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Setup already completed.")
        }
        setupService.performInitialSetup(adminData, logo.bytes)
        return ResponseEntity.ok("Setup completed.")
    }
}
