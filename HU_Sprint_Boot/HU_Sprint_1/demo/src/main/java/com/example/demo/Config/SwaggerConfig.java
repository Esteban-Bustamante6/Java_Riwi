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
                summary = "Description" , description = "This is a Event and Venues management system where you can list and create new coders and assign them to a specific group (clan). You can also filter them by their clan or ID, as well as edit and delete them.",
                contact = @Contact(name = "Soporte EBL", email = "soporte@riwi.io")
        )
)

public class SwaggerConfig {
}
