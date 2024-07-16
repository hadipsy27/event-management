package mii.co.id.emsserverside.repository;

//<editor-fold defaultstate="collapsed" desc="Import">
import java.util.List;
import java.util.Optional;
import mii.co.id.emsserverside.model.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
//</editor-fold>

@Repository
public interface EventRepository extends JpaRepository<Event, Long> {

    Optional<Event> findByName(String name);

    List<Event> findByUser_id(Long id);

    List<Event> findByTopic_id(Long id);

    @Query(
            value = "SELECT COUNT(*) FROM event",
            nativeQuery = true
    )
    Long countEvent();

    @Query(
            value = "SELECT COUNT(*) FROM event "
            + "JOIN master_status "
            + "ON event.event_status_master_status_id = master_status.master_status_id "
            + "WHERE master_status.name = 'Canceled'",
            nativeQuery = true
    )
    Long countCanceledEvent();

    @Query(
            value = "SELECT event_id, event_name, event_image, description, registration_start, registration_end, event_start,"
            + " event_end, location, capacity, e.total, t.topic_name, m.name  FROM event JOIN "
            + "(SELECT event_id AS e_id, COUNT(*) AS total FROM user_event GROUP BY event_id) AS e "
            + "ON event.event_id = e.e_id JOIN (SELECT topic_id AS t_id, topic_name FROM topic) AS t "
            + "ON event.topic_topic_id = t.t_id JOIN (SELECT master_status_id AS m_id, name FROM master_status) AS m "
            + "ON event.event_status_master_status_id = m.m_id \n"
            + "ORDER BY `e`.`total`  DESC LIMIT 10",
            nativeQuery = true
    )
    List<Object> topTen();

    @Query(
            value = "SELECT COUNT(*) AS TotalPerMonth, MONTHNAME(event_start) AS Month FROM event WHERE event_start > now() - INTERVAL 7 MONTH GROUP BY MONTH(event_start)",
            nativeQuery = true
    )
    List<String> lastSeven();

    @Query(
            value = "SELECT event.event_id, event_name, location, master_status.name, topic.topic_name FROM event \n" +
                    "JOIN user_event ON event.event_id = user_event.event_id \n" +
                    "JOIN master_status ON user_event.registration_status_master_status_id = master_status.master_status_id \n" +
                    "JOIN topic ON event.topic_topic_id = topic.topic_id\n" +
                    "WHERE user_event.user_id = ?1",
            nativeQuery = true
    )
    List<Object> myEvent(Long id);
}
