package com.asistencias.services.impl;

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
            FileData fileData = FileData.builder()
                    .fileName(file.getOriginalFilename())
                    .fileType(file.getContentType())
                    .fileData(file.getBytes())
                    .usuario(uO.get())
                    .build();
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
        if (!uO.isPresent()) {
            throw new Exception("Usuario no encontrado");
        }
        return fileRepository.findByUsuarioId(uO.get().getId());
    }

    @Override
    public FileData getByUser(UsuarioDTO usuarioDTO) {
        Optional<FileData> files = fileRepository
                .findByUsuario(UsuarioMapper.toEntity(usuarioDTO));
        if (files.isPresent()) {
            return files.get();
        }
        return null;
    }

    @Override
    public FileData update(Long userId, MultipartFile file) throws Exception {
        Optional<Usuario> uO = uRepository.findById(userId);
        if (!uO.isPresent()) {
            throw new Exception("Usuario no encontrado");
        }
        Optional<FileData> files = fileRepository
                .findByUsuario(uO.get());
        if (files.isPresent()) {
            FileData fileData = FileData.builder()
                    .fileName(file.getOriginalFilename())
                    .fileType(file.getContentType())
                    .fileData(file.getBytes())
                    .usuario(uO.get())
                    .build();
            return fileRepository.save(fileData);
        }
        return null;
    }

    @Override
    public boolean delete(Long userId) throws Exception {
        Optional<Usuario> uO = uRepository.findById(userId);
        if (!uO.isPresent()) {
            throw new Exception("Usuario no encontrado");
        }
        Optional<FileData> file = fileRepository
                .findByUsuario(uO.get());
        if (file.isPresent()) {
            fileRepository.delete(file.get());
            return true;
        }
        return false;
    }

}
