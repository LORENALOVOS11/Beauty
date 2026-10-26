package com.beautysalon.BeautySalon.controller;

import com.beautysalon.BeautySalon.model.Producto;
import com.beautysalon.BeautySalon.repository.ProductoRepository;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class ProductoController {

    private final ProductoRepository productoRepository;

    public ProductoController(ProductoRepository productoRepository) {
        this.productoRepository = productoRepository;
    }

    // Mostrar la página de productos
    @GetMapping("/productos")
    public String mostrarProductos(Model model) {
        model.addAttribute("producto", new Producto());
        model.addAttribute("productos", productoRepository.findAll());

        return "productos";
    }

    // Guardar un producto
    @PostMapping("/productos/guardar")
    public String guardarProducto(
            @ModelAttribute Producto producto,
            @RequestParam(required = false) String nombre,
            RedirectAttributes redirectAttributes) {

        if (producto.getNombre() == null
                || producto.getNombre().trim().isEmpty()
                || producto.getPrecio() == null
                || producto.getCantidad() == null
                || producto.getPrecio() < 0
                || producto.getCantidad() < 0) {

            redirectAttributes.addFlashAttribute(
                    "error", "Completa los campos correctamente.");

            return "redirect:/productos";
        }

        producto.setNombre(producto.getNombre().trim());

        if (producto.getDescripcion() != null) {
            producto.setDescripcion(producto.getDescripcion().trim());
        }

        productoRepository.save(producto);

        redirectAttributes.addFlashAttribute(
                "mensaje", "¡Producto guardado correctamente!");

        return "redirect:/productos";
    }
}