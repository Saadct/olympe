package saad.projet.jo.controller;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import saad.projet.jo.model.Category;
import saad.projet.jo.model.Spectator;
import saad.projet.jo.service.SpectatorService;

import java.util.List;

@RestController
@RequestMapping("/spectators")
public class SpectatorController {

    private final SpectatorService service;

    public SpectatorController(SpectatorService service){
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<Spectator>> findAll(){
        return new ResponseEntity<>(service.findAllSpectator(), HttpStatus.NOT_FOUND);
    }

    @PostMapping
    public ResponseEntity<Spectator> create(@Valid @RequestBody Spectator spectator) {
        return new ResponseEntity<>(service.createSpectator(spectator), HttpStatus.CREATED);
    }

    @DeleteMapping("/{uuid}")
    public ResponseEntity<?> delete(@PathVariable String uuid) {
        if (service.deleteSpectator(uuid)) {
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/{uuid}")
    public ResponseEntity<?> mettreAJourTotalement(@PathVariable String uuid,
                                                   @Valid @RequestBody Spectator sp){
        if (service.updateSpectator(uuid, sp)) {
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
