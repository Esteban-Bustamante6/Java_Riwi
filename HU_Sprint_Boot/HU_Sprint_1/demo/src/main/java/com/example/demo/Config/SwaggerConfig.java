package com.example.demo.Config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
        info = @Info(
                title = "Eventify API",
                version = "2.0",
                summary = "Persistent event and venue management",
                description = "Eventify is a comprehensive management system that uses Spring Data JPA " +
                        "and H2 (persistent mode) to store events and venues in a real database. " +
                        "Supports full CRUD, pagination (?page, ?size, ?sort) and proper 404/204 responses.",
                contact = @Contact(name = "Soporte EBL", email = "soporte@riwi.io")
        )
)
public class SwaggerConfig {

}