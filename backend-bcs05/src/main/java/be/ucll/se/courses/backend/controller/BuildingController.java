package be.ucll.se.courses.backend.controller;

import be.ucll.se.courses.backend.controller.dto.BuildingDto;
import be.ucll.se.courses.backend.model.Building;
import be.ucll.se.courses.backend.service.BuildingService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/buildings")
public class BuildingController {

    private final BuildingService buildingService;

    public BuildingController(BuildingService buildingService) {
        this.buildingService = buildingService;
    }

    @GetMapping
    public List<BuildingDto> getBuildingsWithRoomsAndSchedules() {
        List<Building> buildings = buildingService.getAllBuildings();
        return buildings.stream()
                .map(BuildingDto::new)
                .collect(Collectors.toList());
    }
}
