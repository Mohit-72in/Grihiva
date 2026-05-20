package com.grihakhata.service.impl;

import com.grihakhata.domain.Unit;
import com.grihakhata.repository.UnitRepository;
import com.grihakhata.service.UnitService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UnitServiceImpl implements UnitService {
    private final UnitRepository unitRepository;

    public UnitServiceImpl(UnitRepository unitRepository) {
        this.unitRepository = unitRepository;
    }

    @Override
    public Unit create(Unit unit) {
        return unitRepository.save(unit);
    }

    @Override
    public Unit getById(Long id) {
        return unitRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Unit not found"));
    }

    @Override
    public List<Unit> getByBuilding(Long buildingId) {
        return unitRepository.findByBuildingId(buildingId);
    }
}
