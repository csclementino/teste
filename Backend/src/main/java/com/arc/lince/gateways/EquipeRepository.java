package com.arc.lince.gateways;

import com.arc.lince.domains.Equipe;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface EquipeRepository extends JpaRepository<Equipe,String> {
    Optional<Equipe> findByNome(String nome);
}
