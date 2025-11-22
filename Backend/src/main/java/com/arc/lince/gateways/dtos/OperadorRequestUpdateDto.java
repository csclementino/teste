package com.arc.lince.gateways.dtos;

import com.arc.lince.domains.Equipe;
import com.arc.lince.domains.Operador;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class OperadorRequestUpdateDto {

    @NotBlank
    private String id;
    @NotBlank
    private String nome;
    @NotBlank
    private String funcao;
    @NotBlank
    private String equipeId;

    public Operador toOperador(){
        return Operador.builder()
                .id(id)
                .nome(nome)
                .funcao(funcao)
                .equipe(Equipe.builder()
                        .id(equipeId)
                        .build())
                .build();
    }
}
