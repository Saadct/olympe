package saad.projet.jo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import saad.projet.jo.model.Evenement;
import saad.projet.jo.model.Spectator;
import saad.projet.jo.repository.SpectatorRepository;

import java.util.List;

@Service
public class SpectatorService {

    private final SpectatorRepository repository;

    @Autowired
    public SpectatorService(SpectatorRepository repository){
        this.repository = repository;
    }

    public List<Spectator> findAllSpectator () {
        System.out.println("Toutes les spectateurs");
        return repository.findAll();
    }
    public Spectator findSpectatorById(String uuid) {
        return repository.findOneByUuid(uuid).orElse(null);
    }

    public Spectator createSpectator (Spectator spectator){
        System.out.println("spectateur créer");
        return repository.save(spectator);
    }

    @Transactional
    public Boolean deleteSpectator (String id){
        System.out.println("spectatuer supprimée");
        Spectator spectator = findSpectatorById(id);
        if(spectator != null) {
            repository.deleteById(id);
            return true;
        }
        return false;
    }

    @Transactional
    public boolean updateSpectator (String id, Spectator sp){
        Spectator spectator = findSpectatorById(id);
        if(spectator != null) {
            spectator.setName(sp.getName());
            spectator.setLastName(sp.getLastName());
            spectator.setTel(sp.getTel());
            spectator.setEmail(sp.getEmail());
            spectator.setPassword(sp.getPassword());
            repository.save(spectator);
            return true;
        }
        return false;
    }

}
