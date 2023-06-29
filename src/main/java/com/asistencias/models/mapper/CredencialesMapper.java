package com.asistencias.models.mapper;

import org.modelmapper.ModelMapper;

import com.asistencias.models.dto.CredencialesDTO;
import com.asistencias.models.entity.Credenciales;

public class CredencialesMapper {
    private static final ModelMapper mMapper = new ModelMapper();

    public static Credenciales toEntity(CredencialesDTO cDto){
        return mMapper.map(cDto, Credenciales.class);
    }

    public static CredencialesDTO toDto(Credenciales c){
        return mMapper.map(c, CredencialesDTO.class);
    }
}
