package saad.projet.jo.controller;


import jakarta.validation.Valid;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import saad.projet.jo.constants.Role;
import saad.projet.jo.dto.LoginDto;
import saad.projet.jo.dto.LoginResponse;
import saad.projet.jo.dto.RegisterDto;
import saad.projet.jo.dto.user.UpdatePasswordDto;
import saad.projet.jo.model.User;
import saad.projet.jo.security.AuthService;
import saad.projet.jo.security.JwtService;
import saad.projet.jo.service.UserService;

@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/auth")
@RestController
public class AuthController {

    private final JwtService jwtService;
    private final AuthService authentificationService;
    private final UserService userService;
    public AuthController(JwtService jwtService, AuthService authService,
                          UserService userService) {
        this.jwtService = jwtService;
        this.authentificationService = authService;
        this.userService = userService;
    }

    @PostMapping("/signup")
    public ResponseEntity<User> register(@RequestBody @Valid RegisterDto registerUserDto) {
        User userExisting = userService.findByEmail(registerUserDto.getEmail());
        if (userExisting  != null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        User registeredUser = authentificationService.signup(registerUserDto);
        return new ResponseEntity<>(registeredUser, HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> authenticate(
            @RequestBody LoginDto loginUserDto) {

        User authentificatedUser = authentificationService.authenticate(loginUserDto);
        String jwtToken = jwtService.generateToken(authentificatedUser);

        Role role = userService.findByEmail(loginUserDto.getEmail()).getRole();
        String id = userService.findByEmail(loginUserDto.getEmail()).getId();

        LoginResponse loginResponse = new LoginResponse();
        loginResponse.setRole(role);
        loginResponse.setId(id);
        loginResponse.setToken(jwtToken);
        loginResponse.setExpiresIn(jwtService.getExpirationTime());

        //return new ResponseEntity<>(loginResponse, HttpStatus.OK);
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.AUTHORIZATION, jwtToken);
        headers.add(HttpHeaders.ACCESS_CONTROL_EXPOSE_HEADERS, HttpHeaders.AUTHORIZATION); // Exposer l'en-tête d'autorisation

        return ResponseEntity.ok()
                .headers(headers)
                .body(loginResponse);

    }

    @CrossOrigin(origins = "http://localhost:3000")
    @PatchMapping("/updatePassword")
    public ResponseEntity<?> updateUserPassword(@RequestHeader("Authorization") String token, @Valid @RequestBody UpdatePasswordDto password) {
        if (authentificationService.UpdatePassword(jwtService.extractEmail(token), password)) {
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
