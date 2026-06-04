package com.example.demo.web;

import com.example.demo.Models.Event;
import com.example.demo.Models.Category;
import com.example.demo.Repositories.CategoryRepository;
import com.example.demo.Services.EventService;
import com.example.demo.Services.VenuesService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/admin/events")
public class EventsControllerUI {

    private final EventService eventService;
    private final VenuesService venuesService;
    private final CategoryRepository categoryRepository;

    public EventsControllerUI(EventService eventService,
                              VenuesService venuesService,
                              CategoryRepository categoryRepository) {
        this.eventService = eventService;
        this.venuesService = venuesService;
        this.categoryRepository = categoryRepository;
    }

    // ── LISTADO CON FILTROS Y PAGINACIÓN Slice ────────────────────────────────
    @GetMapping
    public String listEvents(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String city,
            @RequestParam(required = false) String category,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            Model model) {

        Slice<Event> slice = eventService.search(name, city, category, null, null, null, page, size);

        model.addAttribute("slice", slice);
        model.addAttribute("events", slice.getContent());
        model.addAttribute("name", name);
        model.addAttribute("city", city);
        model.addAttribute("category", category);
        model.addAttribute("page", page);

        return "admin/events/list";
    }

    // ── FORMULARIO DE CREACIÓN ────────────────────────────────────────────────
    @GetMapping("/new")
    public String showCreateForm(Model model) {
        List<Category> categories = categoryRepository.findAllByOrderByNameAsc();

        System.out.println("DEBUG: Categorías encontradas para el formulario: " + categories.size());

        model.addAttribute("event", new Event());
        model.addAttribute("venues", venuesService.findAllForSelect());
        model.addAttribute("categories", categories);

        return "admin/events/form";
    }

    // ── GUARDAR NUEVO EVENTO ──────────────────────────────────────────────────
    @PostMapping
    public String createEvent(
            @Valid @ModelAttribute("event") Event event,
            BindingResult result,
            @RequestParam Long venueId,
            @RequestParam(required = false) List<Long> categoryIds,
            Model model,
            RedirectAttributes redirectAttrs) {

        if (result.hasErrors()) {
            model.addAttribute("venues", venuesService.findAllForSelect());
            model.addAttribute("categories", categoryRepository.findAllByOrderByNameAsc());
            return "admin/events/form";
        }
        eventService.create(event, venueId, categoryIds);
        redirectAttrs.addFlashAttribute("successMessage", "Evento creado exitosamente.");
        return "redirect:/admin/events";
    }

    // ── FORMULARIO DE EDICIÓN ─────────────────────────────────────────────────
    @GetMapping("/{id}/edit")
    public String showEditForm(@PathVariable Long id, Model model) {
        Event event = eventService.findById(id);
        model.addAttribute("event", event);
        model.addAttribute("venues", venuesService.findAllForSelect());
        model.addAttribute("categories", categoryRepository.findAllByOrderByNameAsc());
        return "admin/events/form";
    }

    // ── ACTUALIZAR EVENTO ─────────────────────────────────────────────────────
    @PostMapping("/{id}/edit")
    public String updateEvent(
            @PathVariable Long id,
            @Valid @ModelAttribute("event") Event updatedEvent,
            BindingResult result,
            @RequestParam Long venueId,
            @RequestParam(required = false) List<Long> categoryIds,
            Model model,
            RedirectAttributes redirectAttrs) {

        if (result.hasErrors()) {
            model.addAttribute("venues", venuesService.findAllForSelect());
            model.addAttribute("categories", categoryRepository.findAllByOrderByNameAsc());
            return "admin/events/form";
        }
        eventService.update(id, updatedEvent, venueId, categoryIds);
        redirectAttrs.addFlashAttribute("successMessage", "Evento actualizado correctamente.");
        return "redirect:/admin/events";
    }

    // ── SOFT DELETE ───────────────────────────────────────────────────────────
    @PostMapping("/{id}/delete")
    public String deleteEvent(@PathVariable Long id, RedirectAttributes redirectAttrs) {
        eventService.delete(id);
        redirectAttrs.addFlashAttribute("successMessage", "Evento desactivado.");
        return "redirect:/admin/events";
    }
}