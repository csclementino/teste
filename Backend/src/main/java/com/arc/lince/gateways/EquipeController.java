package com.arc.lince.gateways;

import com.arc.lince.domains.Equipe;
import com.arc.lince.gateways.dtos.EquipeRequestDto;
import com.arc.lince.gateways.dtos.EquipeRequestPatchDto;
import com.arc.lince.gateways.dtos.EquipeRequestUpdateDto;
import com.arc.lince.gateways.dtos.response.EquipeResponseDto;
import com.arc.lince.services.EquipeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/equipe")
@RequiredArgsConstructor
public class EquipeController implements EquipeControllerInterface {

    private final EquipeService equipeService;

    @PostMapping
    public ResponseEntity<EquipeResponseDto> create(@RequestBody @Valid EquipeRequestDto equipeRequestDto) {
        Equipe equipe = equipeService.create(equipeRequestDto.toEquipe());
        return ResponseEntity.status(HttpStatus.CREATED).body(EquipeResponseDto.fromEquipe(equipe));
    }

    @PutMapping()
    public ResponseEntity<EquipeResponseDto> update(@RequestBody @Valid EquipeRequestUpdateDto equipeRequestUpdateDto) {
        Equipe equipe = equipeService.update(equipeRequestUpdateDto.toEquipe());
        return ResponseEntity.ok().body(EquipeResponseDto.fromEquipe(equipe));
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<EquipeResponseDto> getId(@PathVariable("id") String id) {
        Equipe equipe = equipeService.getId(id);
        return ResponseEntity.ok().body(EquipeResponseDto.fromEquipe(equipe));
    }

    @GetMapping
    public ResponseEntity<List<EquipeResponseDto>> getAll() {
        List<Equipe> equipes = equipeService.getAll();
        if (equipes.isEmpty()) {
            return ResponseEntity
                    .noContent()
                    .build();
        } else {
            return ResponseEntity.ok(equipes.stream()
                    .map(EquipeResponseDto::fromEquipe)
                    .toList());
        }
    }

    @PatchMapping(value = "/{id}")
    public ResponseEntity<EquipeResponseDto> patch(@PathVariable("id") String id, @RequestBody @Valid EquipeRequestPatchDto equipeRequestPatchDto) {
        Equipe equipe = equipeService.patch(id, equipeRequestPatchDto.toEquipe());
        return ResponseEntity.ok().body(EquipeResponseDto.fromEquipe(equipe));
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") String id) {
        equipeService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
