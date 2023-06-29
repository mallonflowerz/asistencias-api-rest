package com.asistencias.services.impl;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.asistencias.models.dto.UsuarioDTO;
import com.asistencias.models.entity.Movimientos;
import com.asistencias.models.entity.Usuario;
import com.asistencias.models.mapper.UsuarioMapper;
import com.asistencias.repositories.MovimientosRepository;
import com.asistencias.repositories.UsuarioRepository;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class EmailSenderService {

    private final JavaMailSender mailSender;

    private final UsuarioRepository uRepository;

    private final MovimientosRepository mRepository;

    public void enviarMovimientosPorUser(Long id) {
        Optional<Usuario> userO = uRepository.findById(id);
        if (userO.isPresent()) {
            UsuarioDTO userDTO = UsuarioMapper.toDto(userO.get());
            Optional<List<Movimientos>> mU = mRepository.findAllByUsuarios(userO.get());
            if (mU.isPresent() && !mU.isEmpty()) {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("EEEE, d 'de' MMMM 'de' yyyy. h:mm a",
                        new Locale("es"));
                StringBuilder mensaje = new StringBuilder();
                mensaje.append("Hola ").append(userDTO.getPrimerNombre()).append(",\n\n");
                mensaje.append("Este es tu registro TOTAL de entradas y salidas en CORP SAS EXAMPLE:\n\n");

                mensaje.append("Fecha y Hora\t\t\tTipo de Movimiento\n");
                mensaje.append("----------------------------------------------\n");

                for (Movimientos registro : mU.get()) {
                    String tipoMovimiento = registro.isEntrada() ? "Entrada" : "Salida";
                    LocalDateTime fechaHora = registro.getFechaHora();
                    String fechaHoraFormatted = fechaHora.format(formatter);

                    mensaje.append(fechaHoraFormatted).append("\t").append(tipoMovimiento).append("\n");
                }

                sendMail(userO.get().getEmail(),
                        "Registro de entradas y salidas de " + userDTO.getNombreCompleto(),
                        mensaje.toString());
            }
        }
    }

    private void sendMail(String toEmail, String subject, String body) {
        SimpleMailMessage msg = new SimpleMailMessage();
        msg.setFrom("marlon0.florez@gmail.com");
        msg.setTo(toEmail);
        msg.setText(body);
        msg.setSubject(subject);

        mailSender.send(msg);

        System.out.println("Email enviado correctamente!");
    }
}
