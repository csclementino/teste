package com.arc.lince.gateways;

import com.arc.lince.gateways.dtos.EquipeRequestDto;
import com.arc.lince.gateways.dtos.EquipeRequestPatchDto;
import com.arc.lince.gateways.dtos.EquipeRequestUpdateDto;
import com.arc.lince.gateways.dtos.response.EquipeResponseDto;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface EquipeControllerInterface {
    ResponseEntity<EquipeResponseDto> create(EquipeRequestDto equipeRequestDto);
    ResponseEntity<EquipeResponseDto> update(EquipeRequestUpdateDto equipeRequestUpdateDto);
    ResponseEntity<EquipeResponseDto> getId(String id);
    ResponseEntity<List<EquipeResponseDto>> getAll();
    ResponseEntity<EquipeResponseDto> patch(String id, EquipeRequestPatchDto equipeRequestPatchDto);
    ResponseEntity<Void> delete(String id);

}
