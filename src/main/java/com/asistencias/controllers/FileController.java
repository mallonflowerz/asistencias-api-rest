package com.asistencias.controllers;

import java.util.Arrays;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.asistencias.models.Response;
import com.asistencias.models.entity.FileData;
import com.asistencias.services.FileService;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@RequestMapping("/files")
@RestController
public class FileController {

    private final FileService fileService;

    @PostMapping
    public ResponseEntity<Response> uploadFile(@RequestParam("file") MultipartFile file,
            @RequestParam("userId") Long userId) throws Exception {

        if (isImageFile(file)) {
            Boolean esCorrecto = fileService.save(file, userId);
            if (esCorrecto) {
                return ResponseEntity.ok().body(new Response("Se cargó correctamente la foto"));
            }
            return ResponseEntity.status(404).body(new Response("El usuario digitado no existe"));
        }

        return ResponseEntity.badRequest().body(new Response("No se proporcionó una foto válida"));
    }

    @GetMapping("/{userId}")
    public ResponseEntity<FileData> getFile(@PathVariable Long userId) throws Exception {
        FileData r = fileService.load(userId);
        return ResponseEntity.ok().body(r);
    }

    @PutMapping
    public ResponseEntity<Response> updateFile(@RequestParam("file") MultipartFile file,
            @RequestParam("userId") Long userId) throws Exception {
            if (isImageFile(file)) {
                if (fileService.update(userId, file) == null){
                    return ResponseEntity.status(404).body(new Response("El usuario digitado no existe"));
                }
                return ResponseEntity.ok().body(new Response("Se actualizo correctamente la foto"));
            }
            return ResponseEntity.badRequest().body(new Response("No se proporcionó una foto válida"));
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<Response> deleteFile(@PathVariable Long userId) throws Exception {
        boolean eliminado = fileService.delete(userId);
        if (eliminado){
            return ResponseEntity.ok().body(new Response("Se elimino correctamente el archivo"));
        }
        return ResponseEntity.badRequest().body(new Response("Ha ocurrido un error, recuerde proporcionar datos validos"));
    }

    private boolean isImageFile(MultipartFile file) {
        String[] allowedExtensions = { "jpg", "jpeg", "png" };

        String originalFilename = file.getOriginalFilename();
        if (originalFilename != null) {
            String extension = originalFilename.substring(originalFilename.lastIndexOf(".") + 1).toLowerCase();
            return Arrays.asList(allowedExtensions).contains(extension);
        }

        return false;
    }

}
