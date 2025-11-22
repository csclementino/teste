package com.arc.lince.gateways;

import com.arc.lince.domains.Equipe;
import com.arc.lince.domains.Setor;
import com.arc.lince.gateways.dtos.*;
import com.arc.lince.gateways.dtos.response.EquipeResponseDto;
import com.arc.lince.gateways.dtos.response.SetorResponseDto;
import com.arc.lince.services.SetorService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/setor")
@RequiredArgsConstructor
public class SetorController {

    private final SetorService setorService;

    @PostMapping
    public ResponseEntity<SetorResponseDto> create(@RequestBody @Valid SetorRequestDto setorRequestDto) {
        Setor setor = setorService.create(setorRequestDto.toSetor());
        return ResponseEntity.status(HttpStatus.CREATED).body(SetorResponseDto.fromSetor(setor));
    }

    @PutMapping()
    public ResponseEntity<SetorResponseDto> update(@RequestBody @Valid SetorRequestUpdateDto setorRequestUpdateDto) {
        Setor setor = setorService.update(setorRequestUpdateDto.toSetor());
        return ResponseEntity.ok().body(SetorResponseDto.fromSetor(setor));
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<SetorResponseDto> getId(@PathVariable("id") String id) {
        Setor setor = setorService.getId(id);
        return ResponseEntity.ok().body(SetorResponseDto.fromSetor(setor));
    }

    @GetMapping
    public ResponseEntity<List<SetorResponseDto>> getAll() {
        List<Setor> setores = setorService.getAll();
        if (setores.isEmpty()) {
            return ResponseEntity
                    .noContent()
                    .build();
        } else {
            return ResponseEntity.ok(setores.stream()
                    .map(SetorResponseDto::fromSetor)
                    .toList());
        }
    }

    @PatchMapping(value = "/{id}")
    public ResponseEntity<SetorResponseDto> patch(@PathVariable("id") String id, @RequestBody @Valid SetorRequestPatchDto setorRequestPatchDto) {
        Setor setor = setorService.patch(id, setorRequestPatchDto.toSetor());
        return ResponseEntity.ok().body(SetorResponseDto.fromSetor(setor));
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") String id) {
        setorService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
