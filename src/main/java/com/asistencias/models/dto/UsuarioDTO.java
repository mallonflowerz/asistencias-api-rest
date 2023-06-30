package com.asistencias.models.dto;

import com.asistencias.models.entity.FileData;
import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;

@Data
public class UsuarioDTO {

    private Long id;

    @JsonIgnore
    private FileData foto;

    @NotBlank(message = "El primer nombre no puede estar vacio")
    private String primerNombre;

    @NotBlank(message = "El segundo nombre no puede estar vacio")
    private String segundoNombre;

    @NotBlank(message = "El primer Apellido no puede estar vacio")
    private String primerApellido;

    @NotBlank(message = "El segundo Apellido no puede estar vacio")
    private String segundoApellido;

    @NotBlank(message = "El email no puede estar vacio")
    @Email
    private String email;

    @Size(min = 4, message = "El numero de Identificacion Debe contener minimo cuatro caracteres")
    @Pattern(regexp = "\\d+", message = "El número de identificación debe contener solo números")
    private String numeroIdentificacion;

    @JsonIgnore
    public String getNombreCompleto() {
        return String.join(" ", primerNombre, segundoNombre, primerApellido, segundoApellido);
    }

}
