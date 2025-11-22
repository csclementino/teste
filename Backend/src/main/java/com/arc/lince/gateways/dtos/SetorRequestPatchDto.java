package com.arc.lince.gateways.dtos;

import com.arc.lince.domains.Equipe;
import com.arc.lince.domains.Setor;
import lombok.Data;

@Data
public class SetorRequestPatchDto {

    private String nome;
    private String descricao;

    public Setor toSetor(){
        return Setor.builder()
                .nome(nome)
                .descricao(descricao)
                .build();
    }
}
