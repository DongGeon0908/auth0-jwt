package com.goofy.jwt.auth0

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConfigurationPropertiesBinding
import org.springframework.context.annotation.Configuration
import javax.validation.constraints.NotBlank

@Configuration
@ConfigurationProperties(prefix = "auth.jwt")
@ConfigurationPropertiesBinding
data class JwtConfig(
    @field:NotBlank
    var secret: String = "",
    val issuer: String = "goofy.kim"
)
