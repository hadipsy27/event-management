/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mii.co.id.emsserverside.service;

import java.util.List;
import java.util.stream.Collectors;
import mii.co.id.emsserverside.model.AppUserDetail;
import mii.co.id.emsserverside.model.User;
import mii.co.id.emsserverside.model.data.LoginRequestDto;
import mii.co.id.emsserverside.model.data.LoginResponseDto;
import mii.co.id.emsserverside.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

/*
 *
 * @author Richo Dea
 */
@Service
public class CredentialService {

    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;

    @Autowired
    public CredentialService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public LoginResponseDto login(LoginRequestDto login) {
        User user = userRepository.findByEmail(login.getUsername())
                .orElseThrow(() -> new ResponseStatusException(
                HttpStatus.UNAUTHORIZED,
                "Username or password incorrect"));
        if (!passwordEncoder.matches(login.getPassword(), user.getPassword())) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Username or Password incorrect");
        }

        return new LoginResponseDto(authorities(new AppUserDetail(user)), user.getEmail());
    }

    private List<String> authorities(AppUserDetail appUserDetail) {
        return appUserDetail.getAuthorities()
                .stream()
                .map(auth -> auth.getAuthority())
                .collect(Collectors.toList());
    }
}
