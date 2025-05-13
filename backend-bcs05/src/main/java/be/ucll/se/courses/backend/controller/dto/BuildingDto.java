package be.ucll.se.courses.backend.controller.dto;

import be.ucll.se.courses.backend.model.Building;

import java.util.List;
import java.util.stream.Collectors;

public class BuildingDto {
    private Long id;
    private String name;
    private List<RoomDto> rooms;

    public BuildingDto(Building building) {
        this.id = building.getId();
        this.name = building.getName();
        this.rooms = building.getRooms().stream()
                .map(RoomDto::new)
                .collect(Collectors.toList());
    }

    // getters
    public Long getId() { return id; }
    public String getName() { return name; }
    public List<RoomDto> getRooms() { return rooms; }
}
