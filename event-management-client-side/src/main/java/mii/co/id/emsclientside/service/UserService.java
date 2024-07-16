package mii.co.id.emsclientside.service;

//<editor-fold defaultstate="collapsed" desc="Import">
import java.util.List;
import mii.co.id.emsclientside.model.User;
import mii.co.id.emsclientside.model.data.UserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
//</editor-fold>

@Service
public class UserService {

    private RestTemplate restTemplate;

    @Value("${api.baseUrl}")
    private String url;

    @Autowired
    public UserService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public Long countUser() {
        return restTemplate
                .exchange(url + "/user/count", HttpMethod.GET, null,
                        new ParameterizedTypeReference<Long>() {
                })
                .getBody();
    }

    public List<User> getByRole(String role) {
        return restTemplate
                .exchange(url + "/user/get-byrole/" + role, HttpMethod.GET, null,
                        new ParameterizedTypeReference<List<User>>() {
                }).getBody();
    }

    public User getById(Long id) {
        return restTemplate
                .getForObject(url + "/user/" + id, User.class);
    }

    public List<User> getAll() {
        return restTemplate
                .exchange(url + "/user", HttpMethod.GET, null,
                        new ParameterizedTypeReference<List<User>>() {
                })
                .getBody();
    }

    public User getByProfile(String email) {
        return restTemplate
                .exchange(url + "/user/getby-email/" + email, HttpMethod.GET, null,
                        new ParameterizedTypeReference<User>() {
                }).getBody();
    }

    public User createEo(UserDto userDto) {
        UserDto userBuild = UserDto.builder()
                .name(userDto.getName())
                .address(userDto.getAddress())
                .email(userDto.getEmail())
                .password(userDto.getPassword())
                .phoneNumber(userDto.getPhoneNumber())
                .companyUniversity(userDto.getCompanyUniversity())
                .jobTitleYears(userDto.getJobTitleYears())
                .role(2L)
                .statusUser(userDto.getStatusUser())
                .build();
        return restTemplate
                .postForEntity(url + "/user", userBuild, User.class).getBody();
    }

    public User updateEo(Long id, UserDto userDto) {
        User data = getById(id);
        if (userDto.getPassword().isEmpty()) {
            userDto.setPassword(data.getPassword());
        }
        UserDto userBuild = UserDto.builder()
                .id(id)
                .name(userDto.getName())
                .address(userDto.getAddress())
                .email(userDto.getEmail())
                .password(userDto.getPassword())
                .phoneNumber(userDto.getPhoneNumber())
                .companyUniversity(userDto.getCompanyUniversity())
                .jobTitleYears(userDto.getJobTitleYears())
                .role(2L)
                .statusUser(userDto.getStatusUser())
                .build();
        return restTemplate
                .exchange(url + "/user/" + id, HttpMethod.PUT, new HttpEntity(userBuild),
                        new ParameterizedTypeReference<User>() {
                }).getBody();
    }

    public User createUser(UserDto userDto) {
        UserDto userBuild = UserDto.builder()
                .name(userDto.getName())
                .address(userDto.getAddress())
                .email(userDto.getEmail())
                .password(userDto.getPassword())
                .phoneNumber(userDto.getPhoneNumber())
                .companyUniversity(userDto.getCompanyUniversity())
                .jobTitleYears(userDto.getJobTitleYears())
                .role(3L)
                .statusUser(userDto.getStatusUser())
                .build();
        return restTemplate
                .postForEntity(url + "/user", userBuild, User.class).getBody();
    }

    public User updateUser(Long id, UserDto userDto) {
        User data = getById(id);
        if (userDto.getPassword().isEmpty()) {
            userDto.setPassword(data.getPassword());
        }
        UserDto userBuild = UserDto.builder()
                .id(id)
                .name(userDto.getName())
                .address(userDto.getAddress())
                .email(userDto.getEmail())
                .password(userDto.getPassword())
                .phoneNumber(userDto.getPhoneNumber())
                .companyUniversity(userDto.getCompanyUniversity())
                .jobTitleYears(userDto.getJobTitleYears())
                .role(3L)
                .statusUser(userDto.getStatusUser())
                .build();
        return restTemplate
                .exchange(url + "/user/" + id, HttpMethod.PUT, new HttpEntity(userBuild),
                        new ParameterizedTypeReference<User>() {
                }).getBody();
    }

    public User delete(Long id) {
        return restTemplate
                .exchange(url + "/user/" + id, HttpMethod.DELETE, null,
                        new ParameterizedTypeReference<User>() {
                })
                .getBody();
    }
}
