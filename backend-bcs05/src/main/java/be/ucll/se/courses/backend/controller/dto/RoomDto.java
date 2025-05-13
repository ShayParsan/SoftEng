package be.ucll.se.courses.backend.controller.dto;

import be.ucll.se.courses.backend.model.Room;

import java.util.List;
import java.util.stream.Collectors;

public class RoomDto {
    private Long id;
    private String name;
    private List<ScheduleDto> schedules;

    public RoomDto(Room room) {
        this.id = room.getId();
        this.name = room.getName();
        this.schedules = room.getSchedules().stream()
                .map(ScheduleDto::new)
                .collect(Collectors.toList());
    }

    public Long getId() { return id; }
    public String getName() { return name; }
    public List<ScheduleDto> getSchedules() { return schedules; }
}

