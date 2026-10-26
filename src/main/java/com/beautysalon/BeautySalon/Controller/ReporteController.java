
package com.beautysalon.BeautySalon.controller;

import com.beautysalon.BeautySalon.model.Cliente;
import com.beautysalon.BeautySalon.repository.ClienteRepository;

import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Element;
import com.lowagie.text.Font;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;

import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class ReporteController {

    private final ClienteRepository clienteRepository;

    public ReporteController(ClienteRepository clienteRepository) {
        this.clienteRepository = clienteRepository;
    }

    // Mostrar reportes y buscar clientes
    @GetMapping("/reportes")
    public String reportes(
            @RequestParam(required = false) String buscar,
            Model model) {

        List<Cliente> clientes = obtenerClientes(buscar);

        model.addAttribute("clientes", clientes);
        model.addAttribute("buscar", buscar == null ? "" : buscar);
        model.addAttribute("totalClientes", clienteRepository.count());

        return "reporte";
    }

    // Descargar reporte en PDF
    @GetMapping("/reportes/pdf")
    public void exportarPdf(
            @RequestParam(required = false) String buscar,
            HttpServletResponse response)
            throws IOException, DocumentException {

        // Obtener los clientes según el texto de búsqueda
        List<Cliente> clientes = obtenerClientes(buscar);

        response.setContentType("application/pdf");
        response.setHeader(
                "Content-Disposition",
                "attachment; filename=reporte-clientes.pdf");

        Document documento = new Document(PageSize.A4);

        PdfWriter.getInstance(documento, response.getOutputStream());

        documento.open();

        Font tituloFont = new Font(Font.HELVETICA, 18, Font.BOLD);
        Font encabezadoFont = new Font(Font.HELVETICA, 10, Font.BOLD);
        Font textoFont = new Font(Font.HELVETICA, 10, Font.NORMAL);

        Paragraph titulo = new Paragraph(
                "Reporte de clientes - BeautySalon",
                tituloFont);

        titulo.setAlignment(Element.ALIGN_CENTER);
        titulo.setSpacingAfter(15);
        documento.add(titulo);

        Paragraph resumen = new Paragraph(
                "Total de clientes en este reporte: " + clientes.size(),
                textoFont);

        resumen.setSpacingAfter(12);
        documento.add(resumen);

        PdfPTable tabla = new PdfPTable(5);
        tabla.setWidthPercentage(100);
        tabla.setWidths(new float[]{1, 3, 3, 3, 5});

        agregarEncabezado(tabla, "ID", encabezadoFont);
        agregarEncabezado(tabla, "Nombre", encabezadoFont);
        agregarEncabezado(tabla, "Apellido", encabezadoFont);
        agregarEncabezado(tabla, "Telefono", encabezadoFont);
        agregarEncabezado(tabla, "Correo", encabezadoFont);

        // Agregar los datos de cada cliente al PDF
        for (Cliente cliente : clientes) {

            tabla.addCell(new Phrase(
                    textoSeguro(cliente.getId()),
                    textoFont));

            tabla.addCell(new Phrase(
                    textoSeguro(cliente.getNombre()),
                    textoFont));

            tabla.addCell(new Phrase(
                    textoSeguro(cliente.getApellido()),
                    textoFont));

            tabla.addCell(new Phrase(
                    textoSeguro(cliente.getTelefono()),
                    textoFont));

            tabla.addCell(new Phrase(
                    textoSeguro(cliente.getCorreo()),
                    textoFont));
        }

        documento.add(tabla);
        documento.close();
    }

    // Buscar clientes por nombre y apellido
    private List<Cliente> obtenerClientes(String buscar) {

        if (buscar == null || buscar.trim().isEmpty()) {
            return clienteRepository.findAll();
        }

        String texto = buscar.trim().replaceAll("\\s+", " ");
        String[] partes = texto.split(" ", 2);

        if (partes.length == 2) {

            List<Cliente> encontrados = clienteRepository
                    .findByNombreContainingIgnoreCaseAndApellidoContainingIgnoreCase(
                            partes[0],
                            partes[1]);

            if (!encontrados.isEmpty()) {
                return encontrados;
            }
        }

        return clienteRepository
                .findByNombreContainingIgnoreCaseOrApellidoContainingIgnoreCase(
                        texto,
                        texto);
    }

    // Crear encabezados de la tabla
    private void agregarEncabezado(
            PdfPTable tabla,
            String texto,
            Font fuente) {

        PdfPCell celda = new PdfPCell(new Phrase(texto, fuente));
        celda.setHorizontalAlignment(Element.ALIGN_CENTER);
        celda.setPadding(7);

        tabla.addCell(celda);
    }

    // Evitar errores cuando un dato sea nulo
    private String textoSeguro(String texto) {
        return texto == null ? "" : texto;
    }

    // Convertir el ID a texto sin provocar errores
    private String textoSeguro(Long valor) {
        return valor == null ? "" : String.valueOf(valor);
    }
}