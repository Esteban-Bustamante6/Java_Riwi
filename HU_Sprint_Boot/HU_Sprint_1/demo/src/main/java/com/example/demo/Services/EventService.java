package com.example.demo.Services;

import com.example.demo.exceptions.ResourceNotFoundException;
import com.example.demo.Models.Event;
import com.example.demo.Repositories.EventsRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class EventService {

    private final EventsRepository repository;

    public EventService(EventsRepository repository) {
        this.repository = repository;
    }

    // Listar todos los eventos con paginación y ordenamiento
    public Page<Event> findAll(Pageable pageable) {
        return repository.findAll(pageable);
    }

    // Buscar por nombre con paginación
    public Page<Event> findByName(String name, Pageable pageable) {
        return repository.findByNameContainingIgnoreCase(name, pageable);
    }

    // Buscar por ID — lanza 404 si no existe
    public Event findById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Event", id));
    }

    // Crear evento — el ID lo genera la BD automáticamente
    public Event create(Event event) {
        // Garantizamos que no venga un ID manual (la BD lo asigna)
        event.setId(null);
        return repository.save(event);
    }

    // Actualizar evento — lanza 404 si no existe
    public Event update(Long id, Event updatedEvent) {
        Event existing = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Event", id));

        existing.setName(updatedEvent.getName());
        existing.setDescription(updatedEvent.getDescription());
        return repository.save(existing);
    }

    // Eliminar evento — lanza 404 si no existe
    public void delete(Long id) {
        if (!repository.existsById(id)) {
            throw new ResourceNotFoundException("Event", id);
        }
        repository.deleteById(id);
    }
}