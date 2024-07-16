package mii.co.id.emsserverside.service;

//<editor-fold defaultstate="collapsed" desc="Import">
import mii.co.id.emsserverside.model.Token;
import mii.co.id.emsserverside.model.User;
import mii.co.id.emsserverside.repository.TokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import java.time.LocalDateTime;
import java.util.UUID;
//</editor-fold>

@Service
public class TokenService {

    private TokenRepository tokenRepository;

    @Autowired
    public TokenService(TokenRepository tokenRepository) {
        this.tokenRepository = tokenRepository;
    }

    public Token getByTokenCode(String tokenCode) {
        return tokenRepository.findByTokenCode(tokenCode).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Token not found"));
    }

    public Token getByUserId(Long id) {
        return tokenRepository.findByUser_Id(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Token not found"));
    }

    public Token create(User user) {
        UUID uuid = UUID.randomUUID();
        LocalDateTime created = LocalDateTime.now();
        LocalDateTime expired = LocalDateTime.now().plusDays(1);
        Token token = new Token();
        token.setTokenCode(uuid.toString());
        token.setUser(user);
        token.setCreated(created);
        token.setExpired(expired);
        return tokenRepository.save(token);
    }
}
