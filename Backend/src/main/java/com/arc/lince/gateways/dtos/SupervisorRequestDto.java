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
public class SupervisorRequestDto {

    @NotBlank
    @NotNull
    @Email(message = "Formato de e-mail inválido")
    private String email;
    @NotBlank
    @NotNull
    private String nome;
    @Pattern(regexp = "\\d{11}", message = "O celular deve conter exatamente 11 dígitos numéricos")
    private String telefone;
    @NotBlank
    private String senha;
    private String equipeId;

    public Supervisor toSupervisor(){
        return Supervisor.builder()
                .email(email)
                .nome(nome)
                .telefone(telefone)
                .equipe(Equipe.builder()
                        .id(equipeId)
                        .build())
                .usuario(Usuario.builder()
                        .username(email)
                        .password(senha)
                        .build())
                .build();
    }
}
