package com.salas.controller;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/v1/menus")
@RequiredArgsConstructor
public class MenuController {

    private final IMenuService service;

    @Qualifier("defaultMapper")
    private final ModelMapper mapper;

    @PostMapping("/user")
    public ResponseEntity<List<MenuDTO>> getMenusByUser(){
        List<MenuDTO> menus = service.getMenusByUsername().stream().map(e -> mapper.map(e, MenuDTO.class)).toList();
        return ResponseEntity.ok(menus);
    }
}
