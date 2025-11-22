package com.arc.lince.gateways.dtos;

import com.arc.lince.domains.Equipe;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class EquipeRequestPatchDto {

    private String nome;
    private String descricao;

    public Equipe toEquipe(){
        return Equipe.builder()
                .nome(nome)
                .descricao(descricao)
                .build();
    }
}
