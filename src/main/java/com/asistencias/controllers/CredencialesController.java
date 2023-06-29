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
import com.asistencias.models.dto.CredencialesDTO;
import com.asistencias.models.entity.Credenciales;
import com.asistencias.services.CredencialesService;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@RequestMapping("/register")
@RestController
public class CredencialesController {

    private final CredencialesService cService;

    @GetMapping
    public ResponseEntity<List<CredencialesDTO>> mostrarCredenciales() {
        return ResponseEntity.ok().body(cService.obtenerCredenciales());
    }

    @PostMapping
    public ResponseEntity<?> crearCredencial(@Valid @RequestBody Credenciales c,
            BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Validacion.valid(bindingResult));
        }
        return ResponseEntity.ok().body(cService.crearCredencial(c));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> actualizarCredencial(@PathVariable Long id,
            @Valid @RequestBody Credenciales c, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Validacion.valid(bindingResult));
        }
        CredencialesDTO cNew = cService.editarCredencial(id, c);
        if (cNew == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(cNew);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarCredencial(@PathVariable Long id) {
        boolean eliminado = cService.eliminarCredencialPorId(id);
        if (eliminado) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

}
