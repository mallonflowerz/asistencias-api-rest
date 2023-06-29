package com.asistencias.services;

import java.util.List;

import com.asistencias.models.dto.CredencialesDTO;
import com.asistencias.models.entity.Credenciales;

public interface CredencialesService {
    
    List<CredencialesDTO> obtenerCredenciales();

    CredencialesDTO crearCredencial(Credenciales c);

    CredencialesDTO editarCredencial(Long id, Credenciales c);

    boolean eliminarCredencialPorId(Long id);
}
