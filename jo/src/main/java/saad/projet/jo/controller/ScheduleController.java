package saad.projet.jo.controller;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import saad.projet.jo.model.Category;
import saad.projet.jo.model.Schedule;
import saad.projet.jo.service.CategoryService;
import saad.projet.jo.service.ScheduleService;

import java.util.List;

@RestController
@RequestMapping("/schedules")
public class ScheduleController {

    private final ScheduleService service;

    public ScheduleController(ScheduleService service){
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<Schedule>> findAll(){
        return new ResponseEntity<>(service.findAllSchedules(), HttpStatus.NOT_FOUND);
    }

    @PostMapping
    public ResponseEntity<Schedule> create(@Valid @RequestBody Schedule schedule) {
        return new ResponseEntity<>(service.createSchedule(schedule), HttpStatus.CREATED);
    }

    @DeleteMapping("/{uuid}")
    public ResponseEntity<?> delete(@PathVariable String uuid) {
        if (service.deleteSchedule(uuid)) {
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/{uuid}")
    public ResponseEntity<?> mettreAJourTotalement(@PathVariable String uuid,
                                                   @Valid @RequestBody Schedule schedule){
        if (service.updateSchedule(uuid, schedule)) {
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
