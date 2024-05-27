

import com.capgemini.wsb.fitnesstracker.user.api.User;
import com.capgemini.wsb.fitnesstracker.user.internal.UserRepository;
import com.capgemini.wsb.fitnesstracker.user.internal.UserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserServiceImpl userService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void shouldCreateUser() {
        User user = new User("John", "Doe", LocalDate.of(1990, 1, 1), "john.doe@example.com");

        when(userRepository.save(any(User.class))).thenReturn(user);

        User createdUser = userService.createUser(user);

        assertEquals(user, createdUser);
        verify(userRepository, times(1)).save(user);
    }

    @Test
    void shouldUpdateUser() {
        User user = new User("John", "Doe", LocalDate.of(1990, 1, 1), "john.doe@example.com");
        user.setId(1L);

        when(userRepository.save(any(User.class))).thenReturn(user);

        User updatedUser = userService.updateUser(user);

        assertEquals(user, updatedUser);
        verify(userRepository, times(1)).save(user);
    }

    @Test
    void shouldDeleteUser() {
        doNothing().when(userRepository).deleteById(1L);

        userService.deleteUser(1L);

        verify(userRepository, times(1)).deleteById(1L);
    }

    @Test
    void shouldGetUserById() {
        User user = new User("John", "Doe", LocalDate.of(1990, 1, 1), "john.doe@example.com");
        user.setId(1L);

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        Optional<User> foundUser = userService.getUser(1L);

        assertTrue(foundUser.isPresent());
        assertEquals(user, foundUser.get());
        verify(userRepository, times(1)).findById(1L);
    }

    @Test
    void shouldGetUserByEmail() {
        User user = new User("John", "Doe", LocalDate.of(1990, 1, 1), "john.doe@example.com");

        when(userRepository.findByEmail("john.doe@example.com")).thenReturn(Optional.of(user));

        Optional<User> foundUser = userService.getUserByEmail("john.doe@example.com");

        assertTrue(foundUser.isPresent());
        assertEquals(user, foundUser.get());
        verify(userRepository, times(1)).findByEmail("john.doe@example.com");
    }

    @Test
    void shouldFindAllUsers() {
        User user = new User("John", "Doe", LocalDate.of(1990, 1, 1), "john.doe@example.com");

        when(userRepository.findAll()).thenReturn(Collections.singletonList(user));

        List<User> users = userService.findAllUsers();

        assertEquals(1, users.size());
        assertEquals(user, users.get(0));
        verify(userRepository, times(1)).findAll();
    }
}
