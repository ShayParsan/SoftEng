package be.ucll.se.courses.backend.service;

import be.ucll.se.courses.backend.exception.NotFoundException;
import be.ucll.se.courses.backend.model.Lecturer;
import be.ucll.se.courses.backend.model.User;
import be.ucll.se.courses.backend.model.Role;
import be.ucll.se.courses.backend.repository.LecturerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class LecturerServiceTest {

    @Mock
    private LecturerRepository lecturerRepository;

    @InjectMocks
    private LecturerService lecturerService;

    private Lecturer lecturer1;
    private Lecturer lecturer2;

    @BeforeEach
void setUp() {
    User user1 = new User(
        "johndoe",
        "John",
        "Doe",
        "johndoe@example.com",
        "securepassword",
        Role.STUDENT 
    );
    user1.setId(1L);
    user1.setUsername("john_doe");

    User user2 = new User(
        "johndoe",
        "John",
        "Doe",
        "johndoe@example.com",
        "securepassword",
        Role.STUDENT 
    );
    user2.setId(2L);
    user2.setUsername("jane_smith");

    lecturer1 = new Lecturer("Computer Science", user1);
    lecturer1.setId(1L);

    lecturer2 = new Lecturer("Mathematics", user2);
    lecturer2.setId(2L);
}

    @Test
    void getAllLecturers_HappyCase() {
        // Given
        when(lecturerRepository.findAll()).thenReturn(Arrays.asList(lecturer1, lecturer2));

        // When
        List<Lecturer> lecturers = lecturerService.getAllLecturers();

        // Then
        assertNotNull(lecturers);
        assertEquals(2, lecturers.size());
        

        verify(lecturerRepository, times(1)).findAll();
    }

    @Test
    void getLecturerById_HappyCase() {
        // Given
        when(lecturerRepository.findById(1L)).thenReturn(Optional.of(lecturer1));

        // When
        Lecturer foundLecturer = lecturerService.getLecturerById(1L);

        
        assertNotNull(foundLecturer);
        //assertEquals("John Doe", foundLecturer.getName());
        verify(lecturerRepository, times(1)).findById(1L);
    }

    @Test
    void getLecturerById_UnhappyCase_NotFound() {
        // Given
        when(lecturerRepository.findById(999L)).thenReturn(Optional.empty());

        // When & Then
        NotFoundException exception = assertThrows(NotFoundException.class, 
            () -> lecturerService.getLecturerById(999L));

        assertEquals("Lecturer with id 999 not found", exception.getMessage());
        verify(lecturerRepository, times(1)).findById(999L);
    }
}
