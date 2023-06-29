package com.asistencias.configuration;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.DependsOn;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.asistencias.models.entity.Credenciales;
import com.asistencias.models.entity.Role;
import com.asistencias.repositories.CredencialesRepository;
import com.asistencias.repositories.RoleRepository;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Component
@DependsOn("roleRepository")
@Transactional
public class DefaultData implements CommandLineRunner{
    
    private final CredencialesRepository cRepository;
    private final PasswordEncoder passEncoder;
    private final RoleRepository roleRepository;

    @Override
    public void run(String... args) throws Exception {
        List<Role> roles = new ArrayList<>();
        Optional<Role> rol = roleRepository.findByNameRole("ROLE_ADMIN");
        Optional<Role> rolU = roleRepository.findByNameRole("ROLE_USER");
        if (!rol.isPresent()){
            Role adminRole = new Role();
            adminRole.setNameRole("ROLE_ADMIN");
            roles.add(adminRole);
            roleRepository.save(adminRole);
        } else {
            roles.add(rol.orElseThrow());
        }

        if (!rolU.isPresent()){
            Role userRole = new Role();
            userRole.setNameRole("ROLE_USER");
            roles.add(userRole);
            roleRepository.save(userRole);
        } else {
            roles.add(rolU.orElseThrow());
        }
        
        if (cRepository.count() == 0){
            Credenciales user = new Credenciales();
            user.setUsername("admin");
            user.setPassword(passEncoder.encode("@123456@"));
            user.setRoles(roles);
            cRepository.save(user);
        }
    }
}
