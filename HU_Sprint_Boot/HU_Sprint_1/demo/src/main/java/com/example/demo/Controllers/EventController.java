package com.example.demo.Controllers;

import com.example.demo.Models.Event;
import com.example.demo.Services.EventService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/Events")

@Tag(name = "Gestation the Event", description = "operations related to events management")
public class EventController {

    private final EventService service;

    public EventController(EventService service) {
        this.service = service;
    }

    @Operation( summary = "Get all the Events" , description = "Returns a paginated list of all registered Events.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Operation completed"),
            @ApiResponse(responseCode = "404", description = "No event found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping
    public ResponseEntity<?> findAll() {
        try {
            List<Event> listaEventos = service.findAll();

            if (listaEventos.isEmpty()) {
                // Si la lista está vacía, devolvemos 204 (No Content) o 404
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("No hay eventos registrados actualmente.");
            }

            // Devolvemos la lista real con un 200 OK
            return ResponseEntity.ok(listaEventos);

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al obtener los eventos: " + e.getMessage());
        }
    }

    @Operation( summary = "search events by id" , description = "This returns all the data of the power related to that ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "This is your Event"),
            @ApiResponse(responseCode = "404", description = "Event ID not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable Long id) {
        try {
            Event finID = service.findById(id);

            if (finID != null) {
                return ResponseEntity.ok("Este es tu evento seleccionado : "+finID);
            }else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("No se encontró el evento con ID: " + id);
            }


        }catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al obtener los eventos: " + e.getMessage());
        }
    }


    @Operation( summary = "Create Events", description = "To create a Events you need to pass 3 parameters: id, name, clan for example { 'id': ''1 \n 'name':'esteban'\n 'clan':'tesla'")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Your event was successfully created"),
            @ApiResponse(responseCode = "400", description = "Data is missing or invalid"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PostMapping
    public ResponseEntity<?> create(@RequestBody Event event) {

        try {
            Event createvent = service.create(event);

            if (createvent != null){
                return ResponseEntity.ok("Se creo perfectamente tu evento : "+ createvent);
            }else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body("Eror faltan datos");
            }
        }catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al obtener los eventos: " + e.getMessage());
        }
    }


    @Operation(summary = "Delete Events for id", description = "We will remove a events that is related to the assigned id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Event successfully removed"),
            @ApiResponse(responseCode = "404", description = "Event not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {

        try {
            boolean deltEvent = service.delete(id);

            if (deltEvent){
                return ResponseEntity.ok("Tu evento :"+ id +" fue eliminado exitosamente");
            }else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("No se encontró el evento con ID: " + id);
            }
        }catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al obtener los eventos: " + e.getMessage());
        }
    }


    @Operation(summary = "update created events", description = "We will update the Events related to the ID; this update will include the name and clan parameters.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Events successfully updated"),
            @ApiResponse(responseCode = "400", description = "Missing fields"),
            @ApiResponse(responseCode = "404", description = "Event not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @Valid @RequestBody Event updatedEvent) {
        try {
            boolean wasUpdated = service.update(id, updatedEvent);

            if (wasUpdated) {
                return ResponseEntity.ok("Evento actualizado con éxito");
            } else if (!wasUpdated) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body("Faltan campos");
            }
            {
                // Si el servicio devuelve false, mandamos el 404
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("No se encontró el evento con ID: " + id);
            }

        } catch (Exception e) {
            // El catch captura cualquier error inesperado (nulos, errores de lógica, etc.)
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al procesar la actualización: " + e.getMessage());
        }
    }




}
