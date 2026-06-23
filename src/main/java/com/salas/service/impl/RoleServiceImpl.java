package com.salas.service.impl;

import com.salas.model.Role;
import com.salas.repo.IGenericRepo;
import com.salas.repo.IRoleRepo;
import com.salas.service.IRoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RoleServiceImpl extends CRUDImpl<Role, Integer> implements IRoleService {

    private final IRoleRepo repo;

    @Override
    protected IGenericRepo<Role, Integer> getRepo() {
        return repo;
    }
}
