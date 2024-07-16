package mii.co.id.emsclientside.service;

//<editor-fold defaultstate="collapsed" desc="Import">
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import mii.co.id.emsclientside.model.data.LoginRequestDto;
import mii.co.id.emsclientside.model.data.LoginResponseDto;
import mii.co.id.emsclientside.utility.BasicHeader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
//</editor-fold>

@Service
public class LoginService {

    private RestTemplate restTemplate;

    @Value("${api.baseUrl}/login")
    private String url;

    @Autowired
    public LoginService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public boolean login(LoginRequestDto request) {
        try {
            ResponseEntity<LoginResponseDto> res = restTemplate
                    .postForEntity(url, request, LoginResponseDto.class);

            if (res.getStatusCode() == HttpStatus.OK) {
                setAuthentication(res.getBody(), request.getPassword());
                return true;
            }
        } catch (RestClientException e) {
        }
        return false;
    }

    private void setAuthentication(LoginResponseDto res, String password) {
        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(res.getUsername(),
                BasicHeader.createBasicToken(res.getUsername(), password),
                getAuthorities(res.getAuthorities()));
        SecurityContextHolder.getContext().setAuthentication(authToken);
    }

    private Collection<GrantedAuthority> getAuthorities(List<String> authorities) {
        return authorities
                .stream()
                .map(auth -> new SimpleGrantedAuthority(auth))
                .collect(Collectors.toList());
    }
}
