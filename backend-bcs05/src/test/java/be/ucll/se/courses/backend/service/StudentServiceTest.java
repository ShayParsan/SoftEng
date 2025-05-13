package be.ucll.se.courses.backend.service;

import be.ucll.se.courses.backend.model.Role;
import be.ucll.se.courses.backend.model.Student;
import be.ucll.se.courses.backend.model.User;
import be.ucll.se.courses.backend.repository.StudentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class StudentServiceTest {

    @Mock
    private StudentRepository studentRepository;

    @InjectMocks
    private StudentService studentService;

    private Student student1;
    private Student student2;

    @BeforeEach
    void setUp() {
        User user1 = new User(
        "alicesmith",
        "Alice",
        "Smith",
        "smith@example.com",
        "securepassword",
        Role.STUDENT 
    );
    User user2 = new User(
        "bobjohnson",
        "Bob",
        "Johnson",
        "bob@example.com",
        "securepassword",
        Role.STUDENT 
    );
        student1 = new Student("S12345", user1);
        student2 = new Student("S12346", user2);
    }

    @Test
    void getAllStudents_ShouldReturnStudents_WhenStudentsExist() {
        // Arrange
        when(studentRepository.findAll()).thenReturn(Arrays.asList(student1, student2));

        // Act
        List<Student> students = studentService.getAllStudents();

        // Assert
        assertNotNull(students);
        assertEquals(2, students.size());
        
        verify(studentRepository, times(1)).findAll();
    }

    @Test
    void getAllStudents_ShouldReturnEmptyList_WhenNoStudentsExist() {
        // Arrange
        when(studentRepository.findAll()).thenReturn(Collections.emptyList());

        // Act
        List<Student> students = studentService.getAllStudents();

        // Assert
        assertNotNull(students);
        assertTrue(students.isEmpty());
        verify(studentRepository, times(1)).findAll();
    }

    @Test
    void getAllStudents_ShouldThrowException_WhenRepositoryFails() {
        // Arrange
        when(studentRepository.findAll()).thenThrow(new RuntimeException("Database error"));

        // Act & Assert
        Exception exception = assertThrows(RuntimeException.class, () -> studentService.getAllStudents());
        assertEquals("Database error", exception.getMessage());
        verify(studentRepository, times(1)).findAll();
    }
}
