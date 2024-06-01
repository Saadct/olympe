package saad.projet.jo.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import saad.projet.jo.dto.evenement.CreateEvent;
import saad.projet.jo.dto.ticket.CreateTicket;
import saad.projet.jo.model.Evenement;
import saad.projet.jo.security.JwtService;
import saad.projet.jo.service.EvenementService;
import saad.projet.jo.service.TicketService;

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


    @GetMapping
    public ResponseEntity<List<Evenement>> findAll(){
        return new ResponseEntity<>(service.findAllEvenement(), HttpStatus.FOUND);
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

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<?> create(@Valid @RequestBody CreateEvent evenement, @RequestHeader("Authorization") String token) {
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

    @PreAuthorize("hasRole('ADMIN')")
    @PatchMapping("/{event_id}/improveSeats")
    public ResponseEntity<String> improveSeats(@PathVariable("event_id") String eventId,
                                             @Valid @RequestBody Integer seats,
                                             @RequestHeader("Authorization") String token) {
        //       return new ResponseEntity<>(ticketService.buyTickets(eventId,tickets), HttpStatus.CREATED);
        if (service.updateTotalSeats(eventId, seats, jwtService.extractEmail(token))) {
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Il n'y a plus de places disponibles pour cet événement.", HttpStatus.NOT_ACCEPTABLE);
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

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{uuid}")
    public ResponseEntity<?> delete(@PathVariable String uuid) {
        if (service.deleteEvenement(uuid)) {
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{uuid}")
    public ResponseEntity<?> mettreAJourTotalement(@PathVariable String uuid,
                                                   @Valid @RequestBody CreateEvent evenement,
                                                   @RequestHeader("Authorization") String token
    ){
        if (service.updateEvenement(uuid, evenement, jwtService.extractEmail(token))) {
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }


}
