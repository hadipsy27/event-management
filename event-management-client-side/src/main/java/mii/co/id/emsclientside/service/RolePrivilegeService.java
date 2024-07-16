package mii.co.id.emsclientside.service;

import java.util.List;
import mii.co.id.emsclientside.model.RolePrivilege;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class RolePrivilegeService {

    private RestTemplate restTemplate;

    @Value("${api.baseUrl}/event")
    private String url;

    public RolePrivilegeService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public List<RolePrivilege> getAll() {
        return restTemplate
                .exchange(url, HttpMethod.GET, null,
                        new ParameterizedTypeReference<List<RolePrivilege>>() {
                })
                .getBody();
    }

}
