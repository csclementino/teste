package com.arc.lince.gateways;

import com.arc.lince.domains.Setor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SetorRepository extends JpaRepository<Setor,String> {
    Optional<Setor> findByNome(String nome);
}
