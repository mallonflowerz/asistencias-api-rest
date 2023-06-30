package com.asistencias.services;

import org.springframework.web.multipart.MultipartFile;

import com.asistencias.models.dto.UsuarioDTO;
import com.asistencias.models.entity.FileData;

public interface FileService {
    
    FileData getByUser(UsuarioDTO usuarioDTO);

    boolean save(MultipartFile file, Long userID) throws Exception;
    
    FileData load(Long userId) throws Exception;

    FileData update(Long userId, MultipartFile file) throws Exception;

    boolean delete(Long userId) throws Exception;

}
