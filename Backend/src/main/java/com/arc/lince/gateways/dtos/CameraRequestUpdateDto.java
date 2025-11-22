package com.arc.lince.gateways.dtos;

import com.arc.lince.domains.Camera;
import com.arc.lince.domains.Setor;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class CameraRequestUpdateDto {

    @NotBlank
    private String id;
    @NotBlank
    private String localizacao;
    private String descricao;
    @NotBlank
    private String status;
    @NotBlank
    private String setorId;

    public Camera toCamera(){
        return Camera.builder()
                .id(id)
                .localizacao(localizacao)
                .descricao(descricao)
                .status(status)
                .setor(Setor.builder()
                        .id(setorId)
                        .build())
                .build();
    }
}
