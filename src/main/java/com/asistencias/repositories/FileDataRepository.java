package com.asistencias.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.asistencias.models.entity.FileData;
import com.asistencias.models.entity.Usuario;

public interface FileDataRepository extends JpaRepository<FileData, Long>{
    
    FileData findByUsuarioId(Long userId);

    Optional<FileData> findByUsuario(Usuario usuario);
}
