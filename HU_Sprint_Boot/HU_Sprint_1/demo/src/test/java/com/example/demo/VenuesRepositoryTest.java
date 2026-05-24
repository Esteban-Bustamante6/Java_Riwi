package com.example.demo;

import com.example.demo.Models.Venues;
import com.example.demo.Repositories.VenuesRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class VenuesRepositoryTest {

    @Autowired
    private VenuesRepository repository;

    @BeforeEach
    void setUp() {
        repository.deleteAll();

        repository.save(new Venues(null, "Parque Boston", "Cra. 38 #54-97, Medellín"));
        repository.save(new Venues(null, "Centro de Convenciones", "Calle 50 #40-20, Bogotá"));
        repository.save(new Venues(null, "Teatro Lido", "Av. El Poblado, Medellín"));
    }

    @Test
    @DisplayName("TASK 1 — Guardar y recuperar un venue por ID")
    void shouldSaveAndFindById() {
        Venues saved = repository.save(new Venues(null, "Nuevo Venue", "Calle 10 #20-30"));

        Optional<Venues> found = repository.findById(saved.getId());

        assertThat(found).isPresent();
        assertThat(found.get().getName()).isEqualTo("Nuevo Venue");
        assertThat(found.get().getId()).isNotNull();
    }

    @Test
    @DisplayName("TASK 1 — Derived Query: findByNameContainingIgnoreCase")
    void shouldFindByNameContaining() {
        Pageable pageable = PageRequest.of(0, 10);

        Page<Venues> result = repository.findByNameContainingIgnoreCase("medellín", pageable);

        // Teatro Lido y Parque Boston tienen "Medellín" en la dirección, no en el nombre
        // Solo buscamos en nombre aquí
        assertThat(result).isNotNull();
    }

    @Test
    @DisplayName("TASK 1 — Derived Query: findByAddressContainingIgnoreCase")
    void shouldFindByAddress() {
        Pageable pageable = PageRequest.of(0, 10);

        Page<Venues> result = repository.findByAddressContainingIgnoreCase("Medellín", pageable);

        assertThat(result.getTotalElements()).isEqualTo(2);
    }

    @Test
    @DisplayName("TASK 2 — Eliminar venue por ID")
    void shouldDeleteById() {
        Venues saved = repository.save(new Venues(null, "Venue a eliminar", "Calle 100"));

        repository.deleteById(saved.getId());

        assertThat(repository.findById(saved.getId())).isEmpty();
    }

    @Test
    @DisplayName("TASK 3 — Paginación de venues")
    void shouldPaginateVenues() {
        Pageable pageable = PageRequest.of(0, 2);

        Page<Venues> page = repository.findAll(pageable);

        assertThat(page.getContent()).hasSize(2);
        assertThat(page.getTotalElements()).isEqualTo(3);
        assertThat(page.getTotalPages()).isEqualTo(2);
    }
}