package saad.projet.jo.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import saad.projet.jo.dto.category.CategoryDto;
import saad.projet.jo.dto.ticket.GetTicketDto;
import saad.projet.jo.model.Category;
import saad.projet.jo.service.CategoryService;

import java.util.ArrayList;
import java.util.List;

@CrossOrigin(origins = "http://localhost:3000")
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
        return new ResponseEntity<>(service.findAllCategory(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Category> findById(@PathVariable("id") String id){
        return new ResponseEntity<>(service.findCategoryById(id), HttpStatus.OK);
    }


    @GetMapping("/paginated/{page}/{size}")
    public ResponseEntity<List<Object>> findAllPaginated(@PathVariable("page") int page, @PathVariable("size") int size ){
        int totalPage = service.getTotalPage(size);
        Page<Category> pageResult = service.findAllCategoryPaginated(page, size);
        List<Object> responseData = new ArrayList<>();
        responseData.add(pageResult.getContent());
        responseData.add(totalPage);
        return new ResponseEntity<>(responseData, HttpStatus.OK);
    }

    @PostMapping("/create")
    public ResponseEntity<?> create(@Valid @RequestBody CategoryDto category) {
        if(service.createCategory(category)){
            return new ResponseEntity<>(HttpStatus.OK);
        }else{
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }


    @DeleteMapping("/delete/{uuid}")
    public ResponseEntity<?> delete(@PathVariable String uuid) {
        if (service.deleteCategory(uuid)) {
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/update/{uuid}")
    public ResponseEntity<?> mettreAJourTotalement(@PathVariable String uuid,
                                                   @Valid @RequestBody CategoryDto category){
        if (service.update(uuid, category)) {
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

}
