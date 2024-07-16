package mii.co.id.emsserverside.repository;

//<editor-fold defaultstate="collapsed" desc="Import">
import mii.co.id.emsserverside.model.Token;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;
//</editor-fold>

@Repository
public interface TokenRepository extends JpaRepository<Token, Long> {

    Optional<Token> findByUser_Id(Long id);

    Optional<Token> findByTokenCode(String tokenCode);
}
