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
@Table(name = "venues")
@Schema(description = "Model representing a venue within the system")
public class Venues {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "Unique identifier of the venue (auto-generated)", example = "1")
    private Long id;

    @Column(nullable = false, length = 150)
    @NotBlank(message = "Name cannot be empty")
    @Size(max = 150, message = "Name cannot exceed 150 characters")
    @Schema(description = "Name of the venue", example = "Boston Park")
    private String name;

    @Column(nullable = false, length = 500)
    @NotBlank(message = "Address cannot be empty")
    @Size(max = 500, message = "Address cannot exceed 500 characters")
    @Schema(description = "Detailed address of the venue", example = "Cra. 38 #54 - 97, La Candelaria, Medellín")
    private String address;
}