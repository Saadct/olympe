package saad.projet.jo.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import saad.projet.jo.model.Category;
import org.springframework.data.repository.query.Param;
import saad.projet.jo.model.Event;

import java.util.List;
import java.util.Optional;

@Repository
public interface EventRepository extends JpaRepository <Event, String>{

    Optional<Event> findOneByUuid(String uuid);

    List<Event> findAllByCategory(Category category);

    @Query("SELECT e FROM Event e WHERE e.dateEvent > CURRENT_DATE ORDER BY e.dateEvent ASC")
    Page<Event> findByDateAfter(Pageable pageable);

    @Query("SELECT COUNT(e) FROM Event e WHERE e.dateEvent > CURRENT_DATE")
    long countByDateAfter();

    @Query("SELECT COUNT(e) FROM Event e WHERE e.dateEvent > CURRENT_DATE AND e.category = :category")
    long countByDateAfterByCategory(@Param("category") Category category);

    @Query("SELECT e FROM Event e WHERE e.dateEvent > CURRENT_DATE AND e.category = :category ORDER BY e.dateEvent ASC")
    Page<Event> findByCategory(@Param("category") Category category, Pageable pageable);

    @Query("SELECT e FROM Event e WHERE e.category = :category ORDER BY e.dateEvent ASC")
    long countByCategory(@Param("category") Category category);


}
