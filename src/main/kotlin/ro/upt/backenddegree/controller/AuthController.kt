package ro.upt.backenddegree.controller

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import ro.upt.backenddegree.entity.User
import ro.upt.backenddegree.service.JwtService

@RestController
@RequestMapping("/api/auth")
class AuthController(
    private val authenticationManager: AuthenticationManager,
    private val jwtService: JwtService,
    private val userDetailsService: UserDetailsService
) {

    data class TokenResponse(val accessToken: String, val refreshToken: String)
    data class RefreshRequest(val refreshToken: String)

    @PostMapping("/login")
    fun login(@RequestBody user: User): ResponseEntity<TokenResponse> {
        val auth = UsernamePasswordAuthenticationToken(user.username, user.password)
        authenticationManager.authenticate(auth)

        val userDetails = userDetailsService.loadUserByUsername(user.username)
        val roles = userDetails.authorities.map { it.authority }

        val accessToken = jwtService.generateAccessToken(user.username, roles)
        val refreshToken = jwtService.generateRefreshToken(user.username)

        return ResponseEntity.ok(TokenResponse(accessToken, refreshToken))
    }

    @PostMapping("/refresh")
    fun refresh(@RequestBody request: RefreshRequest): ResponseEntity<TokenResponse> {
        val refreshToken = request.refreshToken

        if (!jwtService.validateToken(refreshToken)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build()
        }

        val username = jwtService.extractUsername(refreshToken)
        val userDetails = userDetailsService.loadUserByUsername(username)
        val roles = userDetails.authorities.map { it.authority }

        val newAccessToken = jwtService.generateAccessToken(username, roles)
        val newRefreshToken = jwtService.generateRefreshToken(username)

        return ResponseEntity.ok(TokenResponse(newAccessToken, newRefreshToken))
    }
}