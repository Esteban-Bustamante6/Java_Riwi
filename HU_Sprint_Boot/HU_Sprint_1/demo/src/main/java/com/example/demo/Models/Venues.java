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
@Schema(description = "Modelo que representa una sede o lugar físico")
public class Venues {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "Identificador único del lugar (auto-generado)", example = "1")
    private Long id;

    @Column(nullable = false, length = 150)
    @NotBlank(message = "El nombre no puede estar vacío")
    @Size(max = 150, message = "El nombre no puede superar 150 caracteres")
    @Schema(description = "Nombre del lugar", example = "Teatro Metropolitano")
    private String name;

    @Column(nullable = false, length = 500)
    @NotBlank(message = "La dirección no puede estar vacía")
    @Size(max = 500, message = "La dirección no puede superar 500 caracteres")
    @Schema(description = "Dirección detallada del lugar", example = "Calle 41 #57-30, Medellín")
    private String address;

    @Column(nullable = false, length = 100)
    @NotBlank(message = "La ciudad no puede estar vacía")
    @Size(max = 100, message = "La ciudad no puede superar 100 caracteres")
    @Schema(description = "Ciudad donde se ubica el lugar", example = "Medellín")
    private String city;
}