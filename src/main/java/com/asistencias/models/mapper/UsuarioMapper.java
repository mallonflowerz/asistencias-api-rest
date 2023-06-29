package com.asistencias.models.mapper;

import org.modelmapper.ModelMapper;

import com.asistencias.models.dto.UsuarioDTO;
import com.asistencias.models.entity.Usuario;

public class UsuarioMapper {
    private static final ModelMapper modelMapper = new ModelMapper();

    public static Usuario toEntity(UsuarioDTO usuarioDTO) {
        return modelMapper.map(usuarioDTO, Usuario.class);
    }

    public static UsuarioDTO toDto(Usuario usuario) {
        return modelMapper.map(usuario, UsuarioDTO.class);
    }
}
