package com.arc.lince.gateways.dtos.response;

import com.arc.lince.domains.Equipe;
import com.arc.lince.domains.Setor;

public record SetorResponseDto(
        String nome,
        String descricao
) {
    public static SetorResponseDto fromSetor(Setor setor){
        return new SetorResponseDto(
                setor.getNome(),
                setor.getDescricao()
        );
    }
}
