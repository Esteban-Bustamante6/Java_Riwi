package com.example.demo;

import com.example.demo.Models.Venues;
import com.example.demo.Repositories.VenuesRepository;
import com.example.demo.Services.VenuesService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class VenuesServiceTest {

    @Mock
    private VenuesRepository repository;

    @InjectMocks
    private VenuesService service;

    private Venues validVenue;

    @BeforeEach
    void setUp() {
        validVenue = new Venues(1L, "Boston Park", "Parque de Boston Cra. 38 #54-97, Medellín");
    }

    // ===================== findAll =====================

    @Test
    void findAll_ReturnsList_WhenVenuesExist() {
        List<Venues> venues = Arrays.asList(validVenue, new Venues(2L, "Estadio Atanasio", "Cra. 74, El Velódromo, Medellín"));
        when(repository.findAll()).thenReturn(venues);

        List<Venues> result = service.findAll();

        assertEquals(2, result.size());
        verify(repository, times(1)).findAll();
    }

    @Test
    void findAll_ReturnsEmptyList_WhenNoVenues() {
        when(repository.findAll()).thenReturn(Collections.emptyList());

        List<Venues> result = service.findAll();

        assertTrue(result.isEmpty());
    }

    // ===================== findById =====================

    @Test
    void findById_ReturnsVenue_WhenIdExists() {
        when(repository.findById(1L)).thenReturn(validVenue);

        Venues result = service.findById(1L);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("Boston Park", result.getName());
    }

    @Test
    void findById_ReturnsNull_WhenIdDoesNotExist() {
        when(repository.findById(99L)).thenReturn(null);

        Venues result = service.findById(99L);

        assertNull(result);
    }

    // ===================== create =====================

    @Test
    void create_ReturnsVenue_WhenAllFieldsValid() {
        Venues result = service.create(validVenue);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        verify(repository, times(1)).save(validVenue);
    }

    @Test
    void create_ThrowsException_WhenNameIsNull() {
        Venues venue = new Venues(1L, null, "Cra. 38 #54-97, Medellín");

        RuntimeException ex = assertThrows(RuntimeException.class, () -> service.create(venue));
        assertEquals("El nombre es obligatorio", ex.getMessage());
        verify(repository, never()).save(any());
    }

    @Test
    void create_ThrowsException_WhenNameIsEmpty() {
        Venues venue = new Venues(1L, "", "Cra. 38 #54-97, Medellín");

        RuntimeException ex = assertThrows(RuntimeException.class, () -> service.create(venue));
        assertEquals("El nombre es obligatorio", ex.getMessage());
    }

    @Test
    void create_ThrowsException_WhenIdIsNull() {
        Venues venue = new Venues(null, "Boston Park", "Cra. 38 #54-97, Medellín");

        RuntimeException ex = assertThrows(RuntimeException.class, () -> service.create(venue));
        assertEquals("debe de contener un id Unico", ex.getMessage());
        verify(repository, never()).save(any());
    }

    @Test
    void create_ThrowsException_WhenAddressIsNull() {
        Venues venue = new Venues(1L, "Boston Park", null);

        RuntimeException ex = assertThrows(RuntimeException.class, () -> service.create(venue));
        assertEquals("La Descripcion es obligatorio", ex.getMessage());
        verify(repository, never()).save(any());
    }

    @Test
    void create_ThrowsException_WhenAddressIsEmpty() {
        Venues venue = new Venues(1L, "Boston Park", "");

        RuntimeException ex = assertThrows(RuntimeException.class, () -> service.create(venue));
        assertEquals("La Descripcion es obligatorio", ex.getMessage());
    }

    // ===================== delete =====================

    @Test
    void delete_ReturnsTrue_WhenVenueExists() {
        when(repository.delete(1L)).thenReturn(true);

        boolean result = service.delete(1L);

        assertTrue(result);
        verify(repository, times(1)).delete(1L);
    }

    @Test
    void delete_ReturnsFalse_WhenVenueDoesNotExist() {
        when(repository.delete(99L)).thenReturn(false);

        boolean result = service.delete(99L);

        assertFalse(result);
    }

    @Test
    void delete_ThrowsException_WhenIdIsNull() {
        RuntimeException ex = assertThrows(RuntimeException.class, () -> service.delete(null));
        assertEquals("debe de contener un id Unico", ex.getMessage());
        verify(repository, never()).delete(any());
    }

    // ===================== update =====================

    @Test
    void update_ThrowsException_WhenNameIsNull() {
        Venues updated = new Venues(1L, null, "New address");

        RuntimeException ex = assertThrows(RuntimeException.class, () -> service.update(1L, updated));
        assertEquals("El nombre es obligatorio para actualizar", ex.getMessage());
    }

    @Test
    void update_ThrowsException_WhenNameIsEmpty() {
        Venues updated = new Venues(1L, "", "New address");

        RuntimeException ex = assertThrows(RuntimeException.class, () -> service.update(1L, updated));
        assertEquals("El nombre es obligatorio para actualizar", ex.getMessage());
    }

    @Test
    void update_ThrowsException_WhenIdIsNull() {
        Venues updated = new Venues(null, "Boston Park", "New address");

        RuntimeException ex = assertThrows(RuntimeException.class, () -> service.update(1L, updated));
        assertEquals("debe de contener un id Unico para actualizar", ex.getMessage());
    }
}