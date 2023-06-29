package com.asistencias.controllers;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.asistencias.models.dto.MovimientosDTO;
import com.asistencias.services.MovimientosService;
import com.asistencias.services.impl.EmailSenderService;

import lombok.AllArgsConstructor;

@RequestMapping("/asistencia")
@AllArgsConstructor
@RestController
public class MovimientosController {

    private final MovimientosService mService;
    private final EmailSenderService mailService;

    @GetMapping
    public ResponseEntity<List<MovimientosDTO>> mostrarMovimientos() {
        return ResponseEntity.ok().body(mService.obtenerRegistros());
    }

    @GetMapping("/{id}")
    public ResponseEntity<List<MovimientosDTO>> mostrarMovimientosPorUser(@PathVariable Long id) {
        List<MovimientosDTO> registros = mService.obtenerRegistrosPorIdDelUser(id);
        if (registros == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.ok().body(registros);
    }

    @PostMapping("/envmail/{id}")
    public ResponseEntity<List<MovimientosDTO>> enviarMovimientosPorUser(@PathVariable Long id) {
        List<MovimientosDTO> registros = mService.obtenerRegistrosPorIdDelUser(id);
        mailService.enviarMovimientosPorUser(id);
        return ResponseEntity.ok().body(registros);
    }

    @PostMapping("/{numeroIdentificacion}")
    public ResponseEntity<MovimientosDTO> guardarRegistro(@PathVariable String numeroIdentificacion) {
        MovimientosDTO movimiento = mService.crearRegistro(numeroIdentificacion);
        if (movimiento == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.ok().body(movimiento);
    }

    // @PutMapping("/{id}")
    // public ResponseEntity<MovimientosDTO> modificarRegistro(@PathVariable Long
    // id, @RequestBody MovimientosDTO movimiento){
    // MovimientosDTO m = mService.modificarRegistro(id, movimiento);
    // if (m == null){
    // return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    // }
    // return ResponseEntity.ok().body(m);
    // }

}
