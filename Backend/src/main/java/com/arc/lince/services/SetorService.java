package com.arc.lince.services;

import com.arc.lince.domains.Equipe;
import com.arc.lince.domains.Setor;
import com.arc.lince.exceptions.SistemaJaExistenteException;
import com.arc.lince.gateways.EquipeRepository;
import com.arc.lince.gateways.SetorRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SetorService {

    private final SetorRepository setorRepository;

    public Setor create(Setor setor) {
        Optional<Setor> resultSetor = setorRepository.findByNome(setor.getNome());
        if (resultSetor.isPresent()) {
            throw new SistemaJaExistenteException("Já existe um setor com este nome.");
        }
        return setorRepository.save(setor);
    }

    public Setor update(Setor setor) {
        getId(setor.getId());
        if (setorRepository.findByNome(setor.getNome()).filter(s -> !s.getId().equals(setor.getId())).isPresent()) {
            throw new SistemaJaExistenteException("Já existe um setor com este nome.");
        }
        return setorRepository.save(setor);
    }

    public void delete(String id) {
        getId(id);
        setorRepository.deleteById(id);
    }

    public Setor getId(String id) {
        Optional<Setor> setor = setorRepository.findById(id);
        if (setor.isEmpty()) {
            throw new EntityNotFoundException("Setor não encontrado.");
        }
        return setor.get();
    }

    public Setor patch(String id, Setor setor) {
        Setor setorExistente = getId(id);
        Setor setorAtualizado = Setor.builder()
                .id(setorExistente.getId())
                .nome(setor.getNome() != null
                        ? setor.getNome()
                        : setorExistente.getNome())
                .descricao(setor.getDescricao() != null
                        ? setor.getDescricao()
                        : setorExistente.getDescricao())
                .build();
        if (setorRepository.findByNome(setorAtualizado.getNome()).filter(s -> !s.getId().equals(setorAtualizado.getId())).isPresent()) {
            throw new SistemaJaExistenteException("Já existe um setor com este nome.");
        }
        return setorRepository.save(setorAtualizado);
    }

    public List<Setor> getAll(){
        return setorRepository.findAll();
    }
}
