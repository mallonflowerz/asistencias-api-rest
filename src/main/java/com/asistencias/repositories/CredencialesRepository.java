package com.asistencias.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.asistencias.models.entity.Credenciales;

public interface CredencialesRepository extends JpaRepository<Credenciales, Long>{
    boolean existsByUsername(String username);
    Optional<Credenciales> findByUsername(String username);
}
