package com.example.demo.Repositories;

import com.example.demo.Models.Event;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EventsRepository extends JpaRepository<Event, Long> {

    // Derived Query: busca eventos cuyo nombre contenga el texto (case-insensitive)
    Page<Event> findByNameContainingIgnoreCase(String name, Pageable pageable);

    // Derived Query: busca eventos cuya descripción contenga el texto
    Page<Event> findByDescriptionContainingIgnoreCase(String description, Pageable pageable);
}