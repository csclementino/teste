package com.arc.lince.gateways;

import com.arc.lince.domains.Camera;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CameraRepository extends JpaRepository<Camera,String> {
    List<Camera> findBySetor_Id(String setorId);

    Page<Camera> findByStatus(String status, Pageable pageable);
}
