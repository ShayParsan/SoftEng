package be.ucll.se.courses.backend.service;

import be.ucll.se.courses.backend.model.Building;
import be.ucll.se.courses.backend.model.Room;
import be.ucll.se.courses.backend.repository.BuildingRepository;
import be.ucll.se.courses.backend.repository.RoomRepository;

import java.util.List;

import org.springframework.stereotype.Service;

@Service
public class BuildingService {
    private final BuildingRepository buildingRepository;

    public BuildingService (BuildingRepository buildingRepository) {
        this.buildingRepository = buildingRepository;
    }

    public List<Building> getAllBuildings() {
        return buildingRepository.findAll();
    }
}
