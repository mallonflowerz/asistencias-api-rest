package com.asistencias.services;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.asistencias.models.dto.UsuarioDTO;
import com.asistencias.models.entity.FileData;

public interface FileService {
    
    List<FileData> getByUser(UsuarioDTO usuarioDTO);

    boolean save(MultipartFile file, Long userID) throws Exception;
    
    FileData load(Long userId) throws Exception;

}
