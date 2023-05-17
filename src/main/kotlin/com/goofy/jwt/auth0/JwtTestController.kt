package com.goofy.jwt.auth0

import org.springframework.stereotype.Service
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import java.time.LocalDateTime

@RestController
class JwtTestController(
    private val jwtTestService: JwtTestService
) {
    @PostMapping("/create-jwt")
    fun createJwt() = jwtTestService.createToken()

    @GetMapping("/verify-jwt")
    fun verifyJwt(@RequestParam token: String) = jwtTestService.verify(token)
}


@Service
class JwtTestService(
    private val jwtService: JwtService
) {
    fun createToken(): String {
        return jwtService.create(
            expiredAt = LocalDateTime.now().plusDays(1),
            payloads = mapOf("claim-key" to "claim-value")
        )
    }

    fun verify(token: String): Map<String, Any> {
        return jwtService.verify(token)
    }
}
