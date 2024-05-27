

import com.capgemini.wsb.fitnesstracker.user.api.User;
import com.capgemini.wsb.fitnesstracker.user.api.UserDto;
import com.capgemini.wsb.fitnesstracker.user.internal.UserMapper;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class UserMapperTest {

    private final UserMapper userMapper = new UserMapper();

    @Test
    void shouldMapToDto() {
        User user = new User("John", "Doe", LocalDate.of(1990, 1, 1), "john.doe@example.com");
        user.setId(1L);

        UserDto userDto = userMapper.toDto(user);

        assertNotNull(userDto);
        assertEquals(1L, userDto.id());
        assertEquals(user.getFirstName(), userDto.firstName());
        assertEquals(user.getLastName(), userDto.lastName());
        assertEquals(user.getBirthdate(), userDto.birthdate());
        assertEquals(user.getEmail(), userDto.email());
    }

    @Test
    void shouldMapToEntity() {
        UserDto userDto = new UserDto(1L, "John", "Doe", LocalDate.of(1990, 1, 1), "john.doe@example.com");

        User user = userMapper.toEntity(userDto);

        assertNotNull(user);
        assertEquals(1L, user.getId());
        assertEquals(userDto.firstName(), user.getFirstName());
        assertEquals(userDto.lastName(), user.getLastName());
        assertEquals(userDto.birthdate(), user.getBirthdate());
        assertEquals(userDto.email(), user.getEmail());
    }
}
