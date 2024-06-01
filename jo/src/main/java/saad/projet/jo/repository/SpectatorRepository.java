package saad.projet.jo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import saad.projet.jo.model.Category;
import saad.projet.jo.model.Operation;
import saad.projet.jo.model.Spectator;

import java.util.List;
import java.util.Optional;

@Repository
public interface SpectatorRepository extends JpaRepository<Spectator, String> {
    List<Spectator> findAll();
    Optional<Spectator> findOneByUuid(String uuid);

}
