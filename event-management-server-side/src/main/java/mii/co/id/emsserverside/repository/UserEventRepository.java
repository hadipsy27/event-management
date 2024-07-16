package mii.co.id.emsserverside.repository;

//<editor-fold defaultstate="collapsed" desc="Import">
import java.util.List;
import java.util.Optional;
import mii.co.id.emsserverside.model.UserEvent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
//</editor-fold>

@Repository
public interface UserEventRepository extends JpaRepository<UserEvent, Long> {

    Optional<UserEvent> findByUser_id(Long userId);

    List<UserEvent> findByEvent_id(Long eventId);

    @Query(
            value = "SELECT COUNT(*) FROM user_event",
            nativeQuery = true
    )
    Long countParticipant();
    
        @Query(
            value = "SELECT COUNT(*) AS total FROM user_event JOIN event ON user_event.event_id = event.event_id WHERE event_start > now() - INTERVAL 7 MONTH GROUP BY event.event_start",
            nativeQuery = true
    )
    List<String> countParticipantMonth();
}
