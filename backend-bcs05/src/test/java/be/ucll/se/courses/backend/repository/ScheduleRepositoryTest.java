package be.ucll.se.courses.backend.repository;

import be.ucll.se.courses.backend.model.*;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.Instant;
import java.util.List;

import static org.assertj.core.api.Assertions.*;

@DataJpaTest
class ScheduleRepositoryTest {

    @Autowired
    private ScheduleRepository scheduleRepository;

    @Autowired
    private LecturerRepository lecturerRepository;

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private UserRepository userRepository;

    @PersistenceContext
    private EntityManager entityManager;

    private Lecturer testLecturer;
    private Course testCourse;
    private Schedule testSchedule;

    @BeforeEach
    void setUp() {
        
        User user = new User("lecturer1", "John", "Doe", "john.doe@example.com", "password", Role.LECTURER);
        userRepository.save(user);

        
        testLecturer = new Lecturer("Computer Science", user);
        lecturerRepository.save(testLecturer);

        
        testCourse = new Course("Math", "Algebra and Geometry", 1, 6);
        courseRepository.save(testCourse);

        Instant startTime = Instant.parse("2025-03-20T10:00:00Z");
        Instant endTime = Instant.parse("2025-03-20T12:00:00Z");
        
        testSchedule = new Schedule(startTime, endTime, testCourse, testLecturer);
        scheduleRepository.save(testSchedule);

        
        entityManager.flush();
    }

    
    @Test
    void testFindByLecturer_User_Username() {
        List<Schedule> schedules = scheduleRepository.findByLecturer_User_Username("lecturer1");

        assertThat(schedules).hasSize(1);
        assertThat(schedules.get(0).getLecturer().getUser().getUsername()).isEqualTo("lecturer1");
    }

    
    @Test
    void testFindByLecturer_User_Username_NotFound() {
        List<Schedule> schedules = scheduleRepository.findByLecturer_User_Username("unknown_user");

        assertThat(schedules).isEmpty();
    }

    
    @Test
    void testExistsByCourse_IdAndLecturer_Id() {
        boolean exists = scheduleRepository.existsByCourse_IdAndLecturer_Id(testCourse.getId(), testLecturer.getId());

        assertThat(exists).isTrue();
    }

    
    @Test
    void testExistsByCourse_IdAndLecturer_Id_NotFound() {
        boolean exists = scheduleRepository.existsByCourse_IdAndLecturer_Id(999L, 999L);

        assertThat(exists).isFalse();
    }
}
