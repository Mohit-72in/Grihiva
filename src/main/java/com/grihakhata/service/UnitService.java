package com.grihakhata.service;

import com.grihakhata.domain.Unit;
import com.grihakhata.dto.UnitResponseDTO;

import java.util.List;

public interface UnitService {
    UnitResponseDTO create(Unit unit);
    Unit getById(Long id);
    List<UnitResponseDTO> getByBuilding(Long buildingId);
}
