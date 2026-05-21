package com.grihakhata.service.impl;

import com.grihakhata.domain.Role;
import com.grihakhata.domain.Unit;
import com.grihakhata.domain.UnitStatus;
import com.grihakhata.domain.User;
import com.grihakhata.dto.UnitResponseDTO;
import com.grihakhata.repository.UnitRepository;
import com.grihakhata.repository.UserRepository;
import com.grihakhata.service.UnitService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UnitServiceImpl implements UnitService {
    private final UnitRepository unitRepository;
    private final UserRepository userRepository;

    public UnitServiceImpl(UnitRepository unitRepository, UserRepository userRepository) {
        this.unitRepository = unitRepository;
        this.userRepository = userRepository;
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

    @Override
    public UnitResponseDTO assignRenter(Long unitId, Long renterId) {
        Unit unit = unitRepository.findById(unitId)
                .orElseThrow(() -> new EntityNotFoundException("Unit not found"));
        User renter = userRepository.findById(renterId)
                .orElseThrow(() -> new EntityNotFoundException("Renter not found"));
        if (renter.getRole() != Role.ROLE_RENTER) {
            throw new IllegalArgumentException("User is not a renter");
        }

        unit.setCurrentRenter(renter);
        unit.setStatus(UnitStatus.OCCUPIED);
        Unit saved = unitRepository.save(unit);
        return toResponse(saved);
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
