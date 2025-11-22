package com.arc.lince.gateways;

import com.arc.lince.domains.Supervisor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SupervisorRepository extends JpaRepository<Supervisor,String> {

    Optional<Supervisor> findByEmail(String email);
}
