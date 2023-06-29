package com.asistencias.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.asistencias.models.entity.Role;


public interface RoleRepository extends JpaRepository<Role, Long>{
    Optional<Role> findByNameRole(String name);
}
