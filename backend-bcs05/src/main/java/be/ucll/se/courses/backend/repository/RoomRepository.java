package be.ucll.se.courses.backend.repository;

import be.ucll.se.courses.backend.model.Room;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoomRepository extends JpaRepository<Room, Long> {
}
