package saad.projet.jo.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import saad.projet.jo.model.User;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends CrudRepository<User,Integer> {

    List<User> findAll();

    Page<User> findAll(Pageable pageable);

  //  @Query("SELECT COUNT(t) FROM Ticket t WHERE t.user = :user")
   // long countUsers();

    Optional<User> findByEmail(String email);
    Optional<User> findById(String id);
}
