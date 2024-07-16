package mii.co.id.emsserverside.repository;

//<editor-fold defaultstate="collapsed" desc="Import">
import java.util.List;
import mii.co.id.emsserverside.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;
import org.springframework.data.jpa.repository.Query;
//</editor-fold>

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByName(String name);

    Optional<User> findByEmail(String email);

    Optional<User> findByTokens(String token);
    
    List<User> findByRole_name(String role);

    @Query(
            value = "SELECT COUNT(*) FROM user",
            nativeQuery = true
    )
    Long countUser();
}
