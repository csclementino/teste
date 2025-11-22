package com.arc.lince.services;

import com.arc.lince.domains.Equipe;
import com.arc.lince.domains.Operador;
import com.arc.lince.gateways.EquipeRepository;
import com.arc.lince.gateways.OperadorRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class OperadorService {

    private final OperadorRepository operadorRepository;
    private final EquipeRepository equipeRepository;

    public Operador create(Operador operador) {
        getEquipe(operador.getEquipe().getId());
        return operadorRepository.save(operador);
    }

    public Operador update(Operador operador) {
        getId(operador.getId());
        getEquipe(operador.getEquipe().getId());
        return operadorRepository.save(operador);
    }

    public void delete(String id) {
        getId(id);
        operadorRepository.deleteById(id);
    }

    public void getEquipe(String id) {
        Optional<Equipe> equipe = equipeRepository.findById(id);
        if (equipe.isEmpty()) {
            throw new EntityNotFoundException("Equipe não encontrada.");
        }
    }

    public Operador getId(String id) {
        Optional<Operador> operador = operadorRepository.findById(id);
        if (operador.isEmpty()) {
            throw new EntityNotFoundException("Operador não encontrado.");
        }
        return operador.get();
    }

    public List<Operador> getOperadoresEquipe(String equipeId) {
        getEquipe(equipeId);
        return operadorRepository.findByEquipe_Id(equipeId);
    }

    public Operador patch(String id, Operador operador) {
        Operador operadorExistente = getId(id);

        if(operador.getEquipe() != null && operador.getEquipe().getId() != null) {
            getEquipe(operador.getEquipe().getId());
        }

        Operador operadorAtualizado = Operador.builder()
                .id(operadorExistente.getId())
                .nome(operador.getNome() != null
                        ? operador.getNome()
                        : operadorExistente.getNome())
                .funcao(operador.getFuncao() != null
                        ? operador.getFuncao()
                        : operadorExistente.getFuncao())
                .funcao(operador.getFuncao() != null
                        ? operador.getFuncao()
                        : operadorExistente.getFuncao())
                .equipe(Equipe.builder()
                        .id(operador.getEquipe().getId() != null
                                ? operador.getEquipe().getId()
                                : operadorExistente.getEquipe().getId())
                        .build())
                .build();
        return operadorRepository.save(operadorAtualizado);
    }

    public List<Operador> getAll(){
        return operadorRepository.findAll();
    }
}
