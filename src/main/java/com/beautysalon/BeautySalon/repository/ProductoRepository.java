package com.beautysalon.BeautySalon.repository;

import com.beautysalon.BeautySalon.model.Producto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductoRepository
        extends JpaRepository<Producto, Long> {

}