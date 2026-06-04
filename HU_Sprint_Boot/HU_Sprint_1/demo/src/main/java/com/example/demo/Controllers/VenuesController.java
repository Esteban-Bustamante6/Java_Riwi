package com.example.demo.Controllers;

import com.example.demo.Models.Venues;
import com.example.demo.Services.VenuesService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/venues")
@Tag(name = "Venues", description = "Gestión de sedes y lugares físicos")
public class VenuesController {

    private final VenuesService service;

    public VenuesController(VenuesService service) {
        this.service = service;
    }

    @Operation(
            summary = "Listar venues (paginado)",
            description = "Retorna una página de venues. Filtro opcional por nombre."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Página de venues retornada"),
            @ApiResponse(responseCode = "204", description = "No hay venues")
    })
    @GetMapping
    public ResponseEntity<Page<Venues>> findAll(
            @Parameter(description = "Filtrar por nombre (opcional, case-insensitive)")
            @RequestParam(required = false) String name,
            @ParameterObject
            @PageableDefault(size = 10, sort = "name", direction = Sort.Direction.ASC)
            Pageable pageable) {

        Page<Venues> result = (name != null && !name.isBlank())
                ? service.findByName(name, pageable)
                : service.findAll(pageable);

        return result.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(result);
    }

    @Operation(summary = "Obtener venue por ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Venue encontrado"),
            @ApiResponse(responseCode = "404", description = "Venue no encontrado")
    })
    @GetMapping("/{id}")
    public ResponseEntity<Venues> findById(@PathVariable Long id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @Operation(
            summary = "Crear nuevo venue",
            description = "Crea un venue con nombre, dirección y ciudad. El campo ciudad es obligatorio."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Venue creado exitosamente"),
            @ApiResponse(responseCode = "400", description = "Validación fallida — campos obligatorios: name, address, city")
    })
    @PostMapping
    public ResponseEntity<Venues> create(@Valid @RequestBody Venues venue) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.create(venue));
    }

    @Operation(summary = "Actualizar venue existente", description = "Actualiza nombre, dirección y ciudad.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Venue actualizado"),
            @ApiResponse(responseCode = "400", description = "Validación fallida"),
            @ApiResponse(responseCode = "404", description = "Venue no encontrado")
    })
    @PutMapping("/{id}")
    public ResponseEntity<Venues> update(@PathVariable Long id, @Valid @RequestBody Venues venue) {
        return ResponseEntity.ok(service.update(id, venue));
    }

    @Operation(summary = "Eliminar venue", description = "Elimina físicamente el venue. No aplica borrado lógico.")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Venue eliminado"),
            @ApiResponse(responseCode = "404", description = "Venue no encontrado")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}