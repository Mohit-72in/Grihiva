package com.grihakhata.controller;

import com.grihakhata.domain.Building;
import com.grihakhata.domain.Unit;
import com.grihakhata.service.BuildingService;
import com.grihakhata.service.UnitService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/buildings/{buildingId}/units")
public class UnitController {
    private final UnitService unitService;
    private final BuildingService buildingService;

    public UnitController(UnitService unitService, BuildingService buildingService) {
        this.unitService = unitService;
        this.buildingService = buildingService;
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping
    public ResponseEntity<Unit> addUnit(@PathVariable Long buildingId, @RequestBody Unit unit) {
        Building building = buildingService.getById(buildingId);
        unit.setBuilding(building);
        return ResponseEntity.ok(unitService.create(unit));
    }

    @GetMapping
    public ResponseEntity<List<Unit>> listUnits(@PathVariable Long buildingId) {
        return ResponseEntity.ok(unitService.getByBuilding(buildingId));
    }
}
