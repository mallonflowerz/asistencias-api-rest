package com.asistencias.controllers;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.asistencias.configuration.validations.Validacion;
import com.asistencias.models.Response;
import com.asistencias.models.dto.UsuarioDTO;
import com.asistencias.services.UsuarioService;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@RequestMapping("/usuarios")
@RestController
public class UsuarioController {

    private final UsuarioService uService;

    @GetMapping
    public ResponseEntity<List<UsuarioDTO>> mostrarUsuarios() {
        return ResponseEntity.ok().body(uService.obtenerUsers());
    }

    @GetMapping("/{id}")
    public ResponseEntity<UsuarioDTO> mostrarUsuario(@PathVariable Long id) {
        UsuarioDTO user = uService.obtenerUserPorID(id);
        if (user == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.ok().body(user);
    }

    @PostMapping
    public ResponseEntity<?> guardarUsuario(@Valid @RequestBody UsuarioDTO user, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Validacion.valid(bindingResult));
        }
        UsuarioDTO o = uService.crearUser(user);
        if (o == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new Response("El numero de identificacion: '"+ user.getNumeroIdentificacion() +"' ya esta registrado!"));
        }
        return ResponseEntity.ok().body(o);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> modificarUsuario(@PathVariable Long id, @Valid @RequestBody UsuarioDTO user,
            BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Validacion.valid(bindingResult));
        }
        UsuarioDTO newU = uService.modificarUser(id, user);
        if (newU == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.ok().body(newU);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarUsuario(@PathVariable Long id) {
        boolean eliminado = uService.eliminarUser(id);
        if (eliminado) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }
}
