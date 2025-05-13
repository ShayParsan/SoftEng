package be.ucll.se.courses.backend.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class LecturerTest {
    private Lecturer lecturer;
    private User user;

    @BeforeEach
    void setUp() {
        user = new User(); 
        lecturer = new Lecturer("Computer Science", user);
    }

    @Test
    void testConstructor() {
        assertThat(lecturer.getExpertise()).isEqualTo("Computer Science");
        assertThat(lecturer.getUser()).isEqualTo(user);
        assertThat(lecturer.getCourses()).isEmpty();
    }

    @Test
    void testSettersAndGetters() {
        lecturer.setId(1L);
        assertThat(lecturer.getId()).isEqualTo(1L);

        lecturer.setExpertise("Mathematics");
        assertThat(lecturer.getExpertise()).isEqualTo("Mathematics");

        User newUser = new User();
        lecturer.setUser(newUser);
        assertThat(lecturer.getUser()).isEqualTo(newUser);
    }

    @Test
    void testCoursesManagement() {
        Course course1 = new Course(); 
        Course course2 = new Course();

        lecturer.getCourses().add(course1);
        lecturer.getCourses().add(course2);

        assertThat(lecturer.getCourses()).containsExactly(course1, course2);
    }
}