package com.arc.lince.gateways;

import com.arc.lince.domains.Supervisor;
import com.arc.lince.gateways.dtos.SupervisorRequestDto;
import com.arc.lince.gateways.dtos.SupervisorRequestPatchDto;
import com.arc.lince.gateways.dtos.SupervisorRequestUpdateDto;
import com.arc.lince.gateways.dtos.response.SupervisorResponseDto;
import com.arc.lince.services.SupervisorService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/supervisor")
@RequiredArgsConstructor
public class SupervisorController implements SupervisorControllerInterface {

    private final SupervisorService supervisorService;

    @PostMapping
    public ResponseEntity<SupervisorResponseDto> create(@RequestBody @Valid SupervisorRequestDto supervisorRequestDto) {
        Supervisor supervisor = supervisorService.create(supervisorRequestDto.toSupervisor());
        return ResponseEntity.status(HttpStatus.CREATED).body(SupervisorResponseDto.fromSupervisor(supervisor));
    }

    @PutMapping
    public ResponseEntity<SupervisorResponseDto> update(@RequestBody @Valid SupervisorRequestUpdateDto supervisorRequestUpdateDto) {
        Supervisor supervisor = supervisorService.update(supervisorRequestUpdateDto.toSupervisor());
        return ResponseEntity.ok().body(SupervisorResponseDto.fromSupervisor(supervisor));
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<SupervisorResponseDto> getId(@PathVariable("id") String id) {
        Supervisor supervisor = supervisorService.getId(id);
        return ResponseEntity.ok().body(SupervisorResponseDto.fromSupervisor(supervisor));
    }

    @GetMapping
    @PreAuthorize("hasRole('SUPERVISOR')")
    public ResponseEntity<List<SupervisorResponseDto>> getAll() {
        List<Supervisor> supervisores = supervisorService.getAll();
        if (supervisores.isEmpty()) {
            return ResponseEntity
                    .noContent()
                    .build();
        } else {
            return ResponseEntity.ok(supervisores.stream()
                    .map(SupervisorResponseDto::fromSupervisor)
                    .toList());
        }
    }

    @PatchMapping(value = "/{id}")
    public ResponseEntity<SupervisorResponseDto> patch(@PathVariable("id") String id, @RequestBody @Valid SupervisorRequestPatchDto supervisorRequestPatchDto) {
        Supervisor supervisor = supervisorService.patch(id, supervisorRequestPatchDto.toSupervisor());
        return ResponseEntity.ok().body(SupervisorResponseDto.fromSupervisor(supervisor));
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") String id) {
        supervisorService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
