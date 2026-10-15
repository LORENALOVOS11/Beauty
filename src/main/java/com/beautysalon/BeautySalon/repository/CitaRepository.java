package com.beautysalon.BeautySalon.repository;

import com.beautysalon.BeautySalon.model.Cita;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CitaRepository extends JpaRepository<Cita, Long> {

}