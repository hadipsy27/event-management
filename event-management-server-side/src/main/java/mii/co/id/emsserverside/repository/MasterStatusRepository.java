package mii.co.id.emsserverside.repository;

//<editor-fold defaultstate="collapsed" desc="Import">
import java.util.List;
import java.util.Optional;
import mii.co.id.emsserverside.model.MasterStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
//</editor-fold>

@Repository
public interface MasterStatusRepository extends JpaRepository<MasterStatus, Long>{
    
    Optional<MasterStatus> findByName(String name);
    
    List<MasterStatus> findByStatusType_id(Long id);
}
