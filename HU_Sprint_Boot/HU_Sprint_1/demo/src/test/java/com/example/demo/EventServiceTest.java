package com.example.demo;

import com.example.demo.Models.Event;
import com.example.demo.Repositories.EventsRepository;
import com.example.demo.Services.EventService;
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
class EventServiceTest {

    @Mock
    private EventsRepository repository;

    @InjectMocks
    private EventService service;

    private Event validEvent;

    @BeforeEach
    void setUp() {
        validEvent = new Event(1L, "Java Conference", "A workshop to learn Spring Boot basics");
    }

    // ===================== findAll =====================

    @Test
    void findAll_ReturnsList_WhenEventsExist() {
        List<Event> events = Arrays.asList(validEvent, new Event(2L, "DevFest", "Annual developer festival"));
        when(repository.findAll()).thenReturn(events);

        List<Event> result = service.findAll();

        assertEquals(2, result.size());
        verify(repository, times(1)).findAll();
    }

    @Test
    void findAll_ReturnsEmptyList_WhenNoEvents() {
        when(repository.findAll()).thenReturn(Collections.emptyList());

        List<Event> result = service.findAll();

        assertTrue(result.isEmpty());
    }

    // ===================== findById =====================

    @Test
    void findById_ReturnsEvent_WhenIdExists() {
        when(repository.findById(1L)).thenReturn(validEvent);

        Event result = service.findById(1L);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("Java Conference", result.getName());
    }

    @Test
    void findById_ReturnsNull_WhenIdDoesNotExist() {
        when(repository.findById(99L)).thenReturn(null);

        Event result = service.findById(99L);

        assertNull(result);
    }

    // ===================== create =====================

    @Test
    void create_ReturnsEvent_WhenAllFieldsValid() {
        Event result = service.create(validEvent);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        verify(repository, times(1)).save(validEvent);
    }

    @Test
    void create_ThrowsException_WhenNameIsNull() {
        Event event = new Event(1L, null, "Some description");

        RuntimeException ex = assertThrows(RuntimeException.class, () -> service.create(event));
        assertEquals("El nombre es obligatorio", ex.getMessage());
        verify(repository, never()).save(any());
    }

    @Test
    void create_ThrowsException_WhenNameIsEmpty() {
        Event event = new Event(1L, "", "Some description");

        RuntimeException ex = assertThrows(RuntimeException.class, () -> service.create(event));
        assertEquals("El nombre es obligatorio", ex.getMessage());
    }

    @Test
    void create_ThrowsException_WhenIdIsNull() {
        Event event = new Event(null, "Java Conference", "Some description");

        RuntimeException ex = assertThrows(RuntimeException.class, () -> service.create(event));
        assertEquals("debe de contener un id Unico", ex.getMessage());
        verify(repository, never()).save(any());
    }

    @Test
    void create_ThrowsException_WhenDescriptionIsNull() {
        Event event = new Event(1L, "Java Conference", null);

        RuntimeException ex = assertThrows(RuntimeException.class, () -> service.create(event));
        assertEquals("La Descripcion es obligatorio", ex.getMessage());
        verify(repository, never()).save(any());
    }

    @Test
    void create_ThrowsException_WhenDescriptionIsEmpty() {
        Event event = new Event(1L, "Java Conference", "");

        RuntimeException ex = assertThrows(RuntimeException.class, () -> service.create(event));
        assertEquals("La Descripcion es obligatorio", ex.getMessage());
    }

    // ===================== delete =====================

    @Test
    void delete_ReturnsTrue_WhenEventExists() {
        when(repository.delete(1L)).thenReturn(true);

        boolean result = service.delete(1L);

        assertTrue(result);
        verify(repository, times(1)).delete(1L);
    }

    @Test
    void delete_ReturnsFalse_WhenEventDoesNotExist() {
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
        Event updated = new Event(1L, null, "New description");

        RuntimeException ex = assertThrows(RuntimeException.class, () -> service.update(1L, updated));
        assertEquals("El nombre es obligatorio para actualizar", ex.getMessage());
    }

    @Test
    void update_ThrowsException_WhenNameIsEmpty() {
        Event updated = new Event(1L, "", "New description");

        RuntimeException ex = assertThrows(RuntimeException.class, () -> service.update(1L, updated));
        assertEquals("El nombre es obligatorio para actualizar", ex.getMessage());
    }

    @Test
    void update_ThrowsException_WhenIdIsNull() {
        Event updated = new Event(null, "Updated Name", "New description");

        RuntimeException ex = assertThrows(RuntimeException.class, () -> service.update(1L, updated));
        assertEquals("debe de contener un id Unico para actualizar", ex.getMessage());
    }
}