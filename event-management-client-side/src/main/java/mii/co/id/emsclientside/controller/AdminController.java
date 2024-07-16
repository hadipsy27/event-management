package mii.co.id.emsclientside.controller;

//<editor-fold defaultstate="collapsed" desc="Import">
import java.io.IOException;
import javax.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import mii.co.id.emsclientside.model.User;
import mii.co.id.emsclientside.service.EventService;
import mii.co.id.emsclientside.service.MasterStatusService;
import mii.co.id.emsclientside.service.UserEventService;
import mii.co.id.emsclientside.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import java.util.List;
import java.util.Map;
import mii.co.id.emsclientside.model.data.EventDto;
import mii.co.id.emsclientside.model.data.UserDto;
import mii.co.id.emsclientside.service.TopicService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
//</editor-fold>

@Controller
@RequestMapping("/admin")
@Slf4j
@PreAuthorize("hasRole('ADMIN')")
public class AdminController {

    private EventService eventService;
    private UserService userService;
    private UserEventService userEventService;
    private MasterStatusService masterStatusService;
    private TopicService topicService;

    @Autowired
    public AdminController(EventService eventService, UserService userService, UserEventService userEventService, MasterStatusService masterStatusService, TopicService topicService) {
        this.eventService = eventService;
        this.userService = userService;
        this.userEventService = userEventService;
        this.masterStatusService = masterStatusService;
        this.topicService = topicService;
    }

    @GetMapping
    public String index(Model model, Authentication auth) {
        model.addAttribute("countEvent", eventService.countEvent());
        model.addAttribute("countUser", userService.countUser());
        model.addAttribute("countParticipant", userEventService.countParticipant());
        model.addAttribute("countCanceledEvent", eventService.countCanceledEvent());
        model.addAttribute("topTen", eventService.topTen());
        model.addAttribute("auth", auth.getName());
        return "admin/index-admin";
    }

    @GetMapping("/last-seven")
    @ResponseBody
    public Map<String, List<String>> lastSeven() {
        return eventService.lastSeven();
    }

    @GetMapping("/count-month")
    @ResponseBody
    public List<String> countParticipantMonth() {
        return userEventService.countParticipantMonth();
    }

// Manage Event
    @GetMapping("/manage-event")
    public String showEvent(Model model, Authentication auth) {
        model.addAttribute("events", eventService.getAll());
        model.addAttribute("auth", auth.getName());
        return "admin/manage-event";
    }

    @GetMapping("/detail-event/{id}")
    public String detailEvent(@PathVariable("id") Long id, Model model, Authentication auth) {
        model.addAttribute("event", eventService.getById(id));
        model.addAttribute("participant", userEventService.getByEvent(id));
        model.addAttribute("auth", auth.getName());
        return "admin/detail-event";
    }

    @GetMapping("/cancel-event/{id}")
    public String buttonCancelEvent(@PathVariable("id") Long id) {
        eventService.buttonCancelEvent(id);
        return "redirect:/admin/detail-event/" + id;
    }

    @GetMapping("/update-event/{id}")
    public String getEventById(@PathVariable("id") Long id, Model model, Authentication auth) {
        model.addAttribute("event", eventService.getById(id));
        model.addAttribute("topic", topicService.getAllTopic());
        model.addAttribute("status", masterStatusService.getStatusByType(1L));
        model.addAttribute("auth", auth.getName());
        return "admin/update-event";
    }

    @PutMapping("/update-event/{id}")
    public String update(@PathVariable("id") Long id, @Valid EventDto eventDto, BindingResult result, Model model, Authentication auth,
            @RequestParam("image") MultipartFile multipartFile) throws IOException {
        if (result.hasErrors()) {
            model.addAttribute("topic", topicService.getAllTopic());
            model.addAttribute("status", masterStatusService.getStatusByType(1L));
            model.addAttribute("auth", auth.getName());
            return "admin/update-event";
        }
        eventService.update(id, eventDto, multipartFile);
        return "redirect:/admin/manage-event";
    }

// Manage Event Organizer
    @GetMapping("/manage-eo")
    public String getDataEo(Model model, String role, Authentication auth) {
        model.addAttribute("auth", auth.getName());
        return "admin/manage-eo";
    }

    @GetMapping("/create-eo")
    public String form(UserDto userDto, Model model, Authentication auth) {
        model.addAttribute("status", masterStatusService.getStatusByType(2L));
        model.addAttribute("auth", auth.getName());
        return "/admin/create-eo";
    }

    @GetMapping("/update-eo/{id}")
    public String getUserById(@PathVariable("id") Long id, Model model, Authentication auth) {
        model.addAttribute("user", userService.getById(id));
        model.addAttribute("status", masterStatusService.getStatusByType(2L));
        model.addAttribute("auth", auth.getName());
        return "admin/update-eo";
    }

    @PostMapping("/create-eo")
    public String create(@Valid UserDto userDto, BindingResult result, Model model, Authentication auth) {
        if (result.hasErrors()) {
            model.addAttribute("status", masterStatusService.getStatusByType(2L));
            model.addAttribute("auth", auth.getName());
            return "admin/create-eo";
        }
        userService.createEo(userDto);
        return "redirect:/admin/manage-eo";
    }

    @PutMapping("/update-eo/{id}")
    public String update(@PathVariable("id") Long id, @Valid UserDto userDto, BindingResult result, Model model, Authentication auth) {
        if (result.hasErrors()) {
            model.addAttribute("status", masterStatusService.getStatusByType(2L));
            model.addAttribute("auth", auth.getName());
            return "admin/update-eo";
        }
        userService.updateEo(id, userDto);
        return "redirect:/admin/manage-eo";
    }

// Manage User
    @GetMapping("/manage-user")
    public String getDataUser(Model model, String role, Authentication auth) {
        model.addAttribute("auth", auth.getName());
        return "admin/manage-user";
    }

    @GetMapping("/update-user/{id}")
    public String editUser(@PathVariable("id") Long id, Model model, Authentication auth) {
        model.addAttribute("user", userService.getById(id));
        model.addAttribute("status", masterStatusService.getStatusByType(2L));
        model.addAttribute("auth", auth.getName());
        return "admin/update-user";
    }

    @PutMapping("/update-user/{id}")
    public String updateUser(@PathVariable("id") Long id, @Valid UserDto userDto, BindingResult result, Model model, Authentication auth) {
        if (result.hasErrors()) {
            model.addAttribute("status", masterStatusService.getStatusByType(2L));
            model.addAttribute("auth", auth.getName());
            return "admin/update-user";
        }
        userService.updateUser(id, userDto);
        return "redirect:/admin/manage-user";
    }

// Use for EO and User
    @GetMapping("/get-byrole/{role}")
    @ResponseBody
    public List<User> getByRole(@PathVariable("role") String role) {
        return userService.getByRole(role);
    }

    @DeleteMapping("/delete/{id}")
    @ResponseBody
    public User delete(@PathVariable("id") Long id) {
        return userService.delete(id);
    }
}
