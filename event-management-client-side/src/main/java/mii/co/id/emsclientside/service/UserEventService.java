package mii.co.id.emsclientside.service;

//<editor-fold defaultstate="collapsed" desc="Import">
import groovy.util.logging.Slf4j;
import java.util.List;
import java.util.Optional;
import mii.co.id.emsclientside.model.User;
import mii.co.id.emsclientside.model.UserEvent;
import mii.co.id.emsclientside.model.data.UserEventDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
//</editor-fold>

@Service
@Slf4j
public class UserEventService {

    private RestTemplate restTemplate;

    @Value("${api.baseUrl}")
    private String url;

    @Autowired
    public UserEventService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public List<UserEvent> getByEvent(Long id) {
        return restTemplate
                .exchange(url + "/user-event/event/" + id, HttpMethod.GET, null,
                        new ParameterizedTypeReference<List<UserEvent>>() {
                }).getBody();
    }

    public Long countParticipant() {
        return restTemplate
                .exchange(url + "/user-event/count", HttpMethod.GET, null,
                        new ParameterizedTypeReference<Long>() {
                })
                .getBody();
    }

    public User getByEmail(String email) {
        return restTemplate
                .exchange(url + "/user/getby-email/" + email, HttpMethod.GET, null,
                        new ParameterizedTypeReference<User>() {
                }).getBody();
    }

    public UserEvent buttonAccept(Long eventId, Long userId) {
        return restTemplate
                .exchange(url + "/user-event/accept/" + eventId + "/" + userId, HttpMethod.GET, null,
                        new ParameterizedTypeReference<UserEvent>() {
                }).getBody();
    }

    public UserEvent buttonReject(Long eventId, Long userId) {
        return restTemplate
                .exchange(url + "/user-event/reject/" + eventId + "/" + userId, HttpMethod.GET, null,
                        new ParameterizedTypeReference<UserEvent>() {
                }).getBody();
    }

    public UserEvent registerEvent(UserEventDto userEventDto, Long id) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User data = getByEmail(auth.getPrincipal().toString());
        UserEventDto eventBuild = UserEventDto.builder()
                .userId(data.getId())
                .eventId(id)
                .registrationStatusId(null)
                .build();
        return restTemplate
                .exchange(url + "/user-event", HttpMethod.POST, new HttpEntity(eventBuild),
                        new ParameterizedTypeReference<UserEvent>() {
                }).getBody();
    }

    public List<String> countParticipantMonth() {
        return restTemplate
                .exchange(url + "/user-event/count-month", HttpMethod.GET, null,
                        new ParameterizedTypeReference<List<String>>() {
                })
                .getBody();
    }
}
