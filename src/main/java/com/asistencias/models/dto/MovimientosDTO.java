package com.asistencias.models.dto;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class MovimientosDTO {
    
    private UsuarioDTO usuarios;
    private LocalDateTime fechaHora;
    private boolean entrada;
}
