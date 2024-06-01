package saad.projet.jo.service.test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import saad.projet.jo.model.User;
import saad.projet.jo.repository.UserRepository;
import saad.projet.jo.service.UserService;

import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    @Test
    public void testGetUserByEmail() {
        User user = new User();
        user.setEmail("asaad.test@test.com");
        when(userRepository.findByEmail("asaad.test@test.com")).thenReturn(Optional.of(user));

        User result = userService.findByEmail("asaad.test@test.com");

        assertNotNull(result);
        assertEquals("asaad.test@test.com", result.getEmail());
    }


}
