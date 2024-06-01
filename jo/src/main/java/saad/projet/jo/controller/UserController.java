package saad.projet.jo.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import saad.projet.jo.dto.RegisterDto;
import saad.projet.jo.dto.ticket.GetTicketDto;
import saad.projet.jo.dto.user.GetUserDto;
import saad.projet.jo.dto.user.UpdatePasswordDto;
import saad.projet.jo.dto.user.UpdateUserDto;
import saad.projet.jo.model.Ticket;
import saad.projet.jo.model.User;
import saad.projet.jo.security.JwtService;
import saad.projet.jo.service.TicketService;
import saad.projet.jo.service.UserService;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService service;
    private final JwtService jwtService;
    private final TicketService ticketService;

    @Autowired
    public UserController(UserService service,
                          JwtService jwtService,
                          TicketService ticketService
    ){

        this.service  = service;
        this.jwtService = jwtService;
        this.ticketService = ticketService;
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    public ResponseEntity<User> findByToken(@RequestHeader("Authorization") String token){
        return new ResponseEntity<>(service.findByEmail(jwtService.extractEmail(token)), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/informations/{uuid}")
    public ResponseEntity<GetUserDto> findUserById(@PathVariable("uuid") String uuid, @RequestHeader("Authorization") String token){
        return new ResponseEntity<>(service.findUserById(uuid), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    @GetMapping("/tickets/{uuid}")
    public ResponseEntity<List<GetTicketDto>> findTicketById(@PathVariable("uuid") String uuid, @RequestHeader("Authorization") String token){
        return new ResponseEntity<>(ticketService.getTicketByUserId(uuid), HttpStatus.OK);
    }


    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<?> CreateUserByAdmin(@RequestHeader("Authorization") String token, @RequestBody RegisterDto user){
        if(service.createUserAdmin(user, jwtService.extractEmail(token))){
            return new ResponseEntity<>(HttpStatus.OK);
        }
        else{
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/{id}/changeRole")
    public ResponseEntity<?> toggleRoleUser(@RequestHeader("Authorization") String token, @PathVariable("id") String id){
        //  String token = authorizationHeader.substring(7);
        // System.out.println(token);
        //  System.out.println(jwtService.extractUsername(token));
        if(service.togleRole(id, jwtService.extractEmail(token))){
            return new ResponseEntity<>(HttpStatus.OK);
        }
        else{
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<?> updateUser(@RequestHeader("Authorization") String token, @PathVariable("id") String id, @Valid @RequestBody UpdateUserDto user){
        if(service.UpdateUser(user, jwtService.extractEmail(token), id)){
            return new ResponseEntity<>(HttpStatus.OK);
        }
        else{
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    @PatchMapping("/{id}/updatePassword")
    public ResponseEntity<?> updateUser(@RequestHeader("Authorization") String token, @PathVariable("id") String id, @Valid @RequestBody UpdatePasswordDto password){
        if(service.UpdatePassword(id, password)){
            return new ResponseEntity<>(HttpStatus.OK);
        }
        else{
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
