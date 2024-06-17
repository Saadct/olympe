package saad.projet.jo.repository;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import saad.projet.jo.model.Event;
import saad.projet.jo.model.Ticket;
import saad.projet.jo.model.User;

import java.util.List;
import java.util.Optional;

@Repository
public interface TicketRepository extends JpaRepository<Ticket, String> {

    Optional<Ticket> findOneByUuid(String uuid);

    @Override
    List<Ticket> findAll();

    List<Ticket> findAllByEvenement(Event evenement);


    List<Ticket> findAllByUserId(String id);

    @Query("SELECT COUNT(t) FROM Ticket t WHERE t.user = :user")
    long countByDateAfter(@Param("user") User user);

    @Query("SELECT COUNT(t) FROM Ticket t WHERE t.evenement = :evenement")
    long countByEvenement(@Param("evenement") Event evenement);

    @Query("SELECT COUNT(t) FROM Ticket t WHERE t.dateEvent > CURRENT_DATE and t.user = :user")
    long countByDateAfterAvailable(@Param("user") User user);

    @Query("SELECT COUNT(t) FROM Ticket t WHERE t.dateEvent < CURRENT_DATE and t.user = :user")
    long countByDateAfterNotAvailable(@Param("user") User user);

    @Query("SELECT t FROM Ticket t WHERE t.dateEvent > CURRENT_DATE AND t.user = :user ORDER BY t.dateEvent ASC")
    Page<Ticket> findAllByUserPaginatedAvailable(@Param("user") User user, Pageable pageable);

    @Query("SELECT t FROM Ticket t WHERE t.user = :user ORDER BY t.dateEvent ASC")
    Page<Ticket> findAllByUserPaginated(@Param("user") User user, Pageable pageable);

    @Query("SELECT t FROM Ticket t WHERE t.dateEvent < CURRENT_DATE AND t.user = :user ORDER BY t.dateEvent ASC")
    Page<Ticket> findAllByUserPaginatedNotAvailable(@Param("user") User user, Pageable pageable);

    @Query("SELECT t FROM Ticket t WHERE t.user = :user and t.evenement = :evenement ORDER BY t.dateEvent ASC")
    List<Ticket> findAllByUserAndEvenement(@Param("user") User user, @Param("evenement") Event evenement );

    @Query("SELECT t FROM Ticket t WHERE t.evenement = :evenement ORDER BY t.dateEvent ASC")
    Page<Ticket> findAllByEvenementPaginated(@Param("evenement") Event evenement, Pageable pageable);



}
