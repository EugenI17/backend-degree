package ro.upt.backenddegree.service

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Service
import ro.upt.backenddegree.entity.User
import ro.upt.backenddegree.repository.UserRepository

@Service
class UserService(private val userRepository: UserRepository) {

    private val encoder = BCryptPasswordEncoder()

    fun createEmployee(user: User): User {
        if (userRepository.findByUsername(user.username) != null) {
            throw IllegalArgumentException("Username already exists.")
        }
        user.password = encoder.encode(user.password)
        return userRepository.save(user)
    }

    fun getAllEmployees(): List<User> = userRepository.findAll()
}
