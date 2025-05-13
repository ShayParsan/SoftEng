package be.ucll.se.courses.backend.service;

import be.ucll.se.courses.backend.exception.NotFoundException;
import be.ucll.se.courses.backend.model.Lecturer;
import be.ucll.se.courses.backend.model.Room;
import be.ucll.se.courses.backend.repository.RoomRepository;

import java.util.List;

import org.springframework.stereotype.Service;

@Service
public class RoomService {
     private final RoomRepository roomRepository;

     public RoomService (RoomRepository roomRepository) {
        this.roomRepository = roomRepository;
     }

     public List<Room> getAllRooms() {
        return roomRepository.findAll();
    }

    public Room getRoomById(long id) {
        return roomRepository.findById(id).orElseThrow(
                () -> new NotFoundException("Room with id " + id + " not found"));
    }
}
