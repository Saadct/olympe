package saad.projet.jo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import saad.projet.jo.model.Evenement;

import java.util.List;
import java.util.Optional;

@Repository
public interface EvenementRepository extends JpaRepository <Evenement, String>{
    List<Evenement> findAll();

    List<Evenement> findByState(String state);

    Optional<Evenement> findOneByUuid(String uuid);


}
