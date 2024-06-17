package saad.projet.jo.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import saad.projet.jo.dto.ticket.GetTicketDto;
import saad.projet.jo.model.Ticket;
import saad.projet.jo.security.JwtService;
import saad.projet.jo.service.TicketService;

import java.util.ArrayList;
import java.util.List;

@CrossOrigin(origins = "http://localhost:3000")
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
/*
    TODO futur

    @GetMapping
    public ResponseEntity<List<Ticket>> findAll(){
        List<Ticket> tickets = service.findAllTicket();
        return new ResponseEntity<>(tickets, HttpStatus.OK);
    }

    @GetMapping("/{uuid}")
    public ResponseEntity<Ticket> findById(@PathVariable("uuid") String uuid) {
        Ticket ticket = service.findTicketById(uuid);
        if (ticket == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(ticket, HttpStatus.OK);
        }
    }
*/

    @PostMapping
    public ResponseEntity<Ticket> create(@Valid @RequestBody Ticket t) {
        return new ResponseEntity<>(service.createTicket(t), HttpStatus.CREATED);
    }


    @DeleteMapping("/delete/{uuid}")
    public ResponseEntity<?> delete(@PathVariable String uuid) {
        if (service.deleteTicket(uuid)) {
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    /*
    TODO futur
    @PutMapping("/{uuid}")
    public ResponseEntity<?> mettreAJourTotalement(@PathVariable String uuid,
                                                   @Valid @RequestBody Ticket t){
        if (service.updateTicket(uuid, t)) {
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
*/
    @GetMapping("/paginated/{uuid}/{page}/{size}")
    public ResponseEntity<List<Object>> findTicketByEvenement(
            @PathVariable("page") int page,
            @PathVariable("size") int size,
            @PathVariable("uuid") String uuid){
        int totalPage = service.getTotalPageByEvenement(uuid, size );
        Page<GetTicketDto> pageResult = service.findByEvenement(uuid, size, page);
        List<Object> responseData = new ArrayList<>();
        responseData.add(pageResult.getContent());
        responseData.add(totalPage);
        return new ResponseEntity<>(responseData, HttpStatus.OK);
    }
}
