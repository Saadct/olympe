package saad.projet.jo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import saad.projet.jo.model.Operation;

import java.util.List;
import java.util.Optional;

@Repository
public interface OperationRepository extends JpaRepository<Operation, String> {
    Optional<Operation> findOneByUuid(String uuid);
    List<Operation> findAll();


}
