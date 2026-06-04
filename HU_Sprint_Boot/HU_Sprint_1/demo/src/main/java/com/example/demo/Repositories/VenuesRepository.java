package com.example.demo.Repositories;

import com.example.demo.Models.Venues;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VenuesRepository extends JpaRepository<Venues, Long> {

    // Búsqueda por nombre (case-insensitive)
    Page<Venues> findByNameContainingIgnoreCase(String name, Pageable pageable);

    // Búsqueda por ciudad (case-insensitive)
    Page<Venues> findByCityContainingIgnoreCase(String city, Pageable pageable);

    // Lista completa sin paginación — para desplegables en formularios
    List<Venues> findAllByOrderByNameAsc();
}