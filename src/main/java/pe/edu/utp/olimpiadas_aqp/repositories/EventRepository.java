package pe.edu.utp.olimpiadas_aqp.repositories;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import pe.edu.utp.olimpiadas_aqp.entities.EventEntity;

import java.sql.Date;
import java.util.List;

@Repository
public interface EventRepository extends JpaRepository<EventEntity, Long> {
    @Transactional
    @Modifying
    @Query("update event e set e.name = :name, e.startDate = :start_date, e.status = :status, e.client.clientId = :client_id where e.eventId = :event_id")
    int editById(
            @Param("event_id") Long eventId,
            @Param("name") String name,
            @Param("start_date") Date startDate,
            @Param("status") boolean status,
            @Param("client_id") Long clientId);

    @Transactional
    @Modifying
    @Query("update event e set e.status = :status where e.eventId = :event_id")
    int changeStatusById(
            @Param("event_id") Long eventId,
            @Param("status") boolean status);

    @Query(value = "select * from event where client_id = :client_id", nativeQuery = true)
    List<EventEntity> findByClientId(@Param("client_id") Long clientId, Pageable pageable);

    @Query(value = "select e.* from event as e join sport_event as se on se.event_id = e.event_id where se.delegate_id = :delegate_id", nativeQuery = true)
    List<EventEntity> findByDelegateId(@Param("delegate_id") Long delegateId, Pageable pageable);
}
