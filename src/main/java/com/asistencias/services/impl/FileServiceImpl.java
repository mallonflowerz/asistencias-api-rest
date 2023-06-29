package com.asistencias.services.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.asistencias.models.dto.UsuarioDTO;
import com.asistencias.models.entity.FileData;
import com.asistencias.models.entity.Usuario;
import com.asistencias.models.mapper.UsuarioMapper;
import com.asistencias.repositories.FileDataRepository;
import com.asistencias.repositories.UsuarioRepository;
import com.asistencias.services.FileService;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class FileServiceImpl implements FileService {

    private final FileDataRepository fileRepository;
    private final UsuarioRepository uRepository;

    @Override
    public boolean save(MultipartFile file, Long userID) throws Exception {
        Optional<Usuario> uO = uRepository.findById(userID);
        if (uO.isPresent()) {
            FileData fileData = new FileData();
            fileData.setFileName(file.getOriginalFilename());
            fileData.setFileType(file.getContentType());
            fileData.setFileData(file.getBytes());
            fileData.setUsuario(uO.get());
            fileRepository.save(fileData);
            uO.get().setFoto(fileData);
            uRepository.save(uO.get());
            return true;
        }
        return false;
    }

    @Override
    public FileData load(Long userId) throws Exception {
        Optional<Usuario> uO = uRepository.findById(userId);
        if (uO == null) {
            throw new Exception("Usuario no encontrado");
        }
        return fileRepository.findByUsuarioId(uO.get().getId());
    }

    @Override
    public List<FileData> getByUser(UsuarioDTO usuarioDTO) {
        Optional<List<FileData>> files = fileRepository
                .findAllByUsuario(UsuarioMapper.toEntity(usuarioDTO));
        if (files.isPresent()) {
            return files.get();
        }
        return null;
    }

}
