package saad.projet.jo.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import saad.projet.jo.model.Category;
import saad.projet.jo.service.CategoryService;

import java.util.List;

@RestController
@RequestMapping("/categories")
public class CategoryController {

    private final CategoryService service;

    @Autowired
    public CategoryController(CategoryService service){
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<Category>> findAll(){
        return new ResponseEntity<>(service.findAllCategory(), HttpStatus.NOT_FOUND);
    }

    @PostMapping
    public ResponseEntity<Category> create(@Valid @RequestBody Category category) {
        return new ResponseEntity<>(service.createCategory(category), HttpStatus.CREATED);
    }

    @DeleteMapping("/{uuid}")
    public ResponseEntity<?> delete(@PathVariable String uuid) {
        if (service.deleteCategory(uuid)) {
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/{uuid}")
    public ResponseEntity<?> mettreAJourTotalement(@PathVariable String uuid,
                                                   @Valid @RequestBody Category category){
        if (service.update(uuid, category)) {
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

}
