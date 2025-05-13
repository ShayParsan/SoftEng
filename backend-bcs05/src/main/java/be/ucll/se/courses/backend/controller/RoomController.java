package be.ucll.se.courses.backend.controller;

import org.springframework.web.bind.annotation.*;


import be.ucll.se.courses.backend.model.Room;
import be.ucll.se.courses.backend.service.RoomService;

import java.util.List;


@RestController
@RequestMapping("/rooms")
public class RoomController {
    private final RoomService roomService;

    public RoomController(RoomService roomService) {
        this.roomService = roomService;
    }

    @GetMapping
    public List<Room> getAllRooms() {
        return roomService.getAllRooms();
    }

    @GetMapping("/{id}")
    public Room getRoomById(@PathVariable long id) {
        return roomService.getRoomById(id);
    }
}
