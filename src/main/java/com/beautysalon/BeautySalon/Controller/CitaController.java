package com.beautysalon.BeautySalon.controller;

import com.beautysalon.BeautySalon.model.Cita;
import com.beautysalon.BeautySalon.model.Cliente;
import com.beautysalon.BeautySalon.model.Empleado;
import com.beautysalon.BeautySalon.model.Servicio;

import com.beautysalon.BeautySalon.service.CitaService;
import com.beautysalon.BeautySalon.service.ClienteService;
import com.beautysalon.BeautySalon.service.EmpleadoService;
import com.beautysalon.BeautySalon.service.ServicioService;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class CitaController {

    private final CitaService citaService;
    private final ClienteService clienteService;
    private final EmpleadoService empleadoService;
    private final ServicioService servicioService;

    public CitaController(
            CitaService citaService,
            ClienteService clienteService,
            EmpleadoService empleadoService,
            ServicioService servicioService) {

        this.citaService = citaService;
        this.clienteService = clienteService;
        this.empleadoService = empleadoService;
        this.servicioService = servicioService;
    }

    // ==============================
    // MOSTRAR FORMULARIO DE CITAS
    // ==============================
    @GetMapping("/citas")
    public String citas(
            @RequestParam(name = "servicio", required = false) Long servicioId,
            Model model) {

        Cita cita = new Cita();

        // Si se seleccionó un servicio desde otra página
        if (servicioId != null) {

            servicioService.buscarPorId(servicioId)
                    .ifPresent(cita::setServicio);
        }

        model.addAttribute("cita", cita);

        model.addAttribute("citas",
                citaService.listarCitas());

        model.addAttribute("clientes",
                clienteService.listarClientes());

        model.addAttribute("empleados",
                empleadoService.listarEmpleados());

        model.addAttribute("servicios",
                servicioService.listarServicios());

        return "citas";
    }

    // ==============================
    // GUARDAR CITA
    // ==============================
    @PostMapping("/citas/guardar")
    public String guardarCita(
            @RequestParam Long clienteId,
            @RequestParam Long servicioId,
            @RequestParam Long empleadoId,
            @ModelAttribute Cita cita) {

        // Buscar cliente
        Cliente cliente = clienteService.buscarPorId(clienteId)
                .orElseThrow(() ->
                        new RuntimeException("Cliente no encontrado"));

        // Buscar servicio
        Servicio servicio = servicioService.buscarPorId(servicioId)
                .orElseThrow(() ->
                        new RuntimeException("Servicio no encontrado"));

        // Buscar empleado
        Empleado empleado = empleadoService.buscarPorId(empleadoId)
                .orElseThrow(() ->
                        new RuntimeException("Empleado no encontrado"));

        // Asignar las relaciones
        cita.setCliente(cliente);
        cita.setServicio(servicio);
        cita.setEmpleado(empleado);

        // Estado por defecto
        if (cita.getEstado() == null ||
                cita.getEstado().trim().isEmpty()) {

            cita.setEstado("Pendiente");
        }

        // Guardar
        citaService.guardarCita(cita);

        return "redirect:/citas";
    }

    // ==============================
    // ELIMINAR CITA
    // ==============================
    @GetMapping("/citas/eliminar")
    public String eliminarCita(
            @RequestParam Long id) {

        citaService.eliminarCita(id);

        return "redirect:/citas";
    }
}