package com.salas.controller;

import com.salas.dto.RoomDTO;
import com.salas.model.Room;
import com.salas.service.IRoomService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/v1/rooms")
@RequiredArgsConstructor
public class RoomController {

    private final IRoomService service;

    @Qualifier("roomMapper")
    private final ModelMapper modelMapper;

    @GetMapping
    public ResponseEntity<List<RoomDTO>> findAll() throws Exception {
        List<RoomDTO> list = service.findAll().stream().map(this::convertToDTO).toList();
        return ResponseEntity.ok(list);
    }


    private Room convertToEntity(RoomDTO dto) {
        return modelMapper.map(dto, Room.class);
    }

    private RoomDTO convertToDTO(Room obj) {
        return modelMapper.map(obj, RoomDTO.class);
    }
}
