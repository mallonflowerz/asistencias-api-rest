package com.asistencias.models.entity;

import java.time.LocalDateTime;
import java.util.List;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Entity
@Table(name = "usuarios")
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String primerNombre;
    private String segundoNombre;
    private String primerApellido;
    private String segundoApellido;
    private String email;

    @Column(unique = true)
    private String numeroIdentificacion;
    private LocalDateTime fechaYHoraDeRegistro;

    @OneToMany(mappedBy = "usuarios", cascade = CascadeType.ALL)
    @OrderBy("fechaHora DESC")
    private List<Movimientos> movimientos;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "foto_id", referencedColumnName = "id")
    private FileData foto;
}
