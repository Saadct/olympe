package saad.projet.jo.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
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

import java.util.ArrayList;
import java.util.List;

@CrossOrigin(origins = "http://localhost:3000")
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

    @GetMapping("/me")
    public ResponseEntity<GetUserDto> findByToken(@RequestHeader("Authorization") String token){
        return new ResponseEntity<>(service.findByToken(jwtService.extractEmail(token)), HttpStatus.OK);
    }

    @GetMapping("/check-connected")
    public ResponseEntity<?> checkConected(@RequestHeader("Authorization") String token){
        if(service.checkIfLoggin(jwtService.extractEmail(token))){
            return new ResponseEntity<>(HttpStatus.OK);
        }else{
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/check-connected-admin")
    public ResponseEntity<?> checkConectedAdmin(@RequestHeader("Authorization") String token){
        if(service.checkIfLoggin(jwtService.extractEmail(token))){
            return new ResponseEntity<>(HttpStatus.OK);

        }else{
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping()
    public ResponseEntity<List<GetUserDto>> findAll(@RequestHeader("Authorization") String token){
        return new ResponseEntity<>(service.findAll(), HttpStatus.OK);
    }

    @GetMapping("/paginated/{page}/{size}")
    public ResponseEntity<List<Object>> findAllPaginated(@RequestHeader("Authorization") String token,
                                                             @PathVariable("page") int page,
                                                             @PathVariable("size") int size
                                                             ){
        List<GetUserDto> users = service.findAllPaginated(page, size);
        long totalPage = service.getTotalPage(size);
        List<Object> responseData = new ArrayList<>();
        responseData.add(users);
        responseData.add(totalPage);
        return new ResponseEntity<>(responseData, HttpStatus.OK);
    }

    @PutMapping("/me")
    public ResponseEntity<?> updateByToken(@RequestHeader("Authorization") String token, @Valid @RequestBody UpdateUserDto user){
        if(service.UpdateUserByToken(user, jwtService.extractEmail(token))){
            return new ResponseEntity<>(HttpStatus.OK);
        }else{
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/informations/{uuid}")
    public ResponseEntity<GetUserDto> findUserById(@PathVariable("uuid") String uuid, @RequestHeader("Authorization") String token){
        return new ResponseEntity<>(service.findUserById(uuid), HttpStatus.OK);
    }

    @GetMapping("/tickets/{uuid}")
    public ResponseEntity<List<GetTicketDto>> findTicketById(@PathVariable("uuid") String uuid, @RequestHeader("Authorization") String token){
        return new ResponseEntity<>(ticketService.getTicketByUserId(uuid), HttpStatus.OK);
    }

    @GetMapping("/tickets/me/{page}/{size}")
    public ResponseEntity<List<Object>> findTicketByToken(
            @PathVariable("page") int page,
            @PathVariable("size") int size,
            @RequestHeader("Authorization") String token){
        int totalPage = ticketService.getTotalPage(jwtService.extractEmail(token), size );
        Page<GetTicketDto> pageResult = ticketService.getTicketByUserEmailPaginated(jwtService.extractEmail(token), size, page);
        List<Object> responseData = new ArrayList<>();
        responseData.add(pageResult.getContent());
        responseData.add(totalPage);
        return new ResponseEntity<>(responseData, HttpStatus.OK);
    }


    @GetMapping("/tickets/me/available/{page}/{size}")
    public ResponseEntity<List<Object>> findTicketByTokenAvailable(
            @PathVariable("page") int page,
            @PathVariable("size") int size,
            @RequestHeader("Authorization") String token){
        int totalPage = ticketService.getTotalPageAvailable(jwtService.extractEmail(token), size );
        Page<GetTicketDto> pageResult = ticketService.getTicketByUserEmailPaginatedAvailable(jwtService.extractEmail(token), size, page);
        List<Object> responseData = new ArrayList<>();
        responseData.add(pageResult.getContent());
        responseData.add(totalPage);
        return new ResponseEntity<>(responseData, HttpStatus.OK);
    }

    @GetMapping("/tickets/me/notavailable/{page}/{size}")
    public ResponseEntity<List<Object>> findTicketByTokenNotAvailable(
            @PathVariable("page") int page,
            @PathVariable("size") int size,
            @RequestHeader("Authorization") String token){
        int totalPage = ticketService.getTotalPageNotAvailable(jwtService.extractEmail(token), size );
        Page<GetTicketDto> pageResult = ticketService.getTicketByUserEmailPaginatedNotAvailable(jwtService.extractEmail(token), size, page);
        List<Object> responseData = new ArrayList<>();
        responseData.add(pageResult.getContent());
        responseData.add(totalPage);
        return new ResponseEntity<>(responseData, HttpStatus.OK);
    }

    @DeleteMapping("/ticket/me/cancel/{uuid}")
    public ResponseEntity<?> cancelByToken(@RequestHeader("Authorization") String token,
                                           @PathVariable("uuid") String uuid){
        if(ticketService.CancelTicketByToken(jwtService.extractEmail(token), uuid)){
            return new ResponseEntity<>(HttpStatus.OK);
        }else{
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/ticket/cancel-subscription/{uuid}")
    public ResponseEntity<?> cancelById(@PathVariable("uuid") String uuid){
        if(ticketService.CancelTicketById(uuid)){
            return new ResponseEntity<>(HttpStatus.OK);
        }else{
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }


    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/tickets/{uuid}/{page}/{size}")
    public ResponseEntity<List<Object>> findTicketById(
            @PathVariable("page") int page,
            @PathVariable("size") int size,
            @PathVariable("uuid") String uuid){
        int totalPage = ticketService.getTotalPageById(uuid, size );
        Page<GetTicketDto> pageResult = ticketService.getTicketByUserIdPaginated(uuid, size, page);
        List<Object> responseData = new ArrayList<>();
        responseData.add(pageResult.getContent());
        responseData.add(totalPage);
        return new ResponseEntity<>(responseData, HttpStatus.OK);
    }


    @GetMapping("/ticket/checkregistration/{uuid}")
    public ResponseEntity<?> checkIfRegistration(@RequestHeader("Authorization") String token, @PathVariable("uuid") String uuid){
        if(ticketService.checkRegistration(jwtService.extractEmail(token), uuid)){
            return new ResponseEntity<>(HttpStatus.OK);
        }else{
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/ticket/subscription/{uuid}")
    public ResponseEntity<?> subscription(@RequestHeader("Authorization") String token, @PathVariable("uuid") String uuid){
        if(ticketService.createTicket(jwtService.extractEmail(token), uuid)){
            return new ResponseEntity<>(HttpStatus.OK);
        }else{
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping
    public ResponseEntity<?> CreateUserByAdmin(@RequestHeader("Authorization") String token, @RequestBody RegisterDto user){
        if(service.createUserAdmin(user, jwtService.extractEmail(token))){
            return new ResponseEntity<>(HttpStatus.OK);
        }
        else{
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PatchMapping("/change-role/{id}")
    public ResponseEntity<?> toggleRoleUser(@RequestHeader("Authorization") String token, @PathVariable("id") String id){
        if(service.togleRole(id, jwtService.extractEmail(token))){
            return new ResponseEntity<>(HttpStatus.OK);
        }
        else{
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateUser(@RequestHeader("Authorization") String token, @PathVariable("id") String id, @Valid @RequestBody UpdateUserDto user){
        if(service.UpdateUser(user, jwtService.extractEmail(token), id)){
            return new ResponseEntity<>(HttpStatus.OK);
        }
        else{
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    @PatchMapping("/{id}/updatePassword")
    public ResponseEntity<?> updatePassword(@RequestHeader("Authorization") String token, @PathVariable("id") String id, @Valid @RequestBody UpdatePasswordDto password){
        if(service.UpdatePassword(id, password)){
            return new ResponseEntity<>(HttpStatus.OK);
        }
        else{
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
