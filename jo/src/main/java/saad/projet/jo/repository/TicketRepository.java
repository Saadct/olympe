package saad.projet.jo.repository;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import saad.projet.jo.model.Category;
import saad.projet.jo.model.Evenement;
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

  //  List<Ticket> findAllByUserIdPaginated(String id);

  //  @Query("SELECT e FROM Ticket t WHERE t.dateEvent > CURRENT_DATE AND e.category = :category ORDER BY e.dateEvent ASC")
  //  Page<Ticket> findAllByUserIdPaginatedAvailable(@Param("user") User user, Pageable pageable);

    @Query("SELECT t FROM Ticket t WHERE t.user = :user and t.evenement = :evenement ORDER BY t.date ASC")
    List<Ticket> findAllByUserAndEvenement(@Param("user") User user, @Param("evenement") Evenement evenement );


    @Query("SELECT t FROM Ticket t WHERE t.user = :user ORDER BY t.date ASC")
    Page<Ticket> findAllByUserPaginated(@Param("user") User user, Pageable pageable);

    @Query("SELECT COUNT(t) FROM Ticket t WHERE t.user = :user")
    long countByDateAfter(@Param("user") User user);

    @Query("SELECT COUNT(t) FROM Ticket t WHERE t.date > CURRENT_DATE and t.user = :user")
    long countByDateAfterAvailable(@Param("user") User user);

    @Query("SELECT COUNT(t) FROM Ticket t WHERE t.date < CURRENT_DATE and t.user = :user")
    long countByDateAfterNotAvailable(@Param("user") User user);

    @Query("SELECT t FROM Ticket t WHERE t.date > CURRENT_DATE AND t.user = :user ORDER BY t.date ASC")
    Page<Ticket> findAllByUserPaginatedAvailable(@Param("user") User user, Pageable pageable);

    @Query("SELECT t FROM Ticket t WHERE t.date < CURRENT_DATE AND t.user = :user ORDER BY t.date ASC")
    Page<Ticket> findAllByUserPaginatedNotAvailable(@Param("user") User user, Pageable pageable);


}
