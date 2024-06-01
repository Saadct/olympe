package saad.projet.jo.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import saad.projet.jo.model.Ticket;
import saad.projet.jo.model.User;

import java.util.List;
import java.util.Optional;

@Repository
public interface TicketRepository extends JpaRepository<Ticket, String> {

    Optional<Ticket> findOneByUuid(String uuid);

    @Override
    List<Ticket> findAll();


    List<Ticket> findAllByUserId(String id);

}
