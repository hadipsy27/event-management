package mii.co.id.emsclientside.service;

//<editor-fold defaultstate="collapsed" desc="Import">
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import mii.co.id.emsclientside.model.Topic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
//</editor-fold>

@Service
@Slf4j
public class TopicService {

    private RestTemplate restTemplate;

    @Value("${api.baseUrl}")
    private String url;

    @Autowired
    public TopicService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }
    
    public List<Topic> getAllTopic() {
        return restTemplate
                .exchange(url + "/topic", HttpMethod.GET, null,
                        new ParameterizedTypeReference<List<Topic>>() {
                })
                .getBody();
    }
}
