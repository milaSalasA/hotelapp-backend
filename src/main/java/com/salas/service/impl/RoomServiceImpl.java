package com.salas.service.impl;

import com.salas.model.Room;
import com.salas.repo.IGenericRepo;
import com.salas.repo.IRoomRepo;
import com.salas.service.IRoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RoomServiceImpl extends CRUDImpl<Room, Integer> implements IRoomService {

    private final IRoomRepo repo;

    @Override
    protected IGenericRepo<Room, Integer> getRepo() {
        return repo;
    }
}
