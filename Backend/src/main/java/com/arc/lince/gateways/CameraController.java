package com.arc.lince.gateways;

import com.arc.lince.domains.Camera;
import com.arc.lince.domains.Operador;
import com.arc.lince.gateways.dtos.*;
import com.arc.lince.gateways.dtos.response.CameraResponseDto;
import com.arc.lince.gateways.dtos.response.OperadorResponseDto;
import com.arc.lince.services.CameraService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/camera")
@RequiredArgsConstructor
public class CameraController {

    private final CameraService cameraService;

    @PostMapping
    public ResponseEntity<CameraResponseDto> create(@RequestBody @Valid CameraRequestDto cameraRequestDto) {
        Camera camera = cameraService.create(cameraRequestDto.toCamera());
        return ResponseEntity.status(HttpStatus.CREATED).body(CameraResponseDto.fromCamera(camera));
    }

    @PutMapping()
    public ResponseEntity<CameraResponseDto> update(@RequestBody @Valid CameraRequestUpdateDto cameraRequestUpdateDto) {
        Camera camera = cameraService.update(cameraRequestUpdateDto.toCamera());
        return ResponseEntity.ok().body(CameraResponseDto.fromCamera(camera));
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<CameraResponseDto> getId(@PathVariable("id") String id) {
        Camera camera = cameraService.getId(id);
        return ResponseEntity.ok().body(CameraResponseDto.fromCamera(camera));
    }

    @GetMapping(value = "/setor/{id}")
    public ResponseEntity<List<CameraResponseDto>> getCamerasSetor(@PathVariable("id") String id) {
        List<Camera> cameras = cameraService.getCamerasSetor(id);
        if (cameras.isEmpty()) {
            return ResponseEntity
                    .noContent()
                    .build();
        } else {
            return ResponseEntity.ok(cameras.stream()
                    .map(CameraResponseDto::fromCamera)
                    .toList());
        }
    }

    @GetMapping(value = "/status")
    public ResponseEntity<?> getCamerasStatus(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "ASC") Sort.Direction direction,
            @RequestParam(defaultValue = "10") int qtdListada,
            @RequestParam String status
    ){
        Page<Camera> cameras =
                cameraService.listarCamerasStatus(status,page, qtdListada, direction);

        Page<CameraResponseDto> list = cameras.map(CameraResponseDto::fromCamera);

        if (cameras.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(list);
    }

    @GetMapping
    public ResponseEntity<List<CameraResponseDto>> getAll() {
        List<Camera> cameras = cameraService.getAll();
        if (cameras.isEmpty()) {
            return ResponseEntity
                    .noContent()
                    .build();
        } else {
            return ResponseEntity.ok(cameras.stream()
                    .map(CameraResponseDto::fromCamera)
                    .toList());
        }
    }

    @PatchMapping(value = "/{id}")
    public ResponseEntity<CameraResponseDto> patch(@PathVariable("id") String id, @RequestBody @Valid CameraRequestPatchDto cameraRequestPatchDto) {
        Camera camera = cameraService.patch(id, cameraRequestPatchDto.toCamera());
        return ResponseEntity.ok().body(CameraResponseDto.fromCamera(camera));
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") String id) {
        cameraService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
