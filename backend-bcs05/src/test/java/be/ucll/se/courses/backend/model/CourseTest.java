package be.ucll.se.courses.backend.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CourseTest {
    private Course course;

    @BeforeEach
    void setUp() {
        course = new Course("Math", "Algebra and Geometry", 1, 6);
    }

    @Test
    void testConstructor() {
        assertEquals("Math", course.getName());
        assertEquals("Algebra and Geometry", course.getDescription());
        assertEquals(1, course.getPhase());
        assertEquals(6, course.getCredits());
    }

    @Test
    void testSettersAndGetters() {
        course.setName("Physics");
        course.setDescription("Mechanics and Thermodynamics");
        course.setPhase(2);
        course.setCredits(5);
        
        assertEquals("Physics", course.getName());
        assertEquals("Mechanics and Thermodynamics", course.getDescription());
        assertEquals(2, course.getPhase());
        assertEquals(5, course.getCredits());
    }

    @Test
    void testAddLecturer() {
        Lecturer lecturer = new Lecturer("Mathematics", new User(
            "john_doe",
            "John",
            "Doe",
            "john.doe@example.com",
            "securePassword123",
            Role.STUDENT
        ));
        course.addLecturer(lecturer);
        
        List<Lecturer> lecturers = course.getLecturers();
        assertFalse(lecturers.isEmpty());
        assertEquals(1, lecturers.size());
        assertEquals(lecturer, lecturers.get(0));
    }

    @Test
    void testAddLecturer_NullCase() {
        assertThrows(NullPointerException.class, () -> course.addLecturer(null));
    }
}