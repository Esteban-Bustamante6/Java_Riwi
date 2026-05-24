package com.example.demo.Models;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "events")
@Schema(description = "Model representing an event within the system")
public class Event {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "Unique identifier of the event (auto-generated)", example = "1")
    private Long id;

    @Column(nullable = false, length = 150)
    @NotBlank(message = "Name cannot be empty")
    @Size(max = 150, message = "Name cannot exceed 150 characters")
    @Schema(description = "Name of the event", example = "Java Conference")
    private String name;

    @Column(nullable = false, length = 500)
    @NotBlank(message = "Description cannot be empty")
    @Size(max = 500, message = "Description cannot exceed 500 characters")
    @Schema(description = "Detailed description of the event", example = "A workshop to learn Spring Boot basics")
    private String description;
}