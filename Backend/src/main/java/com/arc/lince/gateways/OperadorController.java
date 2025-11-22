package com.arc.lince.gateways;

import com.arc.lince.domains.Equipe;
import com.arc.lince.domains.Operador;
import com.arc.lince.gateways.dtos.*;
import com.arc.lince.gateways.dtos.response.EquipeResponseDto;
import com.arc.lince.gateways.dtos.response.OperadorResponseDto;
import com.arc.lince.services.OperadorService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/operador")
@RequiredArgsConstructor
public class OperadorController {

    private final OperadorService  operadorService;

    @PostMapping
    public ResponseEntity<OperadorResponseDto> create(@RequestBody @Valid OperadorRequestDto operadorRequestDto) {
        Operador operador = operadorService.create(operadorRequestDto.toOperador());
        return ResponseEntity.status(HttpStatus.CREATED).body(OperadorResponseDto.fromOperador(operador));
    }

    @PutMapping()
    public ResponseEntity<OperadorResponseDto> update(@RequestBody @Valid OperadorRequestUpdateDto operadorRequestUpdateDto) {
        Operador operador = operadorService.update(operadorRequestUpdateDto.toOperador());
        return ResponseEntity.ok().body(OperadorResponseDto.fromOperador(operador));
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<OperadorResponseDto> getId(@PathVariable("id") String id) {
        Operador operador = operadorService.getId(id);
        return ResponseEntity.ok().body(OperadorResponseDto.fromOperador(operador));
    }

    @GetMapping(value = "/equipe/{id}")
    public ResponseEntity<List<OperadorResponseDto>> getOperadoresEquipe(@PathVariable("id") String id) {
        List<Operador> operadores = operadorService.getOperadoresEquipe(id);
        if (operadores.isEmpty()) {
            return ResponseEntity
                    .noContent()
                    .build();
        } else {
            return ResponseEntity.ok(operadores.stream()
                    .map(OperadorResponseDto::fromOperador)
                    .toList());
        }
    }

    @GetMapping
    public ResponseEntity<List<OperadorResponseDto>> getAll() {
        List<Operador> operadores = operadorService.getAll();
        if (operadores.isEmpty()) {
            return ResponseEntity
                    .noContent()
                    .build();
        } else {
            return ResponseEntity.ok(operadores.stream()
                    .map(OperadorResponseDto::fromOperador)
                    .toList());
        }
    }

    @PatchMapping(value = "/{id}")
    public ResponseEntity<OperadorResponseDto> patch(@PathVariable("id") String id, @RequestBody @Valid OperadorRequestPatchDto operadorRequestPatchDto) {
        Operador operador = operadorService.patch(id, operadorRequestPatchDto.toOperador());
        return ResponseEntity.ok().body(OperadorResponseDto.fromOperador(operador));
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") String id) {
        operadorService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
