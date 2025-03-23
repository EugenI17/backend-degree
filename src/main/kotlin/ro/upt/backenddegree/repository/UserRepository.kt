package ro.upt.backenddegree.repository

import org.springframework.data.jpa.repository.JpaRepository
import ro.upt.backenddegree.entity.User

interface UserRepository : JpaRepository<User, Long> {
    fun findByUsername(username: String): User?
}