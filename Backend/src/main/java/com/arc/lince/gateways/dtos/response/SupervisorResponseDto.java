package com.arc.lince.gateways.dtos.response;


import com.arc.lince.domains.Supervisor;

public record SupervisorResponseDto(
        String nome,
        String email
){
    public static SupervisorResponseDto fromSupervisor(Supervisor supervisor){
        return new SupervisorResponseDto(
                supervisor.getNome(),
                supervisor.getEmail()
        );
    }
}