package com.arc.lince.services;

import com.arc.lince.domains.Equipe;
import com.arc.lince.domains.Supervisor;
import com.arc.lince.domains.Usuario;
import com.arc.lince.gateways.EquipeRepository;
import com.arc.lince.gateways.SupervisorRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SupervisorService implements SupervisorServiceInterface {

    private final SupervisorRepository supervisorRepository;
    private final EquipeRepository equipeRepository;
    private final PasswordEncoder passwordEncoder;

    public Supervisor create(Supervisor supervisor) {
        Equipe equipe = null;
        if (supervisorRepository.findByEmail(supervisor.getEmail()).isPresent()) {
            throw new IllegalArgumentException("O e-mail já está em uso.");
        }

        if (supervisor.getEquipe() != null && supervisor.getEquipe().getId() != null) {
            getEquipe(supervisor.getEquipe().getId());
            equipe = Equipe.builder().id(supervisor.getEquipe().getId()).build();
        }
        Supervisor supervisorCodificado = Supervisor.builder()
                .email(supervisor.getEmail())
                .nome(supervisor.getNome())
                .telefone(supervisor.getTelefone())
                .equipe(equipe)
                .usuario(Usuario.builder()
                        .username(supervisor.getUsuario().getUsername())
                        .password(passwordEncoder.encode(supervisor.getUsuario().getPassword()))
                        .roles(List.of("ROLE_SUPERVISOR"))
                        .build())
                .build();
        return supervisorRepository.save(supervisorCodificado);
    }

    public void getEquipe(String id) {
        Optional<Equipe> equipe = equipeRepository.findById(id);
        if (equipe.isEmpty()) {
            throw new EntityNotFoundException("Equipe não encontrada.");
        }
    }

    public Supervisor update(Supervisor supervisor) {
        getId(supervisor.getId());
        if (supervisor.getEquipe() != null && supervisor.getEquipe().getId() != null) {
            getEquipe(supervisor.getEquipe().getId());
        }
        if (supervisorRepository.findByEmail(supervisor.getEmail()).filter(c -> !c.getId().equals(supervisor.getId())).isPresent()) {
            throw new IllegalArgumentException("O e-mail já está em uso.");
        }
        return supervisorRepository.save(supervisor);
    }

    public Supervisor getId(String id) {
        Optional<Supervisor> supervisor = supervisorRepository.findById(id);
        if (supervisor.isEmpty()) {
            throw new EntityNotFoundException("Usuario não encontrado.");
        }
        return supervisor.get();
    }

    public void delete(String id) {
        getId(id);
        supervisorRepository.deleteById(id);
    }

    public Supervisor patch(String id, Supervisor supervisor) {
        Equipe equipe = null;
        if (supervisor.getEquipe() != null && supervisor.getEquipe().getId() != null) {
            getEquipe(supervisor.getEquipe().getId());
            equipe = Equipe.builder().id(supervisor.getEquipe().getId()).build();
        }
        Supervisor supervisorExistente = getId(id);
        Supervisor supervisorAtualizado = Supervisor.builder()
                .id(supervisorExistente.getId())
                .nome(supervisor.getNome() != null ? supervisor.getNome() : supervisorExistente.getNome())
                .email(supervisor.getEmail() != null ? supervisor.getEmail() : supervisorExistente.getEmail())
                .telefone(supervisor.getTelefone() != null ? supervisor.getTelefone() : supervisorExistente.getTelefone())
                .equipe(supervisor.getEquipe() != null ? equipe : supervisorExistente.getEquipe())
                .build();
        if (supervisorRepository.findByEmail(supervisorAtualizado.getEmail()).filter(c -> !c.getId().equals(supervisorAtualizado.getId())).isPresent()) {
            throw new IllegalArgumentException("O e-mail já está em uso.");
        }
        return supervisorRepository.save(supervisorAtualizado);
    }

    public List<Supervisor> getAll() {
        return supervisorRepository.findAll();
    }
}
