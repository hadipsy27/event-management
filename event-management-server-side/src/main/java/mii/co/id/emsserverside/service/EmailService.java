package mii.co.id.emsserverside.service;

//<editor-fold defaultstate="collapsed" desc="Import">
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import mii.co.id.emsserverside.model.Token;
import mii.co.id.emsserverside.model.User;
import mii.co.id.emsserverside.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;
//</editor-fold>

@Service
public class EmailService {

    private JavaMailSender javaMailSender;
    private SpringTemplateEngine templateEngine;
    private UserRepository userRepository;
    private TokenService tokenService;

    @Autowired
    public EmailService(JavaMailSender javaMailSender, SpringTemplateEngine templateEngine, UserRepository userRepository, TokenService tokenService) {
        this.javaMailSender = javaMailSender;
        this.templateEngine = templateEngine;
        this.userRepository = userRepository;
        this.tokenService = tokenService;
    }

    public User sendEmail(String email) {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage, "utf-8");
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.FORBIDDEN, "Forbidden access"));
        try {
            if (user.getIsActive() == false) {
                Token token = tokenService.create(user);
                String message = templateEngine.process("emailActivation", buildContextActivation(user.getName(), token.getTokenCode()));
                messageHelper.setSubject("Activation New Account User");
                messageHelper.setText(message, true);
                messageHelper.setTo(email);
                javaMailSender.send(mimeMessage);
            } else {
                String message = templateEngine.process("emailActivationDone", buildContextActivationDone(user.getName()));
                messageHelper.setSubject("Activation Success");
                messageHelper.setText(message, true);
                messageHelper.setTo(email);
                javaMailSender.send(mimeMessage);
            }
        } catch (MessagingException | MailException ex) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ex.getMessage());
        }
        return user;
    }

    private Context buildContextActivation(String username, String token) {
        Context context = new Context();
        context.setVariable("hello", "Hi There, " + username);
        context.setVariable("token", "http://localhost:8088/api/user/activation/" + token);
        return context;
    }

    private Context buildContextActivationDone(String username) {
        Context context = new Context();
        context.setVariable("hello", "Hi There, " + username);
        return context;
    }
}
