package com.example.demo.Services;

import com.example.demo.Models.Category;
import com.example.demo.Models.Event;
import com.example.demo.Models.Venues;
import com.example.demo.Repositories.CategoryRepository;
import com.example.demo.Repositories.EventsRepository;
import com.example.demo.Repositories.VenuesRepository;
import com.example.demo.Dto.EventSummaryDTO;
import com.example.demo.exceptions.ResourceNotFoundException;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@Transactional
public class EventService {

    private final EventsRepository eventRepository;
    private final VenuesRepository venueRepository;
    private final CategoryRepository categoryRepository;

    public EventService(EventsRepository eventRepository,
                        VenuesRepository venueRepository,
                        CategoryRepository categoryRepository) {
        this.eventRepository  = eventRepository;
        this.venueRepository  = venueRepository;
        this.categoryRepository = categoryRepository;
    }

    // ── LISTADO LIVIANO (DTO + Slice) ────────────────────────────────────────
    @Transactional(readOnly = true)
    public Slice<EventSummaryDTO> findAllSummaries(int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("date").descending());
        return eventRepository.findAllSummaries(pageable);
    }

    // ── BÚSQUEDA CON FILTROS ─────────────────────────────────────────────────
    @Transactional(readOnly = true)
    public Slice<Event> search(String name, String city, String category,
                               LocalDateTime from, LocalDateTime to,
                               Integer capacity, int page, int size) {

        Pageable pageable = PageRequest.of(page, size, Sort.by("date").descending());

        if (name     != null && !name.isBlank())     return eventRepository.findByNameContaining(name, pageable);
        if (city     != null && !city.isBlank())     return eventRepository.findByCityContaining(city, pageable);
        if (category != null && !category.isBlank()) return eventRepository.findByCategoryName(category, pageable);
        if (from     != null && to != null)          return eventRepository.findByDateBetween(from, to, pageable);
        if (capacity != null)                        return eventRepository.findByCapacityGreaterThanEqual(capacity, pageable);

        // Sin filtros → listado general con detalles (resuelve N+1)
        return eventRepository.findAllWithDetails(pageable);
    }

    // ── BUSCAR POR ID ────────────────────────────────────────────────────────
    @Transactional(readOnly = true)
    public Event findById(Long id) {
        return eventRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Event", id));
    }

    // ── CREAR ────────────────────────────────────────────────────────────────
    public Event create(Event event, Long venueId, List<Long> categoryIds) {
        event.setId(null);
        event.setActive(true);

        // Asignar venue obligatorio
        Venues venue = venueRepository.findById(venueId)
                .orElseThrow(() -> new ResourceNotFoundException("Venue", venueId));
        event.setVenue(venue);

        // Asignar categorías seleccionadas
        if (categoryIds != null && !categoryIds.isEmpty()) {
            Set<Category> cats = new HashSet<>(categoryRepository.findAllById(categoryIds));
            event.setCategories(cats);
        }

        return eventRepository.save(event);
    }

    // ── ACTUALIZAR ───────────────────────────────────────────────────────────
    public Event update(Long id, Event updated, Long venueId, List<Long> categoryIds) {
        Event existing = findById(id);

        existing.setName(updated.getName());
        existing.setDescription(updated.getDescription());
        existing.setDate(updated.getDate());
        existing.setCapacity(updated.getCapacity());

        Venues venue = venueRepository.findById(venueId)
                .orElseThrow(() -> new ResourceNotFoundException("Venue", venueId));
        existing.setVenue(venue);

        if (categoryIds != null) {
            Set<Category> cats = new HashSet<>(categoryRepository.findAllById(categoryIds));
            existing.setCategories(cats);
        } else {
            existing.getCategories().clear();
        }

        return eventRepository.save(existing);
    }

    // ── SOFT DELETE ──────────────────────────────────────────────────────────

    public void delete(Long id) {
        Event existing = findById(id);
        existing.deactivate();          // active = false
        eventRepository.save(existing); // UPDATE events SET active=false WHERE id=?
    }
}