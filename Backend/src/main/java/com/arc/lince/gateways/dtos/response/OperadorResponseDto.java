package com.arc.lince.gateways.dtos.response;

import com.arc.lince.domains.Equipe;
import com.arc.lince.domains.Operador;

public record OperadorResponseDto(
        String nome,
        String funcao
) {
    public static OperadorResponseDto fromOperador(Operador operador){
        return new OperadorResponseDto(
                operador.getNome(),
                operador.getFuncao()
        );
    }
}
