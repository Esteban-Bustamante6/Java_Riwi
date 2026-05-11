package com.example.demo.Repositories;

import com.example.demo.Models.Event;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class EventsRepository implements GenericRepository<Event , Long> {

    private final List<Event> event = new ArrayList<>();

    @Override
    public List<Event> findAll() {
        return event;
    }

    @Override
    public Event findById(Long id) {
        return event.stream()
                .filter(e -> e.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    @Override
    public void save(Event events) {
        event.add(events);

    }

    @Override
    public boolean delete(Long id) {
        return event.removeIf(e -> e.getId().equals(id));
    }

    @Override
    public Event update(Long id, Event event) {
        Event existing = this.findById(id);

        if (existing != null) {
            existing.setName(event.getName());
            existing.setDescription(event.getDescription());
            return existing;
        }

        return null;
    }
}
