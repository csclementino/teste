package com.arc.lince.services;

import com.arc.lince.domains.Equipe;
import com.arc.lince.exceptions.SistemaJaExistenteException;
import com.arc.lince.gateways.EquipeRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class EquipeService {

    private final EquipeRepository equipeRepository;

    public Equipe create(Equipe equipe) {
        Optional<Equipe> resultEquipe = equipeRepository.findByNome(equipe.getNome());
        if (resultEquipe.isPresent()) {
            throw new SistemaJaExistenteException("Já existe uma equipe com este nome.");
        }
        return equipeRepository.save(equipe);
    }

    public Equipe update(Equipe equipe) {
        getId(equipe.getId());
        if (equipeRepository.findByNome(equipe.getNome()).filter(s -> !s.getId().equals(equipe.getId())).isPresent()) {
            throw new SistemaJaExistenteException("Já existe uma equipe com este nome.");
        }
        return equipeRepository.save(equipe);
    }

    public void delete(String id) {
        getId(id);
        equipeRepository.deleteById(id);
    }

    public Equipe getId(String id) {
        Optional<Equipe> equipe = equipeRepository.findById(id);
        if (equipe.isEmpty()) {
            throw new EntityNotFoundException("Equipe não encontrada.");
        }
        return equipe.get();
    }

    public Equipe patch(String id, Equipe equipe) {
        Equipe equipeExistente = getId(id);
        Equipe equipeAtualizado = Equipe.builder()
                .id(equipeExistente.getId())
                .nome(equipe.getNome() != null
                        ? equipe.getNome()
                        : equipeExistente.getNome())
                .descricao(equipe.getDescricao() != null
                        ? equipe.getDescricao()
                        : equipeExistente.getDescricao())
                .build();
        if (equipeRepository.findByNome(equipeAtualizado.getNome()).filter(s -> !s.getId().equals(equipeAtualizado.getId())).isPresent()) {
            throw new SistemaJaExistenteException("Já existe uma equipe com este nome.");
        }
        return equipeRepository.save(equipeAtualizado);
    }

    public List<Equipe> getAll(){
        return equipeRepository.findAll();
    }
}
