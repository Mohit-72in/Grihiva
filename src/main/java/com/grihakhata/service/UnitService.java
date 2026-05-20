package com.grihakhata.service;

import com.grihakhata.domain.Unit;

import java.util.List;

public interface UnitService {
    Unit create(Unit unit);
    Unit getById(Long id);
    List<Unit> getByBuilding(Long buildingId);
}
