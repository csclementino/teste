package com.arc.lince.services;

import com.arc.lince.domains.Supervisor;

import java.util.List;

public interface SupervisorServiceInterface {

    Supervisor create(Supervisor supervisor);
    Supervisor update(Supervisor supervisor);
    Supervisor getId(String id);
    void delete(String id);
    Supervisor patch(String id, Supervisor supervisor);
    List<Supervisor> getAll();

}
