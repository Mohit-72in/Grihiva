package com.grihakhata.service.impl;

import com.grihakhata.domain.Unit;
import com.grihakhata.dto.UnitResponseDTO;
import com.grihakhata.repository.UnitRepository;
import com.grihakhata.service.UnitService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UnitServiceImpl implements UnitService {
    private final UnitRepository unitRepository;

    public UnitServiceImpl(UnitRepository unitRepository) {
        this.unitRepository = unitRepository;
    }

    @Override
    public UnitResponseDTO create(Unit unit) {
        Unit saved = unitRepository.save(unit);
        return toResponse(saved);
    }

    @Override
    public Unit getById(Long id) {
        return unitRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Unit not found"));
    }

    @Override
    public List<UnitResponseDTO> getByBuilding(Long buildingId) {
        return unitRepository.findByBuildingId(buildingId)
                .stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    private UnitResponseDTO toResponse(Unit unit) {
        Long buildingId = unit.getBuilding() == null ? null : unit.getBuilding().getId();
        return new UnitResponseDTO(
                unit.getId(),
                unit.getUnitNumber(),
                unit.getCategory(),
                unit.getBaseRent(),
                unit.getStatus(),
                buildingId
        );
    }
}
