package com.arc.lince.gateways.dtos;

import com.arc.lince.domains.Equipe;
import com.arc.lince.domains.Supervisor;
import com.arc.lince.domains.Usuario;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class EquipeRequestDto {

    @NotBlank
    private String nome;
    private String descricao;

    public Equipe toEquipe(){
        return Equipe.builder()
                .nome(nome)
                .descricao(descricao)
                .build();
    }
}
