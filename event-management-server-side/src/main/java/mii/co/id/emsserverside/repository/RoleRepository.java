package mii.co.id.emsserverside.repository;

//<editor-fold defaultstate="collapsed" desc="Import">
import mii.co.id.emsserverside.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;
import org.springframework.stereotype.Repository;
//</editor-fold>

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    
}
