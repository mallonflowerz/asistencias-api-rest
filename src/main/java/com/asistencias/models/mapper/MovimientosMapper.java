package com.asistencias.models.mapper;

import org.modelmapper.ModelMapper;

import com.asistencias.models.dto.MovimientosDTO;
import com.asistencias.models.entity.Movimientos;

public class MovimientosMapper {
    private static final ModelMapper modelMapper = new ModelMapper();

    public static Movimientos toEntity(MovimientosDTO mDto){
        return modelMapper.map(mDto, Movimientos.class);
    }

    public static MovimientosDTO toDto(Movimientos movimiento){
        return modelMapper.map(movimiento, MovimientosDTO.class);
    }

}
