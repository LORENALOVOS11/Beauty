package com.beautysalon.BeautySalon.service;

import com.beautysalon.BeautySalon.model.Servicio;
import com.beautysalon.BeautySalon.repository.ServicioRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ServicioService {

    private final ServicioRepository servicioRepository;

    public ServicioService(ServicioRepository servicioRepository) {
        this.servicioRepository = servicioRepository;
    }

    public List<Servicio> listarServicios() {
        return servicioRepository.findAll();
    }

    public Optional<Servicio> buscarPorId(Long id) {
        return servicioRepository.findById(id);
    }

    public Servicio guardarServicio(Servicio servicio) {
        return servicioRepository.save(servicio);
    }

    public void eliminarServicio(Long id) {
        servicioRepository.deleteById(id);
    }
}