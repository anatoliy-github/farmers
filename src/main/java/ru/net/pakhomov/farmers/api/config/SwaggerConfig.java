package ru.net.pakhomov.farmers.api.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;

@OpenAPIDefinition(
        info = @Info(
                title = "Farmers and Districts API",
                version = "1.0.0",
                description = "RESTful Application for Districts and Farmers"
        )
)
public class SwaggerConfig {
}
