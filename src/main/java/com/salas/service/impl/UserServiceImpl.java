package com.salas.service.impl;

import com.salas.model.User;
import com.salas.repo.IGenericRepo;
import com.salas.repo.IUserRepo;
import com.salas.service.IUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl extends CRUDImpl<User, Integer> implements IUserService {

    private final IUserRepo repo;
    private final PasswordEncoder bcrypt;

    @Override
    protected IGenericRepo<User, Integer> getRepo() {
        return repo;
    }

    @Override
    public User findOneByUsername(String username) {
        return repo.findOneByUsername(username);
    }

    @Override
    public void changePassword(String username, String newPassword) {
        repo.changePassword(username, bcrypt.encode(newPassword));
    }
}