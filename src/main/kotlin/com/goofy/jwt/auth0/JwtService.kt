package com.goofy.jwt.auth0

import com.auth0.jwt.JWT
import com.auth0.jwt.JWTVerifier
import com.auth0.jwt.algorithms.Algorithm
import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.module.kotlin.readValue
import org.springframework.stereotype.Service
import java.time.LocalDateTime
import java.time.ZoneOffset
import java.util.*

@Service
class JwtService(
    private val jwtConfig: JwtConfig
) {
    private val jwtVerifier: JWTVerifier = JWT
        .require(Algorithm.HMAC256(jwtConfig.secret))
        .withIssuer("goofy.kim")
        .build()

    fun create(
        expiredAt: LocalDateTime,
        payloads: Map<String, String>
    ): String {
        return JWT.create()
            .withIssuer(jwtConfig.issuer)
            .withExpiresAt(Date.from(expiredAt.toInstant(ZoneOffset.of("+09:00"))))
            .apply {
                payloads.forEach { (key, value) ->
                    this.withClaim(key, value)
                }
            }.sign(Algorithm.HMAC256(jwtConfig.secret))
    }

    fun <T> verify(token: String, typeRef: TypeReference<T>): T {
        val payload = jwtVerifier.verify(token).payload.decodeBase64()
        return mapper.readValue(payload, typeRef)
    }

    fun verify(token: String): Map<String, Any> {
        val payload = jwtVerifier.verify(token).payload.decodeBase64()
        return mapper.readValue(payload)
    }
}
