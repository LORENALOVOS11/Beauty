package com.beautysalon.BeautySalon.controller;

import com.beautysalon.BeautySalon.model.Servicio;
import com.beautysalon.BeautySalon.service.ServicioService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.stream.Collectors;

@Controller
public class ServicioController {

    private final ServicioService servicioService;

    public ServicioController(ServicioService servicioService) {
        this.servicioService = servicioService;
    }

    // ==========================================
    // PÁGINA PRINCIPAL DE SERVICIOS
    // ==========================================

    @GetMapping("/servicios")
    public String servicios(Model model) {

        model.addAttribute("servicios", servicioService.listarServicios());

        model.addAttribute("servicio", new Servicio());

        return "servicios";
    }


    // ==========================================
    // SERVICIOS POR CATEGORÍA
    // ==========================================

    @GetMapping("/servicios/categoria")
    public String serviciosPorCategoria(
            @RequestParam(name = "categoria", required = false) String categoria,
            Model model) {

        if (categoria == null || categoria.trim().isEmpty()) {
            return "redirect:/servicios";
        }

        List<Servicio> servicios = servicioService.listarServicios()
                .stream()
                .filter(servicio ->
                        servicio.getCategoria() != null
                        && servicio.getCategoria().equalsIgnoreCase(categoria))
                .collect(Collectors.toList());

        model.addAttribute("servicios", servicios);
        model.addAttribute("categoria", categoria);

        return "servicios-categoria";
    }


    // ==========================================
    // GUARDAR SERVICIO
    // ==========================================

    @PostMapping("/servicios/guardar")
    public String guardarServicio(
            @ModelAttribute Servicio servicio) {

        servicioService.guardarServicio(servicio);

        return "redirect:/servicios";
    }


    // ==========================================
    // ELIMINAR SERVICIO
    // ==========================================

    @GetMapping("/servicios/eliminar")
    public String eliminarServicio(
            @RequestParam Long id) {

        servicioService.eliminarServicio(id);

        return "redirect:/servicios";
    }
}