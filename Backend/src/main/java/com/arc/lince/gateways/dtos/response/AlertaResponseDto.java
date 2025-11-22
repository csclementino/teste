package com.arc.lince.gateways.dtos.response;

import com.arc.lince.domains.Alerta;
import com.arc.lince.domains.Camera;

import java.time.LocalDateTime;

public record AlertaResponseDto(
        String motivo,
        String nivelAlerta,
        LocalDateTime dataHora
) {
    public static AlertaResponseDto fromAlerta(Alerta alerta){
        return new AlertaResponseDto(
                alerta.getMotivo(),
                alerta.getNivelAlerta(),
                alerta.getDataHora()
        );
    }
}
