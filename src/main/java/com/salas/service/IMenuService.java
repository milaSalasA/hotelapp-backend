package com.salas.service;

import com.salas.model.Menu;

import java.util.List;

public interface IMenuService extends ICRUD<Menu, Integer> {
    List<Menu> getMenusByUsername();
}
