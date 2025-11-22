package com.arc.lince.gateways.dtos;

import com.arc.lince.domains.Camera;
import com.arc.lince.domains.Setor;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class CameraRequestPatchDto {

    private String localizacao;
    private String descricao;
    private String status;
    private String setorId;

    public Camera toCamera(){
        return Camera.builder()
                .localizacao(localizacao)
                .descricao(descricao)
                .status(status)
                .setor(Setor.builder()
                        .id(setorId)
                        .build())
                .build();
    }


}
