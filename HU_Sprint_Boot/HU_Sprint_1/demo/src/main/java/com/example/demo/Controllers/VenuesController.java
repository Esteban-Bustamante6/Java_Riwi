package com.example.demo.Controllers;

import com.example.demo.Models.Venues;
import com.example.demo.Services.VenuesService;
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
@RequestMapping("/api/venues")
@Tag(name = "Venues", description = "Management of event venues")
public class VenuesController {

    private final VenuesService service;

    public VenuesController(VenuesService service) {
        this.service = service;
    }

    @Operation(summary = "Get all the Events", description = "Returns a paginated list of all registered Events.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Operation completed"),
            @ApiResponse(responseCode = "404", description = "No venues found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping
    public ResponseEntity<?> findAll() {
        try {
            List<Venues> listVenues = service.findAll();

            if (listVenues.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("There are no venues currently registered.");
            }

            return ResponseEntity.ok(listVenues);

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error obtaining venues: " + e.getMessage());
        }
    }

    @Operation(summary = "Search Venues by id", description = "This returns all the data of the power related to that ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "This is your Venue"),
            @ApiResponse(responseCode = "404", description = "Venue ID not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable Long id) {
        try {
            Venues finID = service.findById(id);

            if (finID != null) {
                return ResponseEntity.ok("This is your selected venue: " + finID);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("No venue was found with ID: " + id);
            }

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error obtaining venues: " + e.getMessage());
        }
    }

    @Operation(summary = "Create Venues", description = "To create a Venues you need to pass 3 parameters: id, name, clan for example { 'id': '1', 'name':'esteban', 'clan':'tesla' }")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Your Venues was successfully created"),
            @ApiResponse(responseCode = "400", description = "Data is missing or invalid"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PostMapping
    public ResponseEntity<?> create(@Valid @RequestBody Venues venues) {
        try {
            Venues creatvenues = service.create(venues);

            if (creatvenues != null) {
                return ResponseEntity.status(HttpStatus.CREATED)
                        .body("Your venue was created perfectly: " + creatvenues);
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body("Error, data is missing");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error obtaining venues: " + e.getMessage());
        }
    }

    @Operation(summary = "Delete Venues for id", description = "We will remove a Venues that is related to the assigned id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Venues successfully removed"),
            @ApiResponse(responseCode = "404", description = "Venue not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        try {
            boolean deltVenues = service.delete(id);

            if (deltVenues) {
                return ResponseEntity.ok("Your venue: " + id + " was successfully deleted");
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("No venue was found with ID: " + id);
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error obtaining venues: " + e.getMessage());
        }
    }

    @Operation(summary = "Update created venues", description = "We will update the Venues related to the ID; this update will include the name and clan parameters.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Venues successfully updated"),
            @ApiResponse(responseCode = "400", description = "Missing fields"),
            @ApiResponse(responseCode = "404", description = "Venue not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @Valid @RequestBody Venues updatedVenues) {
        try {
            boolean wasUpdated = service.update(id, updatedVenues);

            if (wasUpdated) {
                return ResponseEntity.ok("Venue updated successfully");
            } else if (!wasUpdated) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body("Missing fields");
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("No venue was found with ID: " + id);
            }

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error processing the update: " + e.getMessage());
        }
    }
}