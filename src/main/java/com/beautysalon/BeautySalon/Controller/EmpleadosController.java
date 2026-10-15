package com.beautysalon.BeautySalon.controller;

import com.beautysalon.BeautySalon.model.Empleado;
import com.beautysalon.BeautySalon.service.EmpleadoService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class EmpleadosController {

    private final EmpleadoService empleadoService;

    public EmpleadosController(EmpleadoService empleadoService) {
        this.empleadoService = empleadoService;
    }

    @GetMapping("/empleados")
    public String empleados(Model model) {

        model.addAttribute("empleados", empleadoService.listarEmpleados());
        model.addAttribute("empleado", new Empleado());

        return "empleados";
    }

    @PostMapping("/empleados/guardar")
    public String guardarEmpleado(
            @ModelAttribute Empleado empleado) {

        empleadoService.guardarEmpleado(empleado);

        return "redirect:/empleados";
    }

    @GetMapping("/empleados/eliminar")
    public String eliminarEmpleado(
            @RequestParam Long id) {

        empleadoService.eliminarEmpleado(id);

        return "redirect:/empleados";
    }
}