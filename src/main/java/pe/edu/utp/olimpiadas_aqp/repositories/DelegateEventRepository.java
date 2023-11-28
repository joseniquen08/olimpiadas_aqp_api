package pe.edu.utp.olimpiadas_aqp.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import pe.edu.utp.olimpiadas_aqp.entities.DelegateEventEntity;
import pe.edu.utp.olimpiadas_aqp.entities.SportEventEntity;

import java.util.List;

@Repository
public interface DelegateEventRepository extends JpaRepository<DelegateEventEntity, Long> {
    @Query(value = "select * from delegate_event where event_id = :event_id", nativeQuery = true)
    List<DelegateEventEntity> findByEventId(@Param("event_id") Long eventId);
}