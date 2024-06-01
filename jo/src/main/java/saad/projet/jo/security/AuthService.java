package saad.projet.jo.security;


import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import saad.projet.jo.constants.Role;
import saad.projet.jo.dto.LoginDto;
import saad.projet.jo.dto.RegisterDto;
import saad.projet.jo.dto.user.UpdatePasswordDto;
import saad.projet.jo.model.User;
import saad.projet.jo.repository.UserRepository;
import saad.projet.jo.service.UserService;

@Service
public class AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final UserService userService;

    public AuthService(UserRepository userRepository,
                       PasswordEncoder passwordEncoder,
                       AuthenticationManager authenticationManager,
                       UserService userService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.userService = userService;
    }

    public User signup(RegisterDto input){
        User user = new User();
        user.setFullName(input.getFullName());
        user.setEmail(input.getEmail());
        user.setRole(Role.USER);
        user.setPasword(passwordEncoder.encode(input.getPassword()));

        return userRepository.save(user);
    }

    public boolean UpdatePassword(String id, UpdatePasswordDto updatePassword){
        User userToUpdate = userService.findById(id);
        if(userToUpdate != null){
            userToUpdate.setPasword(passwordEncoder.encode(updatePassword.getPassword()));
            userRepository.save(userToUpdate);
            return true;
        }
        return false;

    }

    public User authenticate(LoginDto input){
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        input.getEmail(),
                        input.getPassword()
                )
        );
        return userRepository.findByEmail(input.getEmail()).orElseThrow();
    }
}