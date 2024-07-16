package mii.co.id.emsserverside.service;

//<editor-fold defaultstate="collapsed" desc="Import">
import java.time.LocalDateTime;
import java.util.Arrays;
import mii.co.id.emsserverside.model.User;
import mii.co.id.emsserverside.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import java.util.List;
import mii.co.id.emsserverside.config.AppSecurityConfig;
import mii.co.id.emsserverside.model.MasterStatus;
import mii.co.id.emsserverside.model.Role;
import mii.co.id.emsserverside.model.Token;
import mii.co.id.emsserverside.model.data.UserDto;
import mii.co.id.emsserverside.repository.MasterStatusRepository;
import mii.co.id.emsserverside.repository.RoleRepository;
import mii.co.id.emsserverside.repository.TokenRepository;
import org.springframework.transaction.annotation.Transactional;
//</editor-fold>

@Service
public class UserService {

    private UserRepository userRepository;
    private RoleRepository roleRepository;
    private MasterStatusRepository masterStatusRepository;
    private EmailService emailService;
    private TokenRepository tokenRepository;
    private AppSecurityConfig appSecurityConfig;

    @Autowired
    public UserService(UserRepository userRepository, RoleRepository roleRepository, MasterStatusRepository masterStatusRepository, EmailService emailService, TokenRepository tokenRepository, AppSecurityConfig appSecurityConfig) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.masterStatusRepository = masterStatusRepository;
        this.emailService = emailService;
        this.tokenRepository = tokenRepository;
        this.appSecurityConfig = appSecurityConfig;
    }

    public List<Object> data(UserDto userDto) {
        Role role = roleRepository.findById(userDto.getRole()).get();
        MasterStatus statusUser = masterStatusRepository.findById(userDto.getStatusUser()).get();
        return Arrays.asList(role, statusUser);
    }

    public Long countUser() {
        return userRepository.countUser();
    }

    public List<User> getByRole(String role) {
        return userRepository.findByRole_name(role);
    }

    public List<User> getAll() {
        return userRepository.findAll();
    }

    public User getById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));
    }

    public User getProfile(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));
    }

    @Transactional
    public User create(UserDto userDto) {
        List<Object> data = data(userDto);
        String encrypt = appSecurityConfig.passwordEncoder().encode(userDto.getPassword());
        User user = User.builder()
                .name(userDto.getName())
                .address(userDto.getAddress())
                .email(userDto.getEmail())
                .password(encrypt)
                .phoneNumber(userDto.getPhoneNumber())
                .companyUniversity(userDto.getCompanyUniversity())
                .jobTitleYears(userDto.getJobTitleYears())
                .isActive(false)
                .isDeleted(null)
                .role((Role) data.get(0))
                .statusUser((MasterStatus) data.get(1))
                .build();
        userRepository.save(user);
        emailService.sendEmail(userDto.getEmail());
        return user;
    }

    public UserDto update(Long id, UserDto userDto) {
        User data = getById(id);
        String encrypt = appSecurityConfig.passwordEncoder().encode(userDto.getPassword());
        List<Object> dataStatus = data(userDto);
        User user = User.builder()
                .id(id)
                .name(userDto.getName())
                .address(userDto.getAddress())
                .email(userDto.getEmail())
                .password(encrypt)
                .phoneNumber(userDto.getPhoneNumber())
                .companyUniversity(userDto.getCompanyUniversity())
                .jobTitleYears(userDto.getJobTitleYears())
                .isActive(data.getIsActive())
                .isDeleted(data.getIsDeleted())
                .role(data.getRole())
                .statusUser((MasterStatus) dataStatus.get(1))
                .build();
        userRepository.save(user);
        return userDto;
    }

    public String activation(String tokenCode) {
        Token token = tokenRepository.findByTokenCode(tokenCode).get();
        User data = getById(token.getUser().getId());
        LocalDateTime date = LocalDateTime.now();
        if (date.isBefore(token.getExpired())) {
            if (token.getConfirm() == null) {
                token.setConfirm(date);
                data.setIsActive(true);
                userRepository.save(data);
                emailService.sendEmail(data.getEmail());
                return "<html lang=\"en\">\n"
                        + "    <head>\n"
                        + "        <meta charset=\"UTF-8\">\n"
                        + "        <meta content=\"width=device-width, initial-scale=1\" name=\"viewport\">\n"
                        + "        <title></title>\n"
                        + "    </head>\n"
                        + "    <body>\n"
                        + "        <div class=\"b-content\">\n"
                        + "            <h1 style=\"text-align: center;\">Account successfully activated!</h1>\n"
                        + "            <p style=\"text-align: center;\">You can sign in <a href=\"http://localhost:8089/login\">here</></p>\n"
                        + "        </div>\n"
                        + "    </body>\n"
                        + "</html>";
            }
            throw new IllegalStateException("Token Was Used");
        }
        throw new IllegalStateException("Expired");
    }

    public User delete(Long id) {
        User user = getById(id);
        userRepository.deleteById(id);
        return user;
    }
}
