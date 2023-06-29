package com.asistencias.configuration.validations;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.validation.BindingResult;

public class Validacion {
    
    public static List<String> valid(BindingResult bindingResult) {
        List<String> errores = bindingResult.getAllErrors().stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .collect(Collectors.toList());
        return errores;
    }
}
