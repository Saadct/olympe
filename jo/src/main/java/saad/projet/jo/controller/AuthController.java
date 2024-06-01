package saad.projet.jo.controller;


import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import saad.projet.jo.dto.LoginDto;
import saad.projet.jo.dto.LoginResponse;
import saad.projet.jo.dto.RegisterDto;
import saad.projet.jo.dto.user.UpdatePasswordDto;
import saad.projet.jo.model.User;
import saad.projet.jo.security.AuthService;
import saad.projet.jo.security.JwtService;

@RequestMapping("/auth")
@RestController
public class AuthController {

    private final JwtService jwtService;
    private final AuthService authentificationService;

    public AuthController(JwtService jwtService, AuthService authService) {
        this.jwtService = jwtService;
        this.authentificationService = authService;
    }

    @PostMapping("/signup")
    public ResponseEntity<User> register(@RequestBody @Valid RegisterDto registerUserDto) {
        User registeredUser = authentificationService.signup(registerUserDto);
        return new ResponseEntity<>(registeredUser, HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> authenticate(
            @RequestBody LoginDto loginUserDto) {

        User authentificatedUser = authentificationService.authenticate(loginUserDto);
        String jwtToken = jwtService.generateToken(authentificatedUser);

        LoginResponse loginResponse = new LoginResponse();
        loginResponse.setToken(jwtToken);
        loginResponse.setExpiresIn(jwtService.getExpirationTime());

        return new ResponseEntity<>(loginResponse, HttpStatus.OK);
    }

    @PatchMapping("/updatePassword/{id}")
    public ResponseEntity<?> updateUser(@RequestHeader("Authorization") String token, @PathVariable("id") String id, @Valid @RequestBody UpdatePasswordDto password) {
        if (authentificationService.UpdatePassword(id, password)) {
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
