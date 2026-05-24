package com.example.demo.Repositories;

import com.example.demo.Models.Venues;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VenuesRepository extends JpaRepository<Venues, Long> {

    // Derived Query: busca venues cuyo nombre contenga el texto (case-insensitive)
    Page<Venues> findByNameContainingIgnoreCase(String name, Pageable pageable);

    // Derived Query: busca venues por dirección
    Page<Venues> findByAddressContainingIgnoreCase(String address, Pageable pageable);
}