package com.example.demo.Services;

import com.example.demo.Models.Event;
import com.example.demo.Repositories.EventsRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service

public class EventService {

    private final EventsRepository repository;

    public EventService(EventsRepository repository) {
        this.repository = repository;
    }

    public List<Event> findAll() {
        return repository.findAll();
    }

    public Event findById(Long id) {
        return repository.findById(id);
    }

    public Event create(Event event) {
        if (event.getName() == null || event.getName().isEmpty()) {
            throw new RuntimeException("El nombre es obligatorio");
        }
        if (event.getId() == null ){
            throw new RuntimeException("debe de contener un id Unico");
        }
        else if(event.getDescription() == null || event.getDescription().isEmpty()) {
            throw new RuntimeException("La Descripcion es obligatorio");
        }
        repository.save(event);
        return event;
    }

    public boolean delete(Long id) {

        if (id == null){
            throw new RuntimeException("debe de contener un id Unico");
        }
        return repository.delete(id);
    }

    public boolean update(Long id, Event updatedEvent) {
        if (updatedEvent.getName() == null || updatedEvent.getName().isEmpty()) {
            throw new RuntimeException("El nombre es obligatorio para actualizar");
        }
        if (updatedEvent.getId() == null || updatedEvent.getId() == updatedEvent.getId()){
            throw new RuntimeException("debe de contener un id Unico para actualizar");
        }
        else if(updatedEvent.getDescription() == null || updatedEvent.getDescription().isEmpty()) {
            throw new RuntimeException("La Descripcion es obligatorio para actualizar");
        }
        Event resultado = repository.update(id, updatedEvent);
        return (resultado != null);
    }

}
