package saad.projet.jo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import saad.projet.jo.model.Evenement;
import saad.projet.jo.model.Schedule;
import saad.projet.jo.repository.ScheduleRepository;

import java.util.List;

@Service
public class ScheduleService {

    private final ScheduleRepository repository;

    @Autowired
    public ScheduleService(ScheduleRepository repository ){
        this.repository = repository;
    }

    public List<Schedule> findAllSchedules () {
        System.out.println("Toutes les horaires");
        return repository.findAll();
    }

    public Schedule findScheduleById(String uuid) {
        return repository.findOneByUuid(uuid).orElse(null);
    }

    public Schedule createSchedule (Schedule schedule){
        System.out.println("Horaire créer");
        return repository.save(schedule);
    }

    @Transactional
    public Boolean deleteSchedule (String id){
        System.out.println("horaire supprimée");
        Schedule schedule = findScheduleById(id);
        if(schedule != null) {
            repository.deleteById(id);
            return true;
        }
        return false;
    }

    @Transactional
    public boolean updateSchedule (String id, Schedule schedule){
        Schedule scheduleModif = findScheduleById(id);
        if(scheduleModif != null) {
            scheduleModif.setDate(schedule.getDate());
            scheduleModif.setTime(schedule.getTime());
            repository.save(scheduleModif);
            return true;
        }
        return false;
    }


}
