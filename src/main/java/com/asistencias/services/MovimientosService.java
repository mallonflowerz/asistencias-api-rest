package com.asistencias.services;

import java.util.List;

import com.asistencias.models.dto.MovimientosDTO;

public interface MovimientosService {
    
    MovimientosDTO crearRegistro(String numeroIdentificacion);

    List<MovimientosDTO> obtenerRegistros();

    MovimientosDTO modificarRegistro(Long id, MovimientosDTO movimientoDTO);

    List<MovimientosDTO> obtenerRegistrosPorIdDelUser(Long id);

}
