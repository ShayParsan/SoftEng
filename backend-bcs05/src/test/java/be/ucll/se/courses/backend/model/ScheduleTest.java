package be.ucll.se.courses.backend.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.Instant;

import static org.junit.jupiter.api.Assertions.*;

class ScheduleTest {
    private Course course;
    private Lecturer lecturer;
    private Schedule schedule;

    @BeforeEach
    void setUp() {
        User user = new User(
            "john_doe",
            "John",
            "Doe",
            "john.doe@example.com",
            "securePassword123",
            Role.STUDENT
        );

        lecturer = new Lecturer("Computer Science", user);
        course = new Course("Software Engineering", "Learn about software development", 2, 6);

        Instant startTime = Instant.parse("2025-03-20T10:00:00Z");
        Instant endTime = Instant.parse("2025-03-20T12:00:00Z");

        schedule = new Schedule(startTime, endTime, course, lecturer);
    }

    @Test
    void testScheduleCreation() {
        assertNotNull(schedule);
        assertEquals(course, schedule.getCourse());
        assertEquals(lecturer, schedule.getLecturer());
        assertEquals(Instant.parse("2025-03-20T10:00:00Z"), schedule.getStart());
        assertEquals(Instant.parse("2025-03-20T12:00:00Z"), schedule.getEnd());
    }

    @Test
    void testAddingStudentToSchedule() {
        User studentUser = new User(
            "john_doe",
            "John",
            "Doe",
            "john.doe@example.com",
            "securePassword123",
            Role.STUDENT
        );
        Student student = new Student("S123456", studentUser);

        schedule.addStudent(student);

        assertEquals(1, schedule.getStudents().size());
        assertTrue(schedule.getStudents().contains(student));
    }

    @Test
    void testSetStartAndEnd() {
        Instant newStart = Instant.parse("2025-03-21T09:00:00Z");
        Instant newEnd = Instant.parse("2025-03-21T11:00:00Z");

        schedule.setStart(newStart);
        schedule.setEnd(newEnd);

        assertEquals(newStart, schedule.getStart());
        assertEquals(newEnd, schedule.getEnd());
    }
}
