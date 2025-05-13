package be.ucll.se.courses.backend.service;

import be.ucll.se.courses.backend.exception.NotFoundException;
import be.ucll.se.courses.backend.model.Course;
import be.ucll.se.courses.backend.repository.CourseRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CourseServiceTest {

    @Mock
    private CourseRepository courseRepository; 

    @InjectMocks
    private CourseService courseService; 

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this); 
    }

    
    @Test
    void testGetCourseById_HappyPath() {
        
        Course course = new Course("Math", "Algebra and Geometry", 1, 6);
        course.setId(1L);
        course.setName("Mathematics");

        
        when(courseRepository.findById(1L)).thenReturn(Optional.of(course));

        
        Course result = courseService.getCourseById(1L);

        
        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("Mathematics", result.getName());

        
        verify(courseRepository, times(1)).findById(1L);
    }

    
    @Test
    void testGetCourseById_CourseNotFound_ShouldThrowException() {
        
        when(courseRepository.findById(2L)).thenReturn(Optional.empty());

        
        NotFoundException thrown = assertThrows(NotFoundException.class, () -> {
            courseService.getCourseById(2L);
        });

        
        assertEquals("Course with id 2 not found", thrown.getMessage());

        
        verify(courseRepository, times(1)).findById(2L);
    }
}
