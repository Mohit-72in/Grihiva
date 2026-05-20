package com.grihakhata.service.impl;

import com.grihakhata.domain.Building;
import com.grihakhata.repository.BuildingRepository;
import com.grihakhata.service.BuildingService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BuildingServiceImpl implements BuildingService {
    private final BuildingRepository buildingRepository;

    public BuildingServiceImpl(BuildingRepository buildingRepository) {
        this.buildingRepository = buildingRepository;
    }

    @Override
    public Building create(Building building) {
        return buildingRepository.save(building);
    }

    @Override
    public Building getById(Long id) {
        return buildingRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Building not found"));
    }

    @Override
    public List<Building> getAll() {
        return buildingRepository.findAll();
    }
}
