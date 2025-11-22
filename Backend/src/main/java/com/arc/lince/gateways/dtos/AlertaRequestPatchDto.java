package com.arc.lince.gateways.dtos;

import com.arc.lince.domains.Alerta;
import com.arc.lince.domains.Camera;
import com.arc.lince.domains.Operador;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class AlertaRequestPatchDto {

    private String motivo;
    private String nivelAlerta;
    private String cameraId;
    private String operadorId;

    public Alerta toAlerta(){
        return Alerta.builder()
                .motivo(motivo)
                .nivelAlerta(nivelAlerta)
                .camera(Camera.builder()
                        .id(cameraId)
                        .build())
                .operador(Operador.builder()
                        .id(operadorId)
                        .build())
                .build();
    }
}
