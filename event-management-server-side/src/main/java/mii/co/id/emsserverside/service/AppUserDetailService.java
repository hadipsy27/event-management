package mii.co.id.emsserverside.service;

//<editor-fold defaultstate="collapsed" desc="Import">
import mii.co.id.emsserverside.model.AppUserDetail;
import mii.co.id.emsserverside.model.User;
import mii.co.id.emsserverside.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
//</editor-fold>

@Service
public class AppUserDetailService implements UserDetailsService {

    private UserRepository userRepository;

    @Autowired
    public AppUserDetailService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Username or password incorrect"));

        return new AppUserDetail(user);
    }
}
