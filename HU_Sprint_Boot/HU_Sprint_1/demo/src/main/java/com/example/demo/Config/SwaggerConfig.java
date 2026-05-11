package com.example.demo.Config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
        info = @Info(
                title = "API of gestation of Corders",
                version = "1.0",
                summary = "Description" , description = "Eventify is a comprehensive management system developed for the Eventify company, designed to centralize and manage information about events and their venues. The system allows validating all necessary information before starting any event, ensuring that all details are properly configured and verified",
                contact = @Contact(name = "Soporte EBL", email = "soporte@riwi.io")
        )
)

public class SwaggerConfig {
}
