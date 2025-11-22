package com.arc.lince.gateways;

import com.arc.lince.gateways.dtos.SupervisorRequestDto;
import com.arc.lince.gateways.dtos.SupervisorRequestPatchDto;
import com.arc.lince.gateways.dtos.SupervisorRequestUpdateDto;
import com.arc.lince.gateways.dtos.response.SupervisorResponseDto;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface SupervisorControllerInterface {

    ResponseEntity<SupervisorResponseDto> create(SupervisorRequestDto supervisorRequestDto);
    ResponseEntity<SupervisorResponseDto> update(SupervisorRequestUpdateDto supervisorRequestUpdateDto);
    ResponseEntity<SupervisorResponseDto> getId(String id);
    ResponseEntity<List<SupervisorResponseDto>> getAll();
    ResponseEntity<SupervisorResponseDto> patch(String id, SupervisorRequestPatchDto supervisorRequestPatchDto);
    ResponseEntity<Void> delete(String id);
}
