package mii.co.id.emsclientside.service;

//<editor-fold defaultstate="collapsed" desc="Import">
import java.io.IOException;
import java.util.List;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import mii.co.id.emsclientside.model.Event;
import mii.co.id.emsclientside.model.User;
import mii.co.id.emsclientside.model.data.EventDto;
import mii.co.id.emsclientside.utility.FileUploadUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;
//</editor-fold>

@Service
@Slf4j
public class EventService {

    private RestTemplate restTemplate;

    @Value("${api.baseUrl}")
    private String url;

    @Autowired
    public EventService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public Long countEvent() {
        return restTemplate
                .exchange(url + "/event/count", HttpMethod.GET, null,
                        new ParameterizedTypeReference<Long>() {
                })
                .getBody();
    }

    public Long countCanceledEvent() {
        return restTemplate
                .exchange(url + "/event/count-canceled", HttpMethod.GET, null,
                        new ParameterizedTypeReference<Long>() {
                })
                .getBody();
    }

    public List<Object> topTen() {
        return restTemplate
                .exchange(url + "/event/top-ten", HttpMethod.GET, null,
                        new ParameterizedTypeReference<List<Object>>() {
                })
                .getBody();
    }

    public Map<String, List<String>> lastSeven() {
        return restTemplate
                .exchange(url + "/event/last-seven", HttpMethod.GET, null,
                        new ParameterizedTypeReference<Map<String, List<String>>>() {
                })
                .getBody();
    }

    public Event buttonCancelEvent(Long id) {
        return restTemplate
                .getForObject(url + "/event/cancel-event/" + id, Event.class);
    }

    public List<Event> getAll() {
        return restTemplate
                .exchange(url + "/event", HttpMethod.GET, null,
                        new ParameterizedTypeReference<List<Event>>() {
                })
                .getBody();
    }

    public List<Event> getByTopic(Long id) {
        return restTemplate
                .exchange(url + "/event/topic/" + id, HttpMethod.GET, null,
                        new ParameterizedTypeReference<List<Event>>() {
                })
                .getBody();
    }

    public List<Object> getMyEvent(Long id) {
        return restTemplate
                .exchange(url + "/event/myevent/" + id, HttpMethod.GET, null,
                        new ParameterizedTypeReference<List<Object>>() {
                })
                .getBody();
    }

    public Event getById(Long id) {
        return restTemplate
                .getForObject(url + "/event/" + id, Event.class);
    }

    public List<Event> getByUser(Long id) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User data = getByEmail(auth.getPrincipal().toString());
        id = data.getId();
        return restTemplate
                .exchange(url + "/event/user/" + id, HttpMethod.GET, null,
                        new ParameterizedTypeReference<List<Event>>() {
                })
                .getBody();
    }

    public User getByEmail(String email) {
        return restTemplate
                .exchange(url + "/user/getby-email/" + email, HttpMethod.GET, null,
                        new ParameterizedTypeReference<User>() {
                }).getBody();
    }

    public Event createEvent(EventDto eventDto, MultipartFile image) throws IOException {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User data = getByEmail(auth.getPrincipal().toString());
        String file = StringUtils.cleanPath(image.getOriginalFilename());
        EventDto eventBuild = EventDto.builder()
                .name(eventDto.getName())
                .eventImage(file)
                .registrationStart(eventDto.getRegistrationStart())
                .registrationEnd(eventDto.getRegistrationEnd())
                .eventStart(eventDto.getEventStart())
                .eventEnd(eventDto.getEventEnd())
                .location(eventDto.getLocation())
                .capacity(eventDto.getCapacity())
                .description(eventDto.getDescription())
                .user(data.getId())
                .topic(eventDto.getTopic())
                .eventStatus(eventDto.getEventStatus())
                .build();
        String uploadDir = "./src/main/resources/static/asset/";
        FileUploadUtil.saveFile(uploadDir, file, image);
        return restTemplate
                .exchange(url + "/event", HttpMethod.POST, new HttpEntity(eventBuild),
                        new ParameterizedTypeReference<Event>() {
                }).getBody();
    }

    public Event update(Long id, EventDto eventDto, MultipartFile image) throws IOException {
        String file = StringUtils.cleanPath(image.getOriginalFilename());
        Event getById = getById(id);
        if (file.isEmpty()) {
            file = getById.getEventImage();
        }

        EventDto eventBuild = EventDto.builder()
                .id(id)
                .name(eventDto.getName())
                .eventImage(file)
                .registrationStart(eventDto.getRegistrationStart())
                .registrationEnd(eventDto.getRegistrationEnd())
                .eventStart(eventDto.getEventStart())
                .eventEnd(eventDto.getEventEnd())
                .location(eventDto.getLocation())
                .capacity(eventDto.getCapacity())
                .description(eventDto.getDescription())
                .user(getById.getUser().getId())
                .topic(eventDto.getTopic())
                .eventStatus(eventDto.getEventStatus())
                .build();
        String uploadDir = "./src/main/resources/static/asset/"; // + getById.getUser().getId()
        FileUploadUtil.saveFile(uploadDir, file, image);
        return restTemplate
                .exchange(url + "/event/" + id, HttpMethod.PUT, new HttpEntity(eventBuild),
                        new ParameterizedTypeReference<Event>() {
                }).getBody();
    }
}
