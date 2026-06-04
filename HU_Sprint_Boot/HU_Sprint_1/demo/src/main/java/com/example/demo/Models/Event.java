package com.example.demo.Models;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.BatchSize;
import org.hibernate.annotations.SQLRestriction;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "events")
@SQLRestriction("active = true")
@Schema(description = "Modelo que representa un evento en el sistema")
public class Event {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "Identificador unico del evento", example = "1")
    private Long id;

    @Column(nullable = false, length = 150)
    @NotBlank(message = "El nombre no puede estar vacio")
    @Size(max = 150)
    @Schema(description = "Nombre del evento", example = "Concierto de Rock Andino")
    private String name;

    @Column(nullable = false, length = 500)
    @NotBlank(message = "La descripcion no puede estar vacia")
    @Size(max = 500)
    @Schema(description = "Descripcion detallada del evento")
    private String description;

    @Column(nullable = false)
    @NotNull(message = "La fecha es obligatoria")
    @Schema(description = "Fecha y hora del evento", example = "2025-08-15T20:00:00")
    private LocalDateTime date;

    @Column
    @Min(value = 1, message = "La capacidad debe ser mayor a 0")
    @Schema(description = "Capacidad maxima de asistentes", example = "500")
    private Integer capacity;

    // SOFT DELETE: @SQLRestriction hace que TODAS las queries filtren
    // automaticamente active=true. Nunca se llama deleteById() sobre Event.
    @Column(nullable = false)
    @Schema(description = "true=activo, false=borrado logico")
    private boolean active = true;

    public void deactivate() {
        this.active = false;
    }

    // Many eventos -> One venue (obligatorio)
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "venue_id", nullable = false)
    @NotNull(message = "El lugar es obligatorio")
    @Schema(description = "Sede donde se realiza el evento")
    private Venues venue;

    // Many eventos <-> Many categories
    // @BatchSize: cuando Hibernate carga categorias de una lista de eventos
    // las agrupa en lotes de 25 con IN(id1..id25) en vez de un SELECT por evento.
    // Esto resuelve el N+1 SIN producir el warning HHH90003004.
    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(
            name = "events_categories",
            joinColumns        = @JoinColumn(name = "event_id"),
            inverseJoinColumns = @JoinColumn(name = "category_id")
    )
    @BatchSize(size = 25)
    @Schema(description = "Categorias tematicas del evento")
    private Set<Category> categories = new HashSet<>();
}
