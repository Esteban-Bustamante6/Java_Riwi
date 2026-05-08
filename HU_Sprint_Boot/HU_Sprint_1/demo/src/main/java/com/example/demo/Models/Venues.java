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
@Schema(description = "Model representing an venues within the system")
public class Venues {

    @Schema(description = "Unique identifier of the venues", example = "1")
    private Long id;

    @Schema(description = "Name of the venues", example = "boston park")
    @NotBlank(message = "Name cannot be empty")
    private String name;

    @Schema(description = "Detailed address of the venues", example = "Parque de BostonCra. 38 #54 - 97, La Candelaria, Medellín")
    @Size(max = 500, message = "address cannot exceed 100 characters")
    private String address;
}
