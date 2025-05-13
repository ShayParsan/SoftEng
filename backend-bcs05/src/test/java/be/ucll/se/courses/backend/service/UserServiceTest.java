package be.ucll.se.courses.backend.service;
import be.ucll.se.courses.backend.model.Role;

import be.ucll.se.courses.backend.controller.dto.AuthenticationResponse;
import be.ucll.se.courses.backend.controller.dto.UserInput;
import be.ucll.se.courses.backend.exception.CoursesException;
import be.ucll.se.courses.backend.model.User;
import be.ucll.se.courses.backend.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private JwtService jwtService;

    @InjectMocks
    private UserService userService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    
    @Test
    void testGetAllUsers_HappyPath() {
        
        User user1 = new User("user1", "John", "Doe", "john@example.com", "password", Role.STUDENT);
        User user2 = new User("user2", "Jane", "Doe", "jane@example.com", "password", Role.ADMIN);

        when(userRepository.findAll()).thenReturn(List.of(user1, user2));

        
        List<User> users = userService.getAllUsers();

        
        assertEquals(2, users.size());
        assertEquals("user1", users.get(0).getUsername());
        assertEquals("user2", users.get(1).getUsername());

        verify(userRepository, times(1)).findAll();
    }

    
    @Test
    void testAuthenticate_HappyPath() {
        
        String username = "user1";
        String password = "securePass";
        User user = new User(
    "user1",
    "John",
    "Doe",
    "johndoe@example.com",
    "securepassword",
    Role.STUDENT 
);

        UsernamePasswordAuthenticationToken authToken =
                new UsernamePasswordAuthenticationToken(username, password);

        UserDetailsImpl userDetails = new UserDetailsImpl(user);

        when(authenticationManager.authenticate(authToken))
                .thenReturn(new UsernamePasswordAuthenticationToken(userDetails, password));

        when(jwtService.generateToken(user)).thenReturn("mock-jwt-token");

        
        AuthenticationResponse response = userService.authenticate(username, password);

        
        assertNotNull(response);
        assertEquals("Authentication successful.", response.message());
        assertEquals("mock-jwt-token", response.token());
        assertEquals(username, response.username());

        verify(authenticationManager, times(1)).authenticate(authToken);
        verify(jwtService, times(1)).generateToken(user);
    }

    
    @Test
    void testAuthenticate_Unhappy_WrongPassword() {
        
        String username = "user1";
        String password = "wrongPass";

        UsernamePasswordAuthenticationToken authToken =
                new UsernamePasswordAuthenticationToken(username, password);

        when(authenticationManager.authenticate(authToken))
                .thenThrow(new BadCredentialsException("Bad credentials"));

       
        assertThrows(BadCredentialsException.class, () -> userService.authenticate(username, password));

        verify(authenticationManager, times(1)).authenticate(authToken);
    }

    
    @Test
    void testSignup_HappyPath() {
        
        UserInput userInput = new UserInput(
            "johndoe",
            "John",
            "johndoe",
            "johndoe@example.com",
            "securepassword",
            Role.STUDENT 
        );
        User user = new User(
        "johndoe",
        "John",
        "johndoe",
        "johndoe@example.com",
        "securepassword",
        Role.STUDENT 
    );

        when(userRepository.existsByUsername(userInput.username())).thenReturn(false);
        when(passwordEncoder.encode(userInput.password())).thenReturn("hashedPassword");
        when(userRepository.save(any(User.class))).thenReturn(user);

        
        User registeredUser = userService.signup(userInput);

        
        assertNotNull(registeredUser);
        assertEquals("johndoe", registeredUser.getUsername());
        assertEquals("securepassword", registeredUser.getPassword());

        verify(userRepository, times(1)).existsByUsername("johndoe");
        verify(passwordEncoder, times(1)).encode("John");
        verify(userRepository, times(1)).save(any(User.class));
    }

    
    @Test
    void testSignup_Unhappy_UsernameAlreadyExists() {
        
        UserInput userInput = new UserInput(
    "johndoe",
    "securepassword",
    "John",
    "Doe",
    "johndoe@example.com",
    Role.STUDENT 
    );

        when(userRepository.existsByUsername(userInput.username())).thenReturn(true);

        
        CoursesException thrown = assertThrows(CoursesException.class, () -> userService.signup(userInput));

        
        assertEquals("Username is already in use.", thrown.getMessage());

        verify(userRepository, times(1)).existsByUsername("johndoe");
        verify(userRepository, never()).save(any(User.class));
    }
}

