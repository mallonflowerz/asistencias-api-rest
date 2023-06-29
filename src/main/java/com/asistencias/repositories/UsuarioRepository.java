package com.asistencias.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.asistencias.models.entity.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    Optional<Usuario> findByNumeroIdentificacion(String numeroIdentificacion);
    boolean existsByNumeroIdentificacion(String numeroIdentificacion);
}
