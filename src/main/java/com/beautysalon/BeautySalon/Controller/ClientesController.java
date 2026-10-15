package com.beautysalon.BeautySalon.controller;

import com.beautysalon.BeautySalon.model.Cliente;
import com.beautysalon.BeautySalon.service.ClienteService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class ClientesController {

    private final ClienteService clienteService;

    public ClientesController(ClienteService clienteService) {
        this.clienteService = clienteService;
    }

    @GetMapping("/clientes")
    public String clientes(Model model) {

        model.addAttribute("clientes", clienteService.listarClientes());
        model.addAttribute("cliente", new Cliente());

        return "clientes";
    }

    @PostMapping("/clientes/guardar")
    public String guardarCliente(
            @ModelAttribute Cliente cliente) {

        clienteService.guardarCliente(cliente);

        return "redirect:/clientes";
    }

    @GetMapping("/clientes/eliminar")
    public String eliminarCliente(
            @RequestParam Long id) {

        clienteService.eliminarCliente(id);

        return "redirect:/clientes";
    }
}