package be.ucll.se.courses.backend.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import be.ucll.se.courses.backend.exception.DomainException;

import static org.assertj.core.api.Assertions.*;

class UserTest {

    private User user;

    @BeforeEach
    void setUp() {
        user = new User(
                "john_doe",
                "John",
                "Doe",
                "john.doe@example.com",
                "securePassword123",
                Role.STUDENT
        );
    }

    
    @Test
    void testUserCreation() {
        assertThat(user.getUsername()).isEqualTo("john_doe");
        assertThat(user.getFirstName()).isEqualTo("John");
        assertThat(user.getLastName()).isEqualTo("Doe");
        assertThat(user.getEmail()).isEqualTo("john.doe@example.com");
        assertThat(user.getPassword()).isEqualTo("securePassword123");
        assertThat(user.getRole()).isEqualTo(Role.STUDENT);
    }

    
    @Test
    void testGetFullName() {
        assertThat(user.getFullName()).isEqualTo("John Doe");
    }

    
    @Test
    void testSetNullEmail() {
        assertThatThrownBy(() -> user.setEmail(null))
                .isInstanceOf(DomainException.class)
                .hasMessageContaining("Email cannot be empty");
    }

    @Test
    void testSetEmptyEmail() {
        assertThatThrownBy(() -> user.setEmail(null))
                .isInstanceOf(DomainException.class)
                .hasMessageContaining("Email cannot be empty");
    }

    
    @Test
    void testSetEmptyPassword() {
        assertThatThrownBy(() -> user.setPassword(""))
                .isInstanceOf(DomainException.class)
                .hasMessageContaining("Password cannot be empty");
    }

    @Test
    void testSetNullPassword() {
        assertThatThrownBy(() -> user.setPassword(null))
                .isInstanceOf(DomainException.class)
                .hasMessageContaining("Password cannot be empty");
    }

    
    @Test
    void testSetNullRole() {
        assertThatThrownBy(() -> user.setRole(null))
                .isInstanceOf(DomainException.class)
                .hasMessageContaining("Role cannot be null");
    }
}
