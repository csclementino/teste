package com.arc.lince.gateways;

import com.arc.lince.domains.Alerta;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AlertaRepository extends JpaRepository<Alerta,String> {
    List<Alerta> findByOperador_Id(String operadorId);

    List<Alerta> findByCamera_Setor_Id(String setorId);
}
