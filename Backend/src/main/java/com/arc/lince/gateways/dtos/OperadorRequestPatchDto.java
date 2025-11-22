package com.arc.lince.gateways.dtos;

import com.arc.lince.domains.Equipe;
import com.arc.lince.domains.Operador;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class OperadorRequestPatchDto {

    private String nome;
    private String funcao;
    private String equipeId;

    public Operador toOperador(){
        return Operador.builder()
                .nome(nome)
                .funcao(funcao)
                .equipe(Equipe.builder()
                        .id(equipeId)
                        .build())
                .build();
    }
}
