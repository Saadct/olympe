package saad.projet.jo.controller;

import jakarta.annotation.security.PermitAll;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import saad.projet.jo.dto.event.CreateEventDto;
import saad.projet.jo.dto.event.UpdateEventDto;
import saad.projet.jo.dto.ticket.CreateTicket;
import saad.projet.jo.model.Event;
import saad.projet.jo.security.JwtService;
import saad.projet.jo.service.EventService;
import saad.projet.jo.service.TicketService;

import java.util.ArrayList;
import java.util.List;
import org.springframework.security.access.prepost.PreAuthorize;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/evenements")
public class EventController {

    private final EventService service;
    private final TicketService ticketService;
    private final JwtService jwtService;

    @Autowired
    public EventController(EventService service, TicketService ticketService, JwtService jwtService){
        this.service = service;
        this.ticketService = ticketService;
        this.jwtService = jwtService;
    }
/*
    @CrossOrigin(origins = "http://localhost:3000")
    @GetMapping
    public ResponseEntity<List<Event>> findAll(){
        return new ResponseEntity<>(service.findAllEvenement(), HttpStatus.OK);
    }
*/

    @GetMapping("/details/{uuid}")
    public ResponseEntity<Event> findById(@PathVariable("uuid") String uuid){
        return new ResponseEntity<>(service.findEvenementById(uuid), HttpStatus.OK);
    }

    @GetMapping("/paginated/{page}/{size}")
    public ResponseEntity<List<Object>> findAllPaginated(
            @PathVariable int page,
            @PathVariable int size) {
        int totalPage = service.getTotalPage(size);
        Page<Event> pageResult = service.findPaginatedEvenements(page, size);
        List<Object> responseData = new ArrayList<>();
        responseData.add(pageResult.getContent());
        responseData.add(totalPage);
        return new ResponseEntity<>(responseData, HttpStatus.OK);
    }

    @GetMapping("/paginatedByCategory/{page}/{size}/{id}")
    public ResponseEntity<List<Object>> findAllPaginatedByCategory(
            @PathVariable("page") int page,
            @PathVariable("size") int size,
            @PathVariable("id") String id
    ) {
        int totalPage = service.getTotalPageByCategory(id, size);
        Page<Event> pageResult = service.findByCategoryPaginatedEvenements(id, page, size);
        List<Object> responseData = new ArrayList<>();
        responseData.add(pageResult.getContent());
        responseData.add(totalPage);
        return new ResponseEntity<>(responseData, HttpStatus.OK);
    }

    /*
    @GetMapping("/{id}")
    public ResponseEntity<Event> findById(@PathVariable("id") String id, @RequestHeader("Authorization") String authorizationHeader){
        return new ResponseEntity<>(service.findEvenementById(id), HttpStatus.FOUND);
    }
*/

    @PostMapping("/create")
    public ResponseEntity<?> create(@Valid @RequestBody CreateEventDto evenement, @RequestHeader("Authorization") String token) {
        if(service.createEvenement(evenement, jwtService.extractEmail(token))){
            return new ResponseEntity<>(HttpStatus.OK);
        }else{
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }


    @GetMapping("/checkavailable/{uuid}")
    public ResponseEntity<?> checkIfRegistration(@PathVariable("uuid") String uuid){
        if(service.checkAvailableEvent(uuid)){
            return new ResponseEntity<>(HttpStatus.OK);
        }else{
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/delete/{uuid}")
    public ResponseEntity<?> delete(@PathVariable String uuid) {
        if (service.deleteEvenement(uuid)) {
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/update/{uuid}")
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


    @GetMapping("/check-category/{uuid}")
    public ResponseEntity<?> checkIfCategoryHasNoEvent(@PathVariable String uuid) {
        if (service.checkIfCategoryHasNoEvent(uuid)) {
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
