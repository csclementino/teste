package com.arc.lince.services;

import com.arc.lince.domains.*;
import com.arc.lince.gateways.*;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AlertaService {

    private final AlertaRepository alertaRepository;
    private final CameraRepository cameraRepository;
    private final OperadorRepository operadorRepository;
    private final SetorRepository setorRepository;

    public Alerta create(Alerta alerta) {
        getCamera(alerta.getCamera().getId());
        getOperador(alerta.getOperador().getId());
        return alertaRepository.save(alerta);
    }

    public Alerta update(Alerta alerta) {
        Alerta alertaExistente = getId(alerta.getId());
        getCamera(alerta.getCamera().getId());
        getOperador(alerta.getOperador().getId());
        Alerta alertaAtualizado = Alerta.builder()
                .id(alertaExistente.getId())
                .motivo(alerta.getMotivo())
                .nivelAlerta(alerta.getNivelAlerta())
                .dataHora(alertaExistente.getDataHora())
                .camera(Camera.builder()
                        .id(alerta.getCamera().getId())
                        .build())
                .operador(Operador.builder()
                        .id(alerta.getOperador().getId())
                        .build())
                .build();
        return alertaRepository.save(alertaAtualizado);
    }

    public void delete(String id) {
        getId(id);
        alertaRepository.deleteById(id);
    }

    public void getCamera(String id) {
        Optional<Camera> camera = cameraRepository.findById(id);
        if (camera.isEmpty()) {
            throw new EntityNotFoundException("Camera não encontrada.");
        }
    }

    public void getOperador(String id) {
        Optional<Operador> operador = operadorRepository.findById(id);
        if (operador.isEmpty()) {
            throw new EntityNotFoundException("Operador não encontrado.");
        }
    }

    public Alerta getId(String id) {
        Optional<Alerta> alerta = alertaRepository.findById(id);
        if (alerta.isEmpty()) {
            throw new EntityNotFoundException("Alerta não encontrado.");
        }
        return alerta.get();
    }

    public List<Alerta> getAlertaOperador(String operadorId) {
        getOperador(operadorId);
        return alertaRepository.findByOperador_Id(operadorId);
    }

    public List<Alerta> getAlertaSetor(String setorId) {
        Optional<Setor> setor = setorRepository.findById(setorId);
        if (setor.isEmpty()) {
            throw new EntityNotFoundException("Setor não encontrado.");
        }
        return alertaRepository.findByCamera_Setor_Id(setorId);
    }

    public Alerta patch(String id, Alerta alerta) {
        Alerta alertaExistente = getId(id);

        if(alerta.getCamera() != null && alerta.getCamera().getId() != null) {
            getCamera(alerta.getCamera().getId());
        }
        if(alerta.getOperador() != null && alerta.getOperador().getId() != null) {
            getOperador(alerta.getOperador().getId());
        }

        Alerta alertaAtualizado = Alerta.builder()
                .id(alertaExistente.getId())
                .motivo(alerta.getMotivo() != null
                        ? alerta.getMotivo()
                        : alertaExistente.getMotivo())
                .nivelAlerta(alerta.getNivelAlerta() != null
                        ? alerta.getNivelAlerta()
                        : alertaExistente.getNivelAlerta())
                .dataHora(alerta.getDataHora() != null
                        ? alerta.getDataHora()
                        : alertaExistente.getDataHora())
                .camera(Camera.builder()
                        .id(alerta.getCamera().getId() != null
                                ? alerta.getCamera().getId()
                                : alertaExistente.getCamera().getId())
                        .build())
                .operador(Operador.builder()
                        .id(alerta.getOperador().getId() != null
                                ? alerta.getOperador().getId()
                                : alertaExistente.getOperador().getId())
                        .build())
                .build();
        return alertaRepository.save(alertaAtualizado);
    }

    public List<Alerta> getAll(){
        return alertaRepository.findAll();
    }
}
