package com.example.demo.Dto;

import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;


@Schema(description = "Resumen liviano de un evento para listados y catálogos")
public record EventSummaryDTO(

        @Schema(description = "Nombre del evento", example = "Concierto de Rock Andino")
        String eventName,

        @Schema(description = "Fecha y hora del evento", example = "2025-08-15T20:00:00")
        LocalDateTime date,

        @Schema(description = "Nombre del lugar donde se realiza", example = "Teatro Metropolitano")
        String venueName,

        @Schema(description = "Ciudad del lugar", example = "Medellín")
        String city
) {}