package com.arc.lince.gateways.dtos;

import com.arc.lince.domains.Equipe;
import com.arc.lince.domains.Setor;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class SetorRequestUpdateDto {

    @NotBlank
    private String id;
    @NotBlank
    private String nome;
    private String descricao;

    public Setor toSetor(){
        return Setor.builder()
                .id(id)
                .nome(nome)
                .descricao(descricao)
                .build();
    }
}
