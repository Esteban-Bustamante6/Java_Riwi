package com.example.demo.Repositories;

import com.example.demo.Models.Event;
import com.example.demo.Dto.EventSummaryDTO;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

@Repository
public interface EventsRepository extends JpaRepository<Event, Long> {


    @Query("""
            SELECT new com.example.demo.Dto.EventSummaryDTO(
                e.name, e.date, e.venue.name, e.venue.city
            )
            FROM Event e
            JOIN e.venue v
            ORDER BY e.date DESC
            """)
    Slice<EventSummaryDTO> findAllSummaries(Pageable pageable);

    // LISTADO GENERAL CON DETALLE
    // CORRECCION HHH90003004:
    // Antes: @EntityGraph(categories) + Slice -> Hibernate ignoraba el LIMIT/OFFSET
    //        y cargaba TODOS los registros en memoria para paginar despues.
    // Ahora: solo JOIN FETCH venue (relacion *-to-One, segura con paginacion).
    //        Las categorias las carga @BatchSize(25) en Event.java en lotes
    //        separados: SELECT * FROM categories WHERE id IN (id1..id25).
    //        Paginacion SQL correcta + sin warning.
    @Query("SELECT e FROM Event e JOIN FETCH e.venue v ORDER BY e.date DESC")
    Slice<Event> findAllWithDetails(Pageable pageable);


    @Query("""
            SELECT e FROM Event e
            JOIN FETCH e.venue v
            WHERE LOWER(v.city) LIKE LOWER(CONCAT('%', :city, '%'))
            ORDER BY e.date DESC
            """)
    Slice<Event> findByCityContaining(@Param("city") String city, Pageable pageable);

    @Query("""
            SELECT DISTINCT e FROM Event e
            JOIN FETCH e.venue v
            JOIN e.categories c
            WHERE LOWER(c.name) LIKE LOWER(CONCAT('%', :category, '%'))
            ORDER BY e.date DESC
            """)
    Slice<Event> findByCategoryName(@Param("category") String category, Pageable pageable);

    @Query("""
            SELECT e FROM Event e
            JOIN FETCH e.venue v
            WHERE LOWER(e.name) LIKE LOWER(CONCAT('%', :name, '%'))
            ORDER BY e.date DESC
            """)
    Slice<Event> findByNameContaining(@Param("name") String name, Pageable pageable);

    // BUSQUEDA POR RANGO DE FECHAS
    @Query("""
            SELECT e FROM Event e
            JOIN FETCH e.venue v
            WHERE e.date BETWEEN :from AND :to
            ORDER BY e.date DESC
            """)
    Slice<Event> findByDateBetween(
            @Param("from") LocalDateTime from,
            @Param("to") LocalDateTime to,
            Pageable pageable);

    // BUSQUEDA POR CAPACIDAD MINIMA
    @Query("""
            SELECT e FROM Event e
            JOIN FETCH e.venue v
            WHERE e.capacity >= :capacity
            ORDER BY e.date DESC
            """)
    Slice<Event> findByCapacityGreaterThanEqual(
            @Param("capacity") Integer capacity,
            Pageable pageable);
}
