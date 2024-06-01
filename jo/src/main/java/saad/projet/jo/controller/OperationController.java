package saad.projet.jo.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import saad.projet.jo.model.Category;
import saad.projet.jo.model.Operation;
import saad.projet.jo.service.CategoryService;
import saad.projet.jo.service.OperationService;

import java.util.List;

@RestController
@RequestMapping("/operations")
public class OperationController {

    private final OperationService service;

    @Autowired
    public OperationController(OperationService service){
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<Operation>> findAll(){
        return new ResponseEntity<>(service.findAllOperation(), HttpStatus.NOT_FOUND);
    }

    @PostMapping
    public ResponseEntity<Operation> create(@Valid @RequestBody Operation op) {
        return new ResponseEntity<>(service.createOperation(op), HttpStatus.CREATED);
    }

    @DeleteMapping("/{uuid}")
    public ResponseEntity<?> delete(@PathVariable String uuid) {
        if (service.deleteOperation(uuid)) {
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/{uuid}")
    public ResponseEntity<?> mettreAJourTotalement(@PathVariable String uuid,
                                                   @Valid @RequestBody Operation op){
        if (service.updateOperation(uuid, op)) {
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }



}
