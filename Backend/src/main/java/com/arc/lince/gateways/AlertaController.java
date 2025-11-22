package com.arc.lince.gateways;

import com.arc.lince.domains.Alerta;
import com.arc.lince.domains.Operador;
import com.arc.lince.gateways.dtos.*;
import com.arc.lince.gateways.dtos.response.AlertaResponseDto;
import com.arc.lince.gateways.dtos.response.OperadorResponseDto;
import com.arc.lince.services.AlertaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/alerta")
@RequiredArgsConstructor
public class AlertaController {

    private final AlertaService alertaService;

    @PostMapping
    public ResponseEntity<AlertaResponseDto> create(@RequestBody @Valid AlertaRequestDto alertaRequestDto) {
        Alerta alerta = alertaService.create(alertaRequestDto.toAlerta());
        return ResponseEntity.status(HttpStatus.CREATED).body(AlertaResponseDto.fromAlerta(alerta));
    }

    @PutMapping()
    public ResponseEntity<AlertaResponseDto> update(@RequestBody @Valid AlertaRequestUpdateDto alertaRequestUpdateDto) {
        Alerta alerta = alertaService.update(alertaRequestUpdateDto.toAlerta());
        return ResponseEntity.ok().body(AlertaResponseDto.fromAlerta(alerta));
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<AlertaResponseDto> getId(@PathVariable("id") String id) {
        Alerta alerta = alertaService.getId(id);
        return ResponseEntity.ok().body(AlertaResponseDto.fromAlerta(alerta));
    }

    @GetMapping(value = "/operador/{id}")
    public ResponseEntity<List<AlertaResponseDto>> getAlertaOperador(@PathVariable("id") String id) {
        List<Alerta> alertas = alertaService.getAlertaOperador(id);
        if (alertas.isEmpty()) {
            return ResponseEntity
                    .noContent()
                    .build();
        } else {
            return ResponseEntity.ok(alertas.stream()
                    .map(AlertaResponseDto::fromAlerta)
                    .toList());
        }
    }

    @GetMapping(value = "/setor/{id}")
    public ResponseEntity<List<AlertaResponseDto>> getAlertaSetor(@PathVariable("id") String id) {
        List<Alerta> alertas = alertaService.getAlertaSetor(id);
        if (alertas.isEmpty()) {
            return ResponseEntity
                    .noContent()
                    .build();
        } else {
            return ResponseEntity.ok(alertas.stream()
                    .map(AlertaResponseDto::fromAlerta)
                    .toList());
        }
    }

    @GetMapping
    public ResponseEntity<List<AlertaResponseDto>> getAll() {
        List<Alerta> alertas = alertaService.getAll();
        if (alertas.isEmpty()) {
            return ResponseEntity
                    .noContent()
                    .build();
        } else {
            return ResponseEntity.ok(alertas.stream()
                    .map(AlertaResponseDto::fromAlerta)
                    .toList());
        }
    }

    @PatchMapping(value = "/{id}")
    public ResponseEntity<AlertaResponseDto> patch(@PathVariable("id") String id, @RequestBody @Valid AlertaRequestPatchDto alertaRequestPatchDto) {
        Alerta alerta = alertaService.patch(id, alertaRequestPatchDto.toAlerta());
        return ResponseEntity.ok().body(AlertaResponseDto.fromAlerta(alerta));
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") String id) {
        alertaService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
