package ro.upt.backenddegree.service

import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import io.jsonwebtoken.security.Keys
import org.springframework.stereotype.Service
import java.util.*

import javax.crypto.SecretKey

@Service
class JwtService {

    private val secretKey: SecretKey = Keys.secretKeyFor(SignatureAlgorithm.HS256)

    private val accessExpirationMs: Long = 1000 * 60 * 15     // 15 minutes
    private val refreshExpirationMs: Long = 1000 * 60 * 60 * 24 * 7 // 7 days

    fun generateAccessToken(username: String, roles: List<String>): String {
        return generateToken(username, roles, accessExpirationMs)
    }

    fun generateRefreshToken(username: String): String {
        return generateToken(username, emptyList(), refreshExpirationMs)
    }

    private fun generateToken(username: String, roles: List<String>, expirationMs: Long): String {
        val claims = if (roles.isNotEmpty()) mapOf("roles" to roles) else emptyMap()

        return Jwts.builder()
            .setClaims(claims)
            .setSubject(username)
            .setIssuedAt(Date())
            .setExpiration(Date(System.currentTimeMillis() + expirationMs))
            .signWith(secretKey)
            .compact()
    }

    fun validateToken(token: String): Boolean {
        return try {
            Jwts.parserBuilder()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(token)
            true
        } catch (e: Exception) {
            println("JWT validation failed: ${e.message}")
            false
        }
    }
    fun extractUsername(token: String): String {
        return Jwts.parserBuilder().setSigningKey(secretKey).build().parseClaimsJws(token).body.subject
    }

    fun extractRoles(token: String): List<String> {
        val claims = Jwts.parserBuilder().setSigningKey(secretKey).build().parseClaimsJws(token).body
        @Suppress("UNCHECKED_CAST")
        return claims["roles"] as? List<String> ?: emptyList()
    }
}
