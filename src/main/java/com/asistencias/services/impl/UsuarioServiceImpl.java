package com.asistencias.services.impl;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.asistencias.models.dto.UsuarioDTO;
import com.asistencias.models.entity.Usuario;
import com.asistencias.models.mapper.UsuarioMapper;
import com.asistencias.repositories.UsuarioRepository;
import com.asistencias.services.UsuarioService;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class UsuarioServiceImpl implements UsuarioService {

    private final UsuarioRepository usuarioRepository;

    @Override
    public UsuarioDTO crearUser(UsuarioDTO user) {
        if (usuarioRepository.existsByNumeroIdentificacion(user.getNumeroIdentificacion())) {
            return null;
        }
        Usuario u = UsuarioMapper.toEntity(user);
        u.setPrimerNombre(u.getPrimerNombre().toUpperCase());
        u.setSegundoNombre(u.getSegundoNombre().toUpperCase());
        u.setPrimerApellido(u.getPrimerApellido().toUpperCase());
        u.setSegundoApellido(u.getSegundoApellido().toUpperCase());
        u.setFechaYHoraDeRegistro(LocalDateTime.now());
        usuarioRepository.save(u);
        return UsuarioMapper.toDto(u);
    }

    @Override
    public UsuarioDTO obtenerUserPorID(Long id) {
        Optional<Usuario> u = usuarioRepository.findById(id);
        if (u.isPresent()) {
            return UsuarioMapper.toDto(u.get());
        }
        return null;
    }

    @Override
    public UsuarioDTO modificarUser(Long id, UsuarioDTO user) {
        Optional<Usuario> u = usuarioRepository.findById(id);
        if (u.isPresent()) {
            Usuario newU = UsuarioMapper.toEntity(user);
            newU.setId(id);
            newU.setPrimerNombre(newU.getPrimerNombre().toUpperCase());
            newU.setSegundoNombre(newU.getSegundoNombre().toUpperCase());
            newU.setPrimerApellido(newU.getPrimerApellido().toUpperCase());
            newU.setSegundoApellido(newU.getSegundoApellido().toUpperCase());
            usuarioRepository.save(newU);
            return UsuarioMapper.toDto(newU);
        }
        return null;
    }

    @Override
    public boolean eliminarUser(Long id) {
        Optional<Usuario> u = usuarioRepository.findById(id);
        if (u.isPresent()) {
            usuarioRepository.deleteById(id);
            return true;
        }
        return false;
    }

    @Override
    public List<UsuarioDTO> obtenerUsers() {
        return usuarioRepository.findAll()
                .stream().map(user -> UsuarioMapper.toDto(user)).collect(Collectors.toList());
    }

}
