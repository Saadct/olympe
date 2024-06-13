package saad.projet.jo.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import saad.projet.jo.model.Category;
import saad.projet.jo.model.Evenement;
import org.springframework.data.repository.query.Param;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface EvenementRepository extends JpaRepository <Evenement, String>{
  //  List<Evenement> findAll(Pageable page);

    List<Evenement> findByState(String state);

    Optional<Evenement> findOneByUuid(String uuid);

    @Query("SELECT e FROM Evenement e WHERE e.dateEvent > CURRENT_DATE ORDER BY e.dateEvent ASC")
    Page<Evenement> findByDateAfter(Pageable pageable);

    @Query("SELECT COUNT(e) FROM Evenement e WHERE e.dateEvent > CURRENT_DATE")
    long countByDateAfter();

    @Query("SELECT COUNT(e) FROM Evenement e WHERE e.dateEvent > CURRENT_DATE AND e.category = :category")
    long countByDateAfterByCategory(@Param("category") Category category);

    @Query("SELECT e FROM Evenement e WHERE e.dateEvent > CURRENT_DATE AND e.category = :category ORDER BY e.dateEvent ASC")
    Page<Evenement> findByCategory(@Param("category") Category category, Pageable pageable);



}
