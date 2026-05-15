package com.example.demo.Services;

import com.example.demo.exceptions.ResourceNotFoundException;
import com.example.demo.Models.Venues;
import com.example.demo.Repositories.VenuesRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class VenuesService {

    private final VenuesRepository repository;

    public VenuesService(VenuesRepository repository) {
        this.repository = repository;
    }

    // Listar todos los venues con paginación y ordenamiento
    public Page<Venues> findAll(Pageable pageable) {
        return repository.findAll(pageable);
    }

    // Buscar por nombre con paginación
    public Page<Venues> findByName(String name, Pageable pageable) {
        return repository.findByNameContainingIgnoreCase(name, pageable);
    }

    // Buscar por ID — lanza 404 si no existe
    public Venues findById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Venue", id));
    }

    // Crear venue — el ID lo genera la BD automáticamente
    public Venues create(Venues venues) {
        venues.setId(null);
        return repository.save(venues);
    }

    // Actualizar venue — lanza 404 si no existe
    public Venues update(Long id, Venues updatedVenues) {
        Venues existing = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Venue", id));

        existing.setName(updatedVenues.getName());
        existing.setAddress(updatedVenues.getAddress());
        return repository.save(existing);
    }

    // Eliminar venue — lanza 404 si no existe
    public void delete(Long id) {
        if (!repository.existsById(id)) {
            throw new ResourceNotFoundException("Venue", id);
        }
        repository.deleteById(id);
    }
}