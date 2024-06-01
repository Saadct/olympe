package saad.projet.jo.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import saad.projet.jo.model.User;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends CrudRepository<User,Integer> {

    Optional<User> findByEmail(String email);
    Optional<User> findById(String id);
}
