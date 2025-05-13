package be.ucll.se.courses.backend.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.DataIntegrityViolationException;

import be.ucll.se.courses.backend.exception.DomainException;

import java.time.Instant;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class StudentTest {
    private Student student;
    private User user;
    private Schedule schedule;

    @BeforeEach
    void setUp() {
        user = new User("john_doe", "John", "Doe", "john@example.com", "securepassword", Role.STUDENT);
        student = new Student("S12345", user);
        Course course = new Course(
            "Computer Science", 
            "An introduction to computer science concepts and programming.", 
            1, 
            6
        );
        Lecturer lecturer = new Lecturer("Mathematics", new User(
            "john_doe",
            "John",
            "Doe",
            "john.doe@example.com",
            "securePassword123",
            Role.STUDENT
        ));
        Instant start = Instant.parse("2025-04-01T09:00:00Z");
        Instant end = Instant.parse("2025-04-01T10:30:00Z");

        Schedule schedule = new Schedule(start, end, course, lecturer);
    }

    
    @Test
    void testCreateStudent_Success() {
        assertNotNull(student);
        assertEquals("S12345", student.getStudentNumber());
        assertEquals(user, student.getUser());
    }

    
    @Test
    void testAddScheduleToStudent_Success() {
        student.getSchedules().add(schedule);
        assertEquals(1, student.getSchedules().size());
        assertTrue(student.getSchedules().contains(schedule));
    }

    
    @Test
    void testCreateStudentWithoutStudentNumber_Failure() {
        assertThrows(DomainException.class, () -> {
            new Student(null, user);
        });
    }

    
    @Test
    void testCreateStudentWithoutUser_Failure() {
        assertThrows(DomainException.class, () -> {
            new Student("S12345", null);
        });
    }
}
