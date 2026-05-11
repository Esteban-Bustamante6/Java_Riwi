package com.example.demo.Models;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Model representing an event within the system")
public class Event {

    @Schema(description = "Unique identifier of the event", example = "1")
    private Long id;

    @Schema(description = "Name of the event", example = "Java Conference")
    @NotBlank(message = "Name cannot be empty")
    private String name;

    @Schema(description = "Detailed description of the event", example = "A workshop to learn Spring Boot basics")
    @Size(max = 500, message = "Description cannot exceed 500 characters")
    private String description;
}