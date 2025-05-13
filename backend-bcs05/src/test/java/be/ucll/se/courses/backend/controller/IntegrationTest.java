package be.ucll.se.courses.backend.controller;

import be.ucll.se.courses.backend.controller.dto.AuthenticationRequest;
import be.ucll.se.courses.backend.controller.dto.UserInput;
import be.ucll.se.courses.backend.model.Role;
import be.ucll.se.courses.backend.model.User;
import be.ucll.se.courses.backend.repository.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
class IntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ObjectMapper objectMapper;

    private User testUser;

    @Test
    void testSignup_WeakPassword() throws Exception {
        UserInput weakUser = new UserInput(
                "newUser",
                "",
                "Alice",
                "Smith",
                "alice@example.com",
                Role.STUDENT
        );
        mockMvc.perform(post("/users/signup")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(weakUser)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void testLogin_HappyPath() throws Exception {
        AuthenticationRequest request = new AuthenticationRequest("admin", "admin123");

        mockMvc.perform(post("/users/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.token").exists());
    }

    @Test
    void testLogin_WrongPassword() throws Exception {
        AuthenticationRequest request = new AuthenticationRequest("user1", "wrongPassword");

        mockMvc.perform(post("/users/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void testLogin_NonExistentUser() throws Exception {
        AuthenticationRequest request = new AuthenticationRequest("unknownUser", "password");

        mockMvc.perform(post("/users/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isUnauthorized());
    }
}
