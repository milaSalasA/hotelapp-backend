package com.salas.controller;

import com.salas.dto.RoomDTO;
import com.salas.model.Room;
import com.salas.service.IRoomService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
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

    @GetMapping("/{id}")
    public ResponseEntity<RoomDTO> findById(@PathVariable Integer id) throws Exception {
        Room obj = service.findById(id);
        return ResponseEntity.ok(convertToDTO(obj));
    }

    @PostMapping
    public ResponseEntity<Void> save(@Valid @RequestBody RoomDTO dto) throws Exception {
        Room obj = service.save(convertToEntity(dto));
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(obj.getIdRoom()).toUri();
        return ResponseEntity.created(location).build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<RoomDTO> update(@PathVariable Integer id, @Valid @RequestBody RoomDTO dto) throws Exception {
        dto.setIdRoom(id);
        Room obj = service.update(id, convertToEntity(dto));
        return ResponseEntity.ok(convertToDTO(obj));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) throws Exception {
        service.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    private Room convertToEntity(RoomDTO dto) {
        return modelMapper.map(dto, Room.class);
    }

    private RoomDTO convertToDTO(Room obj) {
        return modelMapper.map(obj, RoomDTO.class);
    }
}
