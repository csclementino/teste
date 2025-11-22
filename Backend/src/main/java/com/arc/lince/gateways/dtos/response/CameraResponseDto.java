package com.arc.lince.gateways.dtos.response;

import com.arc.lince.domains.Camera;
import com.arc.lince.domains.Equipe;

public record CameraResponseDto(
      String localizacao,
      String descricao,
      String status
) {
    public static CameraResponseDto fromCamera(Camera camera){
        return new CameraResponseDto(
                camera.getLocalizacao(),
                camera.getDescricao(),
                camera.getStatus()
        );
    }
}
