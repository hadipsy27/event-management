package mii.co.id.emsclientside.controller;

//<editor-fold defaultstate="collapsed" desc="Import">
import java.util.ArrayList;
import java.util.List;
import mii.co.id.emsclientside.model.data.LoginRequestDto;
import mii.co.id.emsclientside.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
//</editor-fold>

@Controller
public class LoginController {

    private LoginService loginService;

    @Autowired
    public LoginController(LoginService loginService) {
        this.loginService = loginService;
    }

    public String redirect(Authentication auth) {
        List<String> roles = new ArrayList<>();
        if (auth != null) {
            auth.getAuthorities().forEach(data -> {
                roles.add(data.getAuthority());
            });
            for (String role : roles) {
                if (role.equals("ROLE_ADMIN")) {
                    return "redirect:/admin";
                } else if (role.equals("ROLE_EVENT_ORGANIZER")) {
                    return "redirect:/eo";
                } else if (role.equals("ROLE_USER")) {
                    return "redirect:/user";
                }
            }
        }
        return "redirect:/login";
    }

    @GetMapping("/login")
    public String login(LoginRequestDto loginRequestDto) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null || auth instanceof AnonymousAuthenticationToken) {
            return "login";
        }

        return redirect(auth);
    }

    @PostMapping("/login")
    public String login(LoginRequestDto loginRequestDto, BindingResult result) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (result.hasErrors()) {
            return "login";
        }

        if (!loginService.login(loginRequestDto)) {
            return "redirect:/login?error=true";
        }

        return redirect(auth);
    }

    @PostMapping("/logout")
    public String logout() {
        SecurityContextHolder.getContext().setAuthentication(null);

        return "redirect:/login?logout=true";
    }
}
