
package com.example.demo.web;

import com.example.demo.Models.Event;
import com.example.demo.Services.EventService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
        import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
@Controller
@RequestMapping("/admin/events")
public class EventsControllerUI {


    private final EventService service;

    public EventsControllerUI(EventService service) {
        this.service = service;
    }


    @GetMapping
    public String listEvents(Model model) {
        List<Event> events = service.findAll(
                org.springframework.data.domain.Pageable.unpaged()
        ).getContent();

        model.addAttribute("events", events);
        // th:if en la vista chequea si la lista está vacía para el mensaje amigable
        return "admin/events/list"; // → templates/admin/events/list.html
    }


    @GetMapping("/new")
    public String showCreateForm(Model model) {
        model.addAttribute("event", new Event());
        return "admin/events/form"; // → templates/admin/events/form.html
    }


    @PostMapping
    public String createEvent(@Valid @ModelAttribute("event") Event event,
                              BindingResult result,
                              RedirectAttributes redirectAttrs) {
        if (result.hasErrors()) {
            // Si hay errores de validación, volvemos al formulario con los mensajes
            return "admin/events/form";
        }
        service.create(event);
        // Mensaje flash que se muestra en la siguiente request (después del redirect)
        redirectAttrs.addFlashAttribute("successMessage", "Evento creado exitosamente.");
        return "redirect:/admin/events"; // POST-REDIRECT-GET
    }


    @GetMapping("/{id}/edit")
    public String showEditForm(@PathVariable Long id, Model model) {
        Event event = service.findById(id); // lanza 404 si no existe
        model.addAttribute("event", event);
        return "admin/events/form";
    }


    @PostMapping("/{id}/edit")
    public String updateEvent(@PathVariable Long id,
                              @Valid @ModelAttribute("event") Event updatedEvent,
                              BindingResult result,
                              RedirectAttributes redirectAttrs) {
        if (result.hasErrors()) {
            return "admin/events/form";
        }
        service.update(id, updatedEvent);
        redirectAttrs.addFlashAttribute("successMessage", "Evento actualizado correctamente.");
        return "redirect:/admin/events";
    }


    @PostMapping("/{id}/delete")
    public String deleteEvent(@PathVariable Long id, RedirectAttributes redirectAttrs) {
        service.delete(id); // lanza 404 si no existe
        redirectAttrs.addFlashAttribute("successMessage", "Evento eliminado correctamente.");
        return "redirect:/admin/events";
    }
}


