package com.asistencias.services.impl;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.asistencias.models.dto.MovimientosDTO;
import com.asistencias.models.entity.Movimientos;
import com.asistencias.models.entity.Usuario;
import com.asistencias.models.mapper.MovimientosMapper;
import com.asistencias.repositories.MovimientosRepository;
import com.asistencias.repositories.UsuarioRepository;
import com.asistencias.services.MovimientosService;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class MovimientosServiceImpl implements MovimientosService {

    private final UsuarioRepository uRepository;

    private final MovimientosRepository mRepository;


    @Override
    public MovimientosDTO crearRegistro(String numeroIdentificacion) {
        Optional<Usuario> user = uRepository.findByNumeroIdentificacion(numeroIdentificacion);
        if (user.isPresent()) {
            Optional<Movimientos> ultimoMovimiento = mRepository.findTopByUsuariosOrderByIdDesc(user.get());

            if (ultimoMovimiento.isPresent() && ultimoMovimiento.get().isEntrada()) {
                Movimientos salida = new Movimientos();
                salida.setUsuarios(user.get());
                salida.setFechaHora(LocalDateTime.now());
                salida.setEntrada(false);
                mRepository.save(salida);
                return MovimientosMapper.toDto(salida);
            } else {
                Movimientos entrada = new Movimientos();
                entrada.setUsuarios(user.get());
                entrada.setFechaHora(LocalDateTime.now());
                entrada.setEntrada(true);
                mRepository.save(entrada);
                return MovimientosMapper.toDto(entrada);
            }
        } else {
            return null;
        }

    }

    @Override
    public List<MovimientosDTO> obtenerRegistros() {
        return mRepository.findAllByOrderByFechaHoraDesc()
                .stream()
                .map(m -> MovimientosMapper.toDto(m)).collect(Collectors.toList());
    }

    @Override
    public MovimientosDTO modificarRegistro(Long id, MovimientosDTO movimientoDTO) {
        Optional<Movimientos> m = mRepository.findById(id);
        if (m.isPresent()) {
            Movimientos mNew = mRepository.save(MovimientosMapper.toEntity(movimientoDTO));
            return MovimientosMapper.toDto(mNew);
        }
        return null;
    }

    @Override
    public List<MovimientosDTO> obtenerRegistrosPorIdDelUser(Long id) {
        Optional<Usuario> userO = uRepository.findById(id);
        if (userO.isPresent()) {
            Optional<List<Movimientos>> mU = mRepository.findAllByUsuarios(userO.get());
            if (mU.isPresent()) {
                return mU.get()
                        .stream()
                        .map(m -> MovimientosMapper.toDto(m))
                        .collect(Collectors.toList());
            } else {
                return null;
            }
        } else {
            return null;
        }
    }

}