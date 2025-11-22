package com.arc.lince.gateways.dtos;

import com.arc.lince.domains.Equipe;
import com.arc.lince.domains.Operador;
import com.arc.lince.domains.Supervisor;
import com.arc.lince.domains.Usuario;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;


@Data
public class OperadorRequestDto {

    @NotBlank
    private String nome;
    @NotBlank
    private String funcao;
    @NotBlank
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
