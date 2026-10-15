package com.beautysalon.BeautySalon.service;

import com.beautysalon.BeautySalon.model.Cita;
import com.beautysalon.BeautySalon.repository.CitaRepository;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CitaService {

    private final CitaRepository citaRepository;

    public CitaService(CitaRepository citaRepository) {
        this.citaRepository = citaRepository;
    }

    public List<Cita> listarCitas() {
        return citaRepository.findAll();
    }

    public Optional<Cita> buscarPorId(Long id) {
        return citaRepository.findById(id);
    }

    public Cita guardarCita(Cita cita) {
        return citaRepository.save(cita);
    }

    public void eliminarCita(Long id) {
        citaRepository.deleteById(id);
    }
}