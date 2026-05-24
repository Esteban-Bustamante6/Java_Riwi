package com.example.demo.web;

import com.example.demo.Models.Event;
import com.example.demo.Models.Venues;
import com.example.demo.Services.EventService;
import com.example.demo.Services.VenuesService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;


@Controller
@RequestMapping("/admin/venues")

public class VenuesControllerUI {



    private final VenuesService service;

    public VenuesControllerUI(VenuesService service) {
        this.service = service;
    }


    @GetMapping
    public String listVenues(Model model) {
        List<Venues> venues = service.findAll(
                org.springframework.data.domain.Pageable.unpaged()
        ).getContent();

        model.addAttribute("venues", venues);
        return "admin/venues/list"; // → templates/admin/venues/list.html
    }


    @GetMapping("/new")
    public String showCreateForm(Model model) {
        model.addAttribute("venue", new Venues());
        return "admin/venues/form";
    }


    @PostMapping
    public String createVenue(@Valid @ModelAttribute("venue") Venues venue,
                              BindingResult result,
                              RedirectAttributes redirectAttrs) {
        if (result.hasErrors()) {
            return "admin/venues/form";
        }
        service.create(venue);
        redirectAttrs.addFlashAttribute("successMessage", "Lugar registrado exitosamente.");
        return "redirect:/admin/venues";
    }


    @GetMapping("/{id}/edit")
    public String showEditForm(@PathVariable Long id, Model model) {
        Venues venue = service.findById(id);
        model.addAttribute("venue", venue);
        return "admin/venues/form";
    }


    @PostMapping("/{id}/edit")
    public String updateVenue(@PathVariable Long id,
                              @Valid @ModelAttribute("venue") Venues updatedVenue,
                              BindingResult result,
                              RedirectAttributes redirectAttrs) {
        if (result.hasErrors()) {
            return "admin/venues/form";
        }
        service.update(id, updatedVenue);
        redirectAttrs.addFlashAttribute("successMessage", "Lugar actualizado correctamente.");
        return "redirect:/admin/venues";
    }


    @PostMapping("/{id}/delete")
    public String deleteVenue(@PathVariable Long id, RedirectAttributes redirectAttrs) {
        service.delete(id);
        redirectAttrs.addFlashAttribute("successMessage", "Lugar eliminado correctamente.");
        return "redirect:/admin/venues";
    }


}