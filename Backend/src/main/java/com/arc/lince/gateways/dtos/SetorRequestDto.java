package com.arc.lince.gateways.dtos;

import com.arc.lince.domains.Equipe;
import com.arc.lince.domains.Setor;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class SetorRequestDto {

    @NotBlank
    private String nome;
    private String descricao;

    public Setor toSetor(){
        return Setor.builder()
                .nome(nome)
                .descricao(descricao)
                .build();
    }
}
