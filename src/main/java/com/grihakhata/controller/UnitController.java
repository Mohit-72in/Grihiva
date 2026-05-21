package com.grihakhata.controller;

import com.grihakhata.domain.Building;
import com.grihakhata.domain.Unit;
import com.grihakhata.domain.UnitStatus;
import com.grihakhata.dto.UnitCreateRequest;
import com.grihakhata.dto.UnitResponseDTO;
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

import jakarta.validation.Valid;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/units")
@Tag(name = "Units", description = "Unit and room management")
@SecurityRequirement(name = "bearerAuth")
public class UnitController {
    private final UnitService unitService;
    private final BuildingService buildingService;

    public UnitController(UnitService unitService, BuildingService buildingService) {
        this.unitService = unitService;
        this.buildingService = buildingService;
    }

    @Operation(summary = "Create unit", description = "Add a unit/room to a building.")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @PostMapping
    public ResponseEntity<UnitResponseDTO> addUnit(@Valid @RequestBody UnitCreateRequest request) {
        Building building = buildingService.getById(request.getBuildingId());
        Unit unit = Unit.builder()
                .building(building)
                .unitNumber(request.getUnitNumber())
                .category(request.getCategory())
                .baseRent(request.getBaseRent())
                .rentOverride(request.getRentOverride())
                .electricityRateOverride(request.getElectricityRateOverride())
                .meterNumber(request.getMeterNumber())
                .status(request.getStatus() == null ? UnitStatus.VACANT : request.getStatus())
                .expectedVacateDate(request.getExpectedVacateDate())
                .active(request.getActive() == null || request.getActive())
                .build();
        return ResponseEntity.ok(unitService.create(unit));
    }

    @Operation(summary = "List units by building", description = "List all units for a building.")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @GetMapping("/{buildingId}")
    public ResponseEntity<List<UnitResponseDTO>> listUnits(@PathVariable Long buildingId) {
        return ResponseEntity.ok(unitService.getByBuilding(buildingId));
    }

    @Operation(summary = "Assign renter", description = "Assign a renter to a unit and mark it occupied.")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @PostMapping("/{unitId}/assign-renter/{renterId}")
    public ResponseEntity<UnitResponseDTO> assignRenter(@PathVariable Long unitId, @PathVariable Long renterId) {
        return ResponseEntity.ok(unitService.assignRenter(unitId, renterId));
    }
}
