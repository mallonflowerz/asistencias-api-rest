package com.asistencias.repositories;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.asistencias.models.entity.Movimientos;
import com.asistencias.models.entity.Usuario;

public interface MovimientosRepository extends JpaRepository<Movimientos, Long>{
    List<Movimientos> findAllByOrderByFechaHoraDesc();
    Optional<Movimientos> findByUsuarios(Usuario usuarios);
    Optional<List<Movimientos>> findAllByUsuarios(Usuario usuarios);
    boolean existsByUsuariosAndEntradaAndFechaHoraAfter(Usuario usuario, boolean esEntrada, LocalDateTime fechaHora);
    Optional<Movimientos> findTopByUsuariosOrderByIdDesc(Usuario user);
}
