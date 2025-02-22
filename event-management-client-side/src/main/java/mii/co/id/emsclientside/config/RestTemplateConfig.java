package mii.co.id.emsclientside.config;

//<editor-fold defaultstate="collapsed" desc="Import">
import java.util.ArrayList;
import java.util.List;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.util.CollectionUtils;
import org.springframework.web.client.RestTemplate;
//</editor-fold>

@Configuration
public class RestTemplateConfig {

    @Bean
    public RestTemplate restTemplate() {
        RestTemplate restTemplate = new RestTemplate();

        List<ClientHttpRequestInterceptor> interceptors
                = restTemplate.getInterceptors();

        if (CollectionUtils.isEmpty(interceptors)) {
            interceptors = new ArrayList<>();
        }

        interceptors.add(new RequestInterceptor());
        restTemplate.setInterceptors(interceptors);
        return restTemplate;
    }
}
