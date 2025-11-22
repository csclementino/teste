package com.arc.lince.services;

import com.arc.lince.domains.*;
import com.arc.lince.gateways.CameraRepository;
import com.arc.lince.gateways.EquipeRepository;
import com.arc.lince.gateways.SetorRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class CameraService {

    private final CameraRepository cameraRepository;
    private final SetorRepository setorRepository;

    public Camera create(Camera camera) {
        getSetor(camera.getSetor().getId());
        return cameraRepository.save(camera);
    }

    public Camera update(Camera camera) {
        getId(camera.getId());
        getSetor(camera.getSetor().getId());
        return cameraRepository.save(camera);
    }

    public void delete(String id) {
        getId(id);
        cameraRepository.deleteById(id);
    }

    public void getSetor(String id) {
        Optional<Setor> setor = setorRepository.findById(id);
        if (setor.isEmpty()) {
            throw new EntityNotFoundException("Setor não encontrado.");
        }
    }

    public Camera getId(String id) {
        Optional<Camera> camera = cameraRepository.findById(id);
        if (camera.isEmpty()) {
            throw new EntityNotFoundException("Camera não encontrada.");
        }
        return camera.get();
    }

    public List<Camera> getCamerasSetor(String setorId) {
        getSetor(setorId);
        return cameraRepository.findBySetor_Id(setorId);
    }

    public Page<Camera> listarCamerasStatus(String status,int page, int qtdListada, Sort.Direction direction) {
        Pageable pageable = PageRequest.of(
                page,
                qtdListada,
                Sort.by(direction, "localizacao")
        );
        return cameraRepository.findByStatus(status, pageable);
    }

    public Camera patch(String id, Camera camera) {
        Camera cameraExistente = getId(id);

        if(camera.getSetor() != null && camera.getSetor().getId() != null) {
            getSetor(camera.getSetor().getId());
        }

        Camera cameraAtualizado = Camera.builder()
                .id(cameraExistente.getId())
                .localizacao(camera.getLocalizacao() != null
                        ? camera.getLocalizacao()
                        : cameraExistente.getLocalizacao())
                .descricao(camera.getDescricao() != null
                        ? camera.getDescricao()
                        : cameraExistente.getDescricao())
                .status(camera.getStatus() != null
                        ? camera.getStatus()
                        : cameraExistente.getStatus())
                .setor(Setor.builder()
                        .id(camera.getSetor().getId() != null
                                ? camera.getSetor().getId()
                                : cameraExistente.getSetor().getId())
                        .build())
                .build();
        return cameraRepository.save(cameraAtualizado);
    }

    public List<Camera> getAll(){
        return cameraRepository.findAll();
    }
}
