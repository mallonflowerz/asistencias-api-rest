package com.asistencias.services;

import java.util.List;

import com.asistencias.models.dto.UsuarioDTO;

public interface UsuarioService {
    
    UsuarioDTO crearUser(UsuarioDTO user);

    UsuarioDTO obtenerUserPorID(Long id);

    List<UsuarioDTO> obtenerUsers();

    UsuarioDTO modificarUser(Long id, UsuarioDTO user);

    boolean eliminarUser(Long id);
}
