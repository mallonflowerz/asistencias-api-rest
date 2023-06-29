package com.asistencias.configuration.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.multipart.MaxUploadSizeExceededException;

import com.asistencias.models.Response;

@ControllerAdvice
public class FileUploadException {
    
    @ExceptionHandler(MaxUploadSizeExceededException.class)
    public ResponseEntity<Response> handleMaxSizeException(MaxUploadSizeExceededException ex){
        return ResponseEntity.status(500).body(new Response("El tamaño del archivos no es permitido"));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Response> handleMaxSizeException(Exception ex){
        return ResponseEntity.status(500).body(new Response("Error: "+ ex.getMessage()));
    }
}
