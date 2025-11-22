package com.arc.lince.gateways.dtos;

import com.arc.lince.domains.Alerta;
import com.arc.lince.domains.Camera;
import com.arc.lince.domains.Operador;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class AlertaRequestUpdateDto {

    @NotBlank
    private String id;
    @NotBlank
    private String motivo;
    @NotBlank
    private String nivelAlerta;
    @NotBlank
    private String cameraId;
    @NotBlank
    private String operadorId;

    public Alerta toAlerta(){
        return Alerta.builder()
                .id(id)
                .motivo(motivo)
                .nivelAlerta(nivelAlerta)
                .dataHora(LocalDateTime.now())
                .camera(Camera.builder()
                        .id(cameraId)
                        .build())
                .operador(Operador.builder()
                        .id(operadorId)
                        .build())
                .build();
    }
}
