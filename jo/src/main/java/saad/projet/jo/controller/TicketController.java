package saad.projet.jo.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import saad.projet.jo.model.Ticket;
import saad.projet.jo.security.JwtService;
import saad.projet.jo.service.TicketService;

import java.util.List;

@RestController
@RequestMapping("/tickets")
public class TicketController {

    private final TicketService service;
    private final JwtService jwtService;

    @Autowired
    public TicketController(TicketService service, JwtService jwtService){

        this.service = service;
        this.jwtService = jwtService;
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    public ResponseEntity<List<Ticket>> findAll(){
        List<Ticket> tickets = service.findAllTicket();
        return new ResponseEntity<>(tickets, HttpStatus.OK);
    }


    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/{uuid}")
    public ResponseEntity<Ticket> findById(@PathVariable("uuid") String uuid) {
        Ticket ticket = service.findTicketById(uuid);
        if (ticket == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(ticket, HttpStatus.OK);
        }
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<Ticket> create(@Valid @RequestBody Ticket t) {
        return new ResponseEntity<>(service.createTicket(t), HttpStatus.CREATED);
    }
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{uuid}")
    public ResponseEntity<?> delete(@PathVariable String uuid) {
        if (service.deleteTicket(uuid)) {
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{uuid}")
    public ResponseEntity<?> mettreAJourTotalement(@PathVariable String uuid,
                                                   @Valid @RequestBody Ticket t){
        if (service.updateTicket(uuid, t)) {
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    @PatchMapping("/{ticketId}/paidBookTicket")
    public ResponseEntity<String> bookTicket(@PathVariable("ticketId") String ticketId,
                                             @RequestHeader("Authorization") String token) {
        if (service.paidBookTicket(ticketId,jwtService.extractEmail(token))) {
            return new ResponseEntity<>(HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>("Il n'y a plus de places disponibles pour cet événement.", HttpStatus.NOT_ACCEPTABLE);
        }

    }
}
