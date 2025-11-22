package com.arc.lince.gateways.dtos;

import com.arc.lince.domains.Equipe;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class EquipeRequestUpdateDto {

    @NotBlank
    @NotNull
    private String id;
    @NotBlank
    private String nome;
    private String descricao;

    public Equipe toEquipe(){
        return Equipe.builder()
                .id(id)
                .nome(nome)
                .descricao(descricao)
                .build();
    }
}
