package com.asistencias.configuration.services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.asistencias.models.entity.Credenciales;
import com.asistencias.repositories.CredencialesRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class JpaUserDetailsService implements UserDetailsService {

    private final CredencialesRepository cRepository;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Credenciales> o = cRepository.findByUsername(username);
        if (!o.isPresent()) {
            throw new UsernameNotFoundException(String.format("%s no existe!", username));
        }

        Credenciales user = o.orElseThrow();
        List<GrantedAuthority> authorities = user.getRoles()
                .stream()
                .map(r -> new SimpleGrantedAuthority(r.getNameRole()))
                .collect(Collectors.toList());
                
        return new User(
                user.getUsername(),
                user.getPassword(),
                true,
                true,
                true,
                true,
                authorities);
    }

}
