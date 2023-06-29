package com.asistencias.services.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.asistencias.models.dto.CredencialesDTO;
import com.asistencias.models.entity.Credenciales;
import com.asistencias.models.entity.Role;
import com.asistencias.models.mapper.CredencialesMapper;
import com.asistencias.repositories.CredencialesRepository;
import com.asistencias.repositories.RoleRepository;
import com.asistencias.services.CredencialesService;

import jakarta.validation.ConstraintViolationException;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class CredencialesServiceImpl implements CredencialesService {

    private final RoleRepository roleRepository;
    private final CredencialesRepository cRepository;
    private final PasswordEncoder passEncoder;

    @Override
    public List<CredencialesDTO> obtenerCredenciales() {
        return cRepository.findAll()
                .stream()
                .map(c -> CredencialesMapper.toDto(c))
                .collect(Collectors.toList());
    }

    @Override
    public CredencialesDTO crearCredencial(Credenciales c) {
        if (cRepository.existsByUsername(c.getUsername())){
            throw new ConstraintViolationException("El username ya existe", null);
        }
        c.setRoles(getRoles(c));
        c.setPassword(passEncoder.encode(c.getPassword()));
        return CredencialesMapper.toDto(cRepository.save(c));
    }

    @Override
    public CredencialesDTO editarCredencial(Long id, Credenciales c) {
        Optional<Credenciales> o = cRepository.findById(id);
        if (o.isPresent()){
            c.setId(id);
            c.setPassword(passEncoder.encode(c.getPassword()));
            c.setRoles(getRoles(c));
            return CredencialesMapper.toDto(cRepository.save(c));
        }
        return null;
    }

    @Override
    public boolean eliminarCredencialPorId(Long id) {
        Optional<Credenciales> o = cRepository.findById(id);
        if (o.isPresent()){
            cRepository.deleteById(id);
            return true;
        }
        return false;
    }

    private List<Role> getRoles(Credenciales user) {
        List<Role> roles = new ArrayList<>();
        Optional<Role> o = roleRepository.findByNameRole("ROLE_USER");
        if (o.isPresent()) {
            roles.add(o.orElseThrow());
        }

        if (user.isAdmin()) {
            Optional<Role> oA = roleRepository.findByNameRole("ROLE_ADMIN");
            if (oA.isPresent()) {
                roles.add(oA.orElseThrow());
            }
        }
        return roles;
    }

}
