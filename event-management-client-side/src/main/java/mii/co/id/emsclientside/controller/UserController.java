package mii.co.id.emsclientside.controller;

//<editor-fold defaultstate="collapsed" desc="Import">
import java.util.List;
import javax.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import mii.co.id.emsclientside.model.Event;
import mii.co.id.emsclientside.model.User;
import mii.co.id.emsclientside.model.data.UserDto;
import mii.co.id.emsclientside.model.data.UserEventDto;
import mii.co.id.emsclientside.service.EventService;
import mii.co.id.emsclientside.service.MasterStatusService;
import mii.co.id.emsclientside.service.UserEventService;
import mii.co.id.emsclientside.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
//</editor-fold>

@Controller
@RequestMapping("/user")
@Slf4j
public class UserController {

    private UserService userService;
    private EventService eventService;
    private UserEventService userEventService;
    private MasterStatusService masterStatusService;

    @Autowired
    public UserController(UserService userService, UserEventService userEventService, EventService eventService, MasterStatusService masterStatusService) {
        this.userService = userService;
        this.eventService = eventService;
        this.userEventService = userEventService;
        this.masterStatusService = masterStatusService;
    }

    @GetMapping
    public String index(Model model, Authentication auth) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || authentication instanceof AnonymousAuthenticationToken) {
            model.addAttribute("countEvent", eventService.countEvent());
            model.addAttribute("countUser", userService.countUser());
            model.addAttribute("countParticipant", userEventService.countParticipant());
            model.addAttribute("topTen", eventService.topTen());
            model.addAttribute("events", eventService.getAll());
            return "user/index-anonymous";
        }
        model.addAttribute("countEvent", eventService.countEvent());
        model.addAttribute("countUser", userService.countUser());
        model.addAttribute("countParticipant", userEventService.countParticipant());
        model.addAttribute("topTen", eventService.topTen());
        model.addAttribute("events", eventService.getAll());
        model.addAttribute("user", userService.getByProfile(auth.getName()));
        model.addAttribute("auth", auth.getName());
        return "user/index";
    }

    @GetMapping("/{id}")
    @ResponseBody
    public User getById(@PathVariable("id") Long id) {
        return userService.getById(id);
    }

    @GetMapping("/get-all")
    @ResponseBody
    public List<User> getAll() {
        return userService.getAll();
    }

    @GetMapping("/top-ten")
    @ResponseBody
    public List<Object> topTen() {
        return eventService.topTen();
    }

    @GetMapping("/event/get-all")
    @ResponseBody
    public List<Event> allEvent() {
        return eventService.getAll();
    }

    @GetMapping("/topic/{id}")
    @ResponseBody
    public List<Event> getByTopic(@PathVariable("id") Long id) {
        return eventService.getByTopic(id);
    }

    @DeleteMapping("/{id}")
    @ResponseBody
    public User delete(@PathVariable("id") Long id) {
        return userService.delete(id);
    }

    @GetMapping("/detail-event")
    public String detail() {
        return "user/detail-event";
    }

    @GetMapping("/detail-event/{id}")
    public String detailEvent(@PathVariable("id") Long id, Model model, Authentication auth) {
        model.addAttribute("event", eventService.getById(id));
        model.addAttribute("user", userService.getByProfile(auth.getName()));
        model.addAttribute("auth", auth.getName());
        return "user/detail-event";
    }

    @GetMapping("/register/{id}")
    public String registerEvent(@PathVariable("id") Long id, UserEventDto eventDto, Model model, Authentication auth) {
        model.addAttribute("user", userEventService.registerEvent(eventDto, id));
        model.addAttribute("auth", auth.getName());
        return "redirect:/user";
    }

    @GetMapping("/user-event")
    public String myEvent(Model model, Authentication auth) {
        User data = userService.getByProfile(auth.getName());
        model.addAttribute("myevent", eventService.getMyEvent(data.getId()));
        model.addAttribute("user", userService.getByProfile(auth.getName()));
        model.addAttribute("auth", auth.getName());
        return "user/user-event";
    }

    @GetMapping("/create-user")
    public String form(UserDto userDto, Model model) {
        model.addAttribute("status", masterStatusService.getStatusByType(2L));
        return "user/create-user";
    }

    @PostMapping("/create-user")
    public String createUser(@Valid UserDto userDto, BindingResult result, Model model, Authentication auth) {
        if (result.hasErrors()) {
            model.addAttribute("status", masterStatusService.getStatusByType(2L));
            return "user/create-user";
        }
        userService.createUser(userDto);
        return "redirect:/login";
    }

    @GetMapping("/event/{id}")
    public String getByEvent(@PathVariable("id") Long id, Model model, Authentication auth) {
        model.addAttribute("events", userEventService.getByEvent(id));
        model.addAttribute("auth", auth.getName());
        return "user/user-event/" + id;
    }
    
    @GetMapping("/update-user")
    public String getUserById(Model model, Authentication auth) {
        User data = userService.getByProfile(auth.getName());
        model.addAttribute("user", userService.getById(data.getId()));
        model.addAttribute("status", masterStatusService.getStatusByType(2L));
        model.addAttribute("auth", auth.getName());
        return "user/update-user";
    }

    @PutMapping("/update-user")
    public String updateUserParticipant(@Valid UserDto userDto, BindingResult result, Model model, Authentication auth) {
        User data = userService.getByProfile(auth.getName());
        if (result.hasErrors()) {
            model.addAttribute("status", masterStatusService.getStatusByType(2L));
            model.addAttribute("auth", auth.getName());
            return "user/update-user";
        }
        userService.updateUser(data.getId(), userDto);
        return "redirect:/user/update-user";
    }
}
