package ro.upt.backenddegree.service

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import org.springframework.beans.factory.annotation.Value
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Service
import ro.upt.backenddegree.dto.AdminSetupDto
import ro.upt.backenddegree.entity.User
import ro.upt.backenddegree.enums.Role
import ro.upt.backenddegree.repository.UserRepository
import java.nio.file.Files
import java.nio.file.Paths

@Service
class SetupService(
    private val userRepository: UserRepository,
    @Value("\${restaurant.setup.init-path}")
    private val setupFilePathStr: String,
    @Value("\${restaurant.setup.logo-dir}")
    private val logoDir: String,
    ) {
        private val setupFilePath = Paths.get(setupFilePathStr)


    fun isInitialSetupNeeded(): Boolean = Files.notExists(setupFilePath)

    fun performInitialSetup(adminSetupDto: AdminSetupDto, logo: ByteArray) {
        val encryptedPassword = BCryptPasswordEncoder().encode(adminSetupDto.password)
        adminSetupDto.password = encryptedPassword
        val mapper = jacksonObjectMapper()

        Files.writeString(setupFilePath, mapper.writeValueAsString(adminSetupDto))
        Files.write(Paths.get(logoDir, "logo.png"), logo)

        val adminUser = User(
            username = adminSetupDto.username,
            password = encryptedPassword,
            roles = mutableSetOf(Role.ROLE_ADMIN)
        )
        userRepository.save(adminUser)
    }

    fun loadSetupData(): Map<String, String> {
        val mapper = jacksonObjectMapper()
        return mapper.readValue(setupFilePath.toFile())
    }

    fun loadLogo(): ByteArray = Files.readAllBytes(Paths.get(logoDir, "logo.png"))
}