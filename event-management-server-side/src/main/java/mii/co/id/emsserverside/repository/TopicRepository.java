package mii.co.id.emsserverside.repository;

//<editor-fold defaultstate="collapsed" desc="Import">
import mii.co.id.emsserverside.model.Topic;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
//</editor-fold>

@Repository
public interface TopicRepository extends JpaRepository<Topic, Long>{
    
}
