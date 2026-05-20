package com.grihakhata.service;

import com.grihakhata.domain.Building;

import java.util.List;

public interface BuildingService {
    Building create(Building building);
    Building getById(Long id);
    List<Building> getAll();
}
