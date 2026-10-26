
package com.beautysalon.BeautySalon.repository;

import com.beautysalon.BeautySalon.model.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Long> {

    List<Cliente> findByNombreContainingIgnoreCaseOrApellidoContainingIgnoreCase(
            String nombre,
            String apellido
    );

    List<Cliente> findByNombreContainingIgnoreCaseAndApellidoContainingIgnoreCase(
            String nombre,
            String apellido
    );
}
