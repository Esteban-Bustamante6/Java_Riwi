package com.example.demo.Controllers;

import com.example.demo.Models.Event;
import com.example.demo.Services.EventService;
import com.example.demo.Dto.EventSummaryDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/events")
@Tag(name = "Events", description = "Gestión de eventos con soporte de filtros, paginación Slice y borrado lógico")
public class EventController {

    private final EventService service;

    public EventController(EventService service) {
        this.service = service;
    }

    // ── CATÁLOGO LIVIANO (DTO + Slice) ────────────────────────────────────────
    @Operation(
            summary = "Catálogo de eventos (DTO optimizado)",
            description = """
                    Retorna un Slice<EventSummaryDTO> con los campos aplanados:
                    nombre, fecha, nombre del lugar y ciudad.
                    Usa Slice en lugar de Page para evitar SELECT COUNT(*) — más eficiente.
                    Los registros con active=false (borrado lógico) son excluidos automáticamente
                    por @SQLRestriction en la entidad Event.
                    Ordenado por fecha descendente.
                    """
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Slice de eventos retornado"),
            @ApiResponse(responseCode = "204", description = "No hay eventos")
    })
    @GetMapping("/summary")
    public ResponseEntity<Slice<EventSummaryDTO>> getSummary(
            @Parameter(description = "Número de página (0-based)", example = "0")
            @RequestParam(defaultValue = "0") int page,
            @Parameter(description = "Tamaño de página", example = "10")
            @RequestParam(defaultValue = "10") int size) {

        Slice<EventSummaryDTO> result = service.findAllSummaries(page, size);
        return result.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(result);
    }

    // ── BÚSQUEDA CON FILTROS ──────────────────────────────────────────────────
    @Operation(
            summary = "Buscar eventos con filtros",
            description = """
                    Motor de búsqueda avanzado. Solo se aplica un filtro a la vez (por orden de prioridad):
                    name → city → category → rango de fechas → capacidad mínima.
                    Todas las búsquedas son parciales e insensibles a mayúsculas (Escenario 6).
                    Los eventos con borrado lógico (active=false) nunca aparecen en resultados.
                    """
    )
    @GetMapping
    public ResponseEntity<Slice<Event>> search(
            @Parameter(description = "Filtrar por nombre del evento (parcial, case-insensitive)")
            @RequestParam(required = false) String name,

            @Parameter(description = "Filtrar por ciudad del venue (parcial, case-insensitive). Ej: 'bog' encuentra 'Bogotá'")
            @RequestParam(required = false) String city,

            @Parameter(description = "Filtrar por nombre de categoría (parcial, case-insensitive). Ej: 'rock' encuentra 'Conciertos de Rock'")
            @RequestParam(required = false) String category,

            @Parameter(description = "Fecha inicio del rango (ISO format)")
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime from,

            @Parameter(description = "Fecha fin del rango (ISO format)")
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime to,

            @Parameter(description = "Capacidad mínima requerida")
            @RequestParam(required = false) Integer capacity,

            @Parameter(description = "Número de página (0-based)", example = "0")
            @RequestParam(defaultValue = "0") int page,

            @Parameter(description = "Tamaño de página", example = "10")
            @RequestParam(defaultValue = "10") int size) {

        Slice<Event> result = service.search(name, city, category, from, to, capacity, page, size);
        return result.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(result);
    }

    @Operation(summary = "Obtener evento por ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Evento encontrado"),
            @ApiResponse(responseCode = "404", description = "Evento no encontrado o inactivo (borrado lógico)")
    })
    @GetMapping("/{id}")
    public ResponseEntity<Event> findById(@PathVariable Long id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @Operation(
            summary = "Crear nuevo evento",
            description = "Crea un evento con venue y categorías asignadas. El ID es auto-generado."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Evento creado exitosamente"),
            @ApiResponse(responseCode = "400", description = "Validación fallida"),
            @ApiResponse(responseCode = "404", description = "Venue o categoría no encontrada")
    })
    @PostMapping
    public ResponseEntity<Event> create(
            @Valid @RequestBody Event event,
            @Parameter(description = "ID del venue asignado al evento", required = true)
            @RequestParam Long venueId,
            @Parameter(description = "IDs de las categorías del evento")
            @RequestParam(required = false) List<Long> categoryIds) {

        Event created = service.create(event, venueId, categoryIds);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @Operation(summary = "Actualizar evento existente")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Evento actualizado"),
            @ApiResponse(responseCode = "400", description = "Validación fallida"),
            @ApiResponse(responseCode = "404", description = "Evento, venue o categoría no encontrada")
    })
    @PutMapping("/{id}")
    public ResponseEntity<Event> update(
            @PathVariable Long id,
            @Valid @RequestBody Event event,
            @RequestParam Long venueId,
            @RequestParam(required = false) List<Long> categoryIds) {

        return ResponseEntity.ok(service.update(id, event, venueId, categoryIds));
    }

    @Operation(
            summary = "Eliminar evento (borrado lógico)",
            description = """
                    NO ejecuta DELETE físico. Marca el campo active=false en la base de datos.
                    El evento deja de aparecer en cualquier consulta gracias a @SQLRestriction.
                    Esto garantiza la trazabilidad histórica requerida por las reglas de auditoría.
                    """
    )
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Evento desactivado (borrado lógico aplicado)"),
            @ApiResponse(responseCode = "404", description = "Evento no encontrado")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}