package mii.co.id.emsclientside.controller;

//<editor-fold defaultstate="collapsed" desc="Import">
import java.io.IOException;
import java.util.List;
import java.util.Map;
import javax.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import mii.co.id.emsclientside.model.UserEvent;
import mii.co.id.emsclientside.model.data.EventDto;
import mii.co.id.emsclientside.service.EventService;
import mii.co.id.emsclientside.service.MasterStatusService;
import mii.co.id.emsclientside.service.TopicService;
import mii.co.id.emsclientside.service.UserEventService;
import mii.co.id.emsclientside.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
//</editor-fold>

@Controller
@RequestMapping("/eo")
@Slf4j
@PreAuthorize("hasAnyRole('EVENT_ORGANIZER', 'ADMIN')")
public class EoController {

    private EventService eventService;
    private UserService userService;
    private UserEventService userEventService;
    private MasterStatusService masterStatusService;
    private TopicService topicService;

    @Autowired
    public EoController(EventService eventService, UserService userService, UserEventService userEventService, MasterStatusService masterStatusService, TopicService topicService) {
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
        return "eo/index-eo";
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
    public String manageEvent(Model model, Long id, Authentication auth) {
        model.addAttribute("events", eventService.getByUser(id));
        model.addAttribute("auth", auth.getName());
        return "eo/manage-event";
    }

    @GetMapping("/detail-event/{id}")
    public String detailEvent(@PathVariable("id") Long id, Model model, Authentication auth) {
        model.addAttribute("event", eventService.getById(id));
        model.addAttribute("participant", userEventService.getByEvent(id));
        model.addAttribute("auth", auth.getName());
        return "eo/detail-event";
    }

    @GetMapping("/cancel-event/{id}")
    public String buttonCancelEvent(@PathVariable("id") Long id) {
        eventService.buttonCancelEvent(id);
        return "redirect:/eo/detail-event/" + id;
    }

    @GetMapping("/create-event")
    public String createEvent(EventDto eventDto, Model model, Authentication auth) {
        model.addAttribute("status", masterStatusService.getStatusByType(1L));
        model.addAttribute("topic", topicService.getAllTopic());
        model.addAttribute("auth", auth.getName());
        return "eo/create-event";
    }

    @PostMapping("/create-event")
    public String createEvent(@Valid EventDto eventDto, BindingResult result, Model model, Authentication auth,
            @RequestParam("image") MultipartFile multipartFile) throws IOException {
        if (result.hasErrors()) {
            model.addAttribute("topic", topicService.getAllTopic());
            model.addAttribute("status", masterStatusService.getStatusByType(1L));
            model.addAttribute("auth", auth.getName());
            return "eo/create-event";
        }
        eventService.createEvent(eventDto, multipartFile);
        return "redirect:/eo/manage-event";
    }

    @GetMapping("/update-event/{id}")
    public String getEventById(@PathVariable("id") Long id, Model model, Authentication auth) {
        model.addAttribute("event", eventService.getById(id));
        model.addAttribute("topic", topicService.getAllTopic());
        model.addAttribute("status", masterStatusService.getStatusByType(1L));
        model.addAttribute("auth", auth.getName());
        return "eo/update-event";
    }

    @PutMapping("/update-event/{id}")
    public String update(@PathVariable("id") Long id, @Valid EventDto eventDto, BindingResult result, Model model, Authentication auth,
            @RequestParam("image") MultipartFile multipartFile) throws IOException {
        if (result.hasErrors()) {
            model.addAttribute("topic", topicService.getAllTopic());
            model.addAttribute("status", masterStatusService.getStatusByType(1L));
            model.addAttribute("auth", auth.getName());
            return "eo/update-event";
        }
        eventService.update(id, eventDto, multipartFile);
        return "redirect:/eo/manage-event";
    }

    @GetMapping("/accept/{eventId}/{userId}")
    public String buttonAccept(@PathVariable("eventId") Long eventId, @PathVariable("userId") Long userId) {
        userEventService.buttonAccept(eventId, userId);
        return "redirect:/eo/detail-event/" + eventId;
    }

    @GetMapping("/reject/{eventId}/{userId}")
    public String buttonReject(@PathVariable("eventId") Long eventId, @PathVariable("userId") Long userId) {
        userEventService.buttonReject(eventId, userId);
        return "redirect:/eo/detail-event/" + eventId;
    }
}
