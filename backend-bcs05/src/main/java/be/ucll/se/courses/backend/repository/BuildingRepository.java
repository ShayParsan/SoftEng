package be.ucll.se.courses.backend.repository;

import be.ucll.se.courses.backend.model.Building;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BuildingRepository extends JpaRepository<Building, Long> {
}
