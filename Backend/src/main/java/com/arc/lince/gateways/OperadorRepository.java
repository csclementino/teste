package com.arc.lince.gateways;

import com.arc.lince.domains.Operador;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OperadorRepository extends JpaRepository<Operador,String> {
    List<Operador> findByEquipe_Id(String equipeId);
}
