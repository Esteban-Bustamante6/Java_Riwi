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
@Table(name = "categories")
@Schema(description = "Clasificación temática de un evento")
public class Category{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "Identificador único de la categoría", example = "1")
    private Long id;

    @Column(nullable = false, length = 100, unique = true)
    @NotBlank(message = "El nombre de la categoría no puede estar vacío")
    @Size(max = 100)
    @Schema(description = "Nombre de la categoría", example = "Conciertos")
    private String name;

    @Column(length = 300)
    @Size(max = 300)
    @Schema(description = "Descripción de la categoría", example = "Eventos musicales en vivo")
    private String description;
}