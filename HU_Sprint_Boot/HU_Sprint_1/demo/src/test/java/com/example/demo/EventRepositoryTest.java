package com.example.demo;

import com.example.demo.Models.Event;
import com.example.demo.Repositories.EventsRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
// @DataJpaTest configura automáticamente H2 en memoria, escanea solo entidades y repositorios
class EventRepositoryTest {

    @Autowired
    private EventsRepository repository;

    @BeforeEach
    void setUp() {
        repository.deleteAll();

        // Insertamos 5 eventos de prueba
        repository.save(new Event(null, "Java Conference", "Annual Java event"));
        repository.save(new Event(null, "Spring Boot Workshop", "Hands-on Spring Boot session"));
        repository.save(new Event(null, "Docker Meetup", "Container technologies"));
        repository.save(new Event(null, "React Summit", "Frontend JavaScript conference"));
        repository.save(new Event(null, "Java Advanced", "Deep dive into JVM internals"));
    }

    @Test
    @DisplayName("TASK 1 — Guardar y recuperar una entidad por ID")
    void shouldSaveAndFindById() {
        Event saved = repository.save(new Event(null, "Nuevo Evento", "Descripción de prueba"));

        Optional<Event> found = repository.findById(saved.getId());

        assertThat(found).isPresent();
        assertThat(found.get().getName()).isEqualTo("Nuevo Evento");
        assertThat(found.get().getId()).isNotNull();
    }

    @Test
    @DisplayName("TASK 1 — Derived Query: findByNameContainingIgnoreCase")
    void shouldFindByNameContaining() {
        Pageable pageable = PageRequest.of(0, 10);

        Page<Event> result = repository.findByNameContainingIgnoreCase("java", pageable);

        // Espera "Java Conference" y "Java Advanced"
        assertThat(result.getTotalElements()).isEqualTo(2);
        assertThat(result.getContent())
                .extracting(Event::getName)
                .allMatch(name -> name.toLowerCase().contains("java"));
    }

    @Test
    @DisplayName("TASK 2 — Eliminar por ID deja el registro inexistente")
    void shouldDeleteById() {
        Event saved = repository.save(new Event(null, "Evento a eliminar", "Descripción"));

        repository.deleteById(saved.getId());

        Optional<Event> found = repository.findById(saved.getId());
        assertThat(found).isEmpty();
    }

    @Test
    @DisplayName("TASK 2 — existsById retorna false para un ID inexistente")
    void shouldReturnFalseForNonExistentId() {
        boolean exists = repository.existsById(9999L);
        assertThat(exists).isFalse();
    }

    @Test
    @DisplayName("TASK 3 — Paginación: página 0 tamaño 2 retorna exactamente 2 elementos")
    void shouldReturnCorrectPageSize() {
        Pageable pageable = PageRequest.of(0, 2);

        Page<Event> page = repository.findAll(pageable);

        assertThat(page.getContent()).hasSize(2);
        assertThat(page.getTotalElements()).isEqualTo(5);
        assertThat(page.getTotalPages()).isEqualTo(3);
    }

    @Test
    @DisplayName("TASK 3 — Ordenamiento ascendente por nombre")
    void shouldReturnSortedByName() {
        Pageable pageable = PageRequest.of(0, 10, Sort.by("name").ascending());

        Page<Event> page = repository.findAll(pageable);

        assertThat(page.getContent().get(0).getName()).isEqualTo("Docker Meetup");
    }

    @Test
    @DisplayName("TASK 3 — Escenario de volumen: 50 eventos, página 0 tamaño 5")
    void shouldHandleVolumeWithPagination() {
        repository.deleteAll();
        for (int i = 1; i <= 50; i++) {
            repository.save(new Event(null, "Event " + i, "Description " + i));
        }

        Pageable pageable = PageRequest.of(0, 5);
        Page<Event> page = repository.findAll(pageable);

        assertThat(page.getContent()).hasSize(5);
        assertThat(page.getTotalElements()).isEqualTo(50);
        assertThat(page.getTotalPages()).isEqualTo(10);
    }
}