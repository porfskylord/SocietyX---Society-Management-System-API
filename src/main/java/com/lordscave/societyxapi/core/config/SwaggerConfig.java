package com.lordscave.societyxapi.core.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.context.annotation.Configuration;

@OpenAPIDefinition(
        info = @Info(title = "SocietyX API", version = "1.0", description = "API documentation for SocietyX"),
        security = @SecurityRequirement(name = "bearerAuth"),
        tags = {
                @Tag(name = "DEV MODULE - Dev Controller"),
                @Tag(name = "AUTHENTICATION MODULE"),
                @Tag(name = "ADMIN MODULE - User Management"),
                @Tag(name = "ADMIN MODULE - Admin Profile"),
                @Tag(name = "ADMIN MODULE - Society Management"),
                @Tag(name = "ADMIN MODULE - Gate Management"),
                @Tag(name = "ADMIN MODULE - Flat Management"),
                @Tag(name = "ADMIN MODULE - Resident Management"),
                @Tag(name = "ADMIN MODULE - Security Management"),
                @Tag(name = "RESIDENT MODULE - Resident Profile"),
                @Tag(name = "SECURITY MODULE - Flat-Resident Info"),
                @Tag(name = "SECURITY MODULE - Visitor Management")
        }
)
@SecurityScheme(
        name = "bearerAuth",
        type = SecuritySchemeType.HTTP,
        bearerFormat = "JWT",
        scheme = "bearer"
)
@Configuration
public class SwaggerConfig {
}
