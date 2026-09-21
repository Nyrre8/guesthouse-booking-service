package com.example.guesthousebookingsystem.controllers;

import com.example.guesthousebookingsystem.dtos.RoomDTO;
import com.example.guesthousebookingsystem.services.RoomService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.ui.Model;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/rooms")
public class RoomController {

    private final RoomService roomService;

    public RoomController(RoomService roomService) {
        this.roomService = roomService;
    }

    @GetMapping
    public String listRooms(Model model) {
        model.addAttribute("rooms", roomService.getAllRooms());
        return "rooms/list";
    }

    @GetMapping("/new")
    public String showCreateForm(Model model) {
        model.addAttribute("room", new RoomDTO());
        return "rooms/form";
    }

    @PostMapping("/save")
    public String saveRoom(@ModelAttribute RoomDTO roomDTO, RedirectAttributes redirectAttributes) {
        roomService.save(roomDTO);
        redirectAttributes.addFlashAttribute("successMessage", "Rummet sparades!");
        return "redirect:/rooms";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        model.addAttribute("room", roomService.getById(id));
        return "rooms/form";
    }

    @PostMapping("/delete/{id}")
    public String deleteRoom(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            roomService.delete(id);
            redirectAttributes.addFlashAttribute("successMessage", "Rummet togs bort!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Kan inte ta bort rummet!");
        }
        return "redirect:/rooms";
    }
}