package com.example.demo.Services;

import com.example.demo.Models.Venues;
import com.example.demo.Repositories.VenuesRepository;
import com.example.demo.exceptions.ResourceNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class VenuesService {

    private final VenuesRepository repository;

    public VenuesService(VenuesRepository repository) {
        this.repository = repository;
    }

    @Transactional(readOnly = true)
    public Page<Venues> findAll(Pageable pageable) {
        return repository.findAll(pageable);
    }

    @Transactional(readOnly = true)
    public List<Venues> findAllForSelect() {
        return repository.findAllByOrderByNameAsc(); // para el <select> del formulario
    }

    @Transactional(readOnly = true)
    public Page<Venues> findByName(String name, Pageable pageable) {
        return repository.findByNameContainingIgnoreCase(name, pageable);
    }

    @Transactional(readOnly = true)
    public Venues findById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Venue", id));
    }

    public Venues create(Venues venue) {
        venue.setId(null);
        return repository.save(venue);
    }

    public Venues update(Long id, Venues updated) {
        Venues existing = findById(id);
        existing.setName(updated.getName());
        existing.setAddress(updated.getAddress());
        existing.setCity(updated.getCity()); // ← nuevo campo
        return repository.save(existing);
    }

    public void delete(Long id) {
        if (!repository.existsById(id)) {
            throw new ResourceNotFoundException("Venue", id);
        }
        repository.deleteById(id);
    }
}