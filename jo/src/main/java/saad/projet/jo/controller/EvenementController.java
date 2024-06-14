package saad.projet.jo.controller;

import jakarta.annotation.security.PermitAll;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import saad.projet.jo.dto.evenement.CreateEventDto;
import saad.projet.jo.dto.evenement.UpdateEventDto;
import saad.projet.jo.dto.ticket.CreateTicket;
import saad.projet.jo.model.Evenement;
import saad.projet.jo.security.JwtService;
import saad.projet.jo.service.EvenementService;
import saad.projet.jo.service.TicketService;

import java.util.ArrayList;
import java.util.List;
import org.springframework.security.access.prepost.PreAuthorize;

@RestController
@RequestMapping("/evenements")
public class EvenementController {

    private final EvenementService service;
    private final TicketService ticketService;
    private final JwtService jwtService;

    @Autowired
    public EvenementController(EvenementService service, TicketService ticketService, JwtService jwtService){
        this.service = service;
        this.ticketService = ticketService;
        this.jwtService = jwtService;
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @GetMapping
    public ResponseEntity<List<Evenement>> findAll(){
        return new ResponseEntity<>(service.findAllEvenement(), HttpStatus.OK);
    }

    @PermitAll
    @CrossOrigin(origins = "http://localhost:3000")
    @GetMapping("/details/{uuid}")
    public ResponseEntity<Evenement> findById(@PathVariable("uuid") String uuid){
        return new ResponseEntity<>(service.findEvenementById(uuid), HttpStatus.OK);
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @GetMapping("/paginated/{page}/{size}")
    public ResponseEntity<List<Object>> findAllPaginated(
            @PathVariable int page,
            @PathVariable int size) {
        int totalPage = service.getTotalPage(size);
        Page<Evenement> pageResult = service.findPaginatedEvenements(page, size);
        List<Object> responseData = new ArrayList<>();
        responseData.add(pageResult.getContent());
        responseData.add(totalPage);
        return new ResponseEntity<>(responseData, HttpStatus.OK);
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @GetMapping("/paginatedByCategory/{page}/{size}/{id}")
    public ResponseEntity<List<Object>> findAllPaginatedByCategory(
            @PathVariable("page") int page,
            @PathVariable("size") int size,
            @PathVariable("id") String id
    ) {
        int totalPage = service.getTotalPageByCategory(id, size);
        Page<Evenement> pageResult = service.findByCategoryPaginatedEvenements(id, page, size);
        List<Object> responseData = new ArrayList<>();
        responseData.add(pageResult.getContent());
        responseData.add(totalPage);
        return new ResponseEntity<>(responseData, HttpStatus.OK);
    }



    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    @GetMapping("/available")
    public ResponseEntity<List<Evenement>> findAllEvenementAvailable(){
        return new ResponseEntity<>(service.findAllEvenementByState("Available"), HttpStatus.OK);
    }
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    @GetMapping("/{id}")
    public ResponseEntity<Evenement> findById(@PathVariable("id") String id, @RequestHeader("Authorization") String authorizationHeader){
        return new ResponseEntity<>(service.findEvenementById(id), HttpStatus.FOUND);
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<?> create(@Valid @RequestBody CreateEventDto evenement, @RequestHeader("Authorization") String token) {
        if(service.createEvenement(evenement, jwtService.extractEmail(token))){
            return new ResponseEntity<>(HttpStatus.OK);
        }else{
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

/*
    @PostMapping
    public ResponseEntity<Evenement> create(@Valid @RequestBody Evenement evenement, @RequestHeader("Authorization") String token) {
        if(service.createEvenement(evenement)){
            return new ResponseEntity<>(HttpStatus.OK);
        }else{
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
 */
    @PreAuthorize("hasRole('USER')")
    @PostMapping("/{event_id}/acheterTicket")
    public ResponseEntity<String> buyTicket(@PathVariable("event_id") String eventId,
                                            @Valid @RequestBody CreateTicket ticket,
                                            @RequestHeader("Authorization") String token) {
        if (ticketService.buyTicket(eventId, ticket, jwtService.extractEmail(token))) {
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Il n'y a plus de places disponibles pour cet événement.", HttpStatus.NOT_FOUND);
        }

    }


    @CrossOrigin(origins = "http://localhost:3000")
    @GetMapping("/checkavailable/{uuid}")
    public ResponseEntity<?> checkIfRegistration(@PathVariable("uuid") String uuid){
        if(service.checkAvailableEvent(uuid)){
            return new ResponseEntity<>(HttpStatus.OK);
        }else{
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PreAuthorize("hasRole('USER')")
    @PostMapping("/{event_id}/acheterLotTicket")
    public ResponseEntity<String> buyLotTicket(@PathVariable("event_id") String eventId,
                                               @Valid @RequestBody List<CreateTicket> tickets,
                                               @RequestHeader("Authorization") String token) {
        if (ticketService.buyTickets(eventId, tickets, jwtService.extractEmail(token))) {
            return new ResponseEntity<>(HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>("Il n'y a plus de places disponibles pour cet événement.", HttpStatus.NOT_ACCEPTABLE);
        }

    }
    @PreAuthorize("hasRole('USER')")
    @PostMapping("/{event_id}/bookTicket")
    public ResponseEntity<String> bookTicket(@PathVariable("event_id") String eventId,
                                               @Valid @RequestBody CreateTicket ticket,
                                               @RequestHeader("Authorization") String token) {
        //       return new ResponseEntity<>(ticketService.buyTickets(eventId,tickets), HttpStatus.CREATED);
        if (ticketService.bookTicket(eventId, ticket, jwtService.extractEmail(token))) {
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Il n'y a plus de places disponibles pour cet événement.", HttpStatus.NOT_ACCEPTABLE);
        }

    }


    @CrossOrigin(origins = "http://localhost:3000")
    @PreAuthorize("hasRole('ADMIN')")
    @PatchMapping("/{id}/updateSeats")
    public ResponseEntity<?> improveSeats(@PathVariable("id") String eventId,
                                             @Valid @RequestBody Integer seats,
                                             @RequestHeader("Authorization") String token) {
        if (service.updateTotalSeats(eventId, seats, jwtService.extractEmail(token))) {
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>( HttpStatus.NOT_ACCEPTABLE);
        }
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PatchMapping("/{event_id}/cancelEvent")
    public ResponseEntity<String> cancelEvent(@PathVariable("event_id") String eventId,
                                               @RequestHeader("Authorization") String token) {
        //       return new ResponseEntity<>(ticketService.buyTickets(eventId,tickets), HttpStatus.CREATED);
        if (service.cancelEvent(eventId, jwtService.extractEmail(token))) {
            return new ResponseEntity<>(HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>("Il n'y a plus de places disponibles pour cet événement.", HttpStatus.NOT_ACCEPTABLE);
        }

    }

    @CrossOrigin(origins = "http://localhost:3000")
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{uuid}")
    public ResponseEntity<?> delete(@PathVariable String uuid) {
        if (service.deleteEvenement(uuid)) {
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{uuid}")
    public ResponseEntity<?> mettreAJourTotalement(@PathVariable String uuid,
                                                   @Valid @RequestBody UpdateEventDto evenement,
                                                   @RequestHeader("Authorization") String token
    ){
        if (service.updateEvenement(uuid, evenement, jwtService.extractEmail(token))) {
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }


}
