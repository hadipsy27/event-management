package mii.co.id.emsserverside.controller;

//<editor-fold defaultstate="collapsed" desc="Import">
import java.util.List;
import java.util.Optional;
import mii.co.id.emsserverside.model.UserEvent;
import mii.co.id.emsserverside.model.data.UserEventDto;
import mii.co.id.emsserverside.service.UserEventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
//</editor-fold>

@RestController
@RequestMapping("/user-event")
public class UserEventController {

    private UserEventService userEventService;

    @Autowired
    public UserEventController(UserEventService userEventService) {
        this.userEventService = userEventService;
    }

    @GetMapping("/count")
    public ResponseEntity<Long> countParticipant() {
        return new ResponseEntity(userEventService.countParticipant(), HttpStatus.OK);
    }

    @GetMapping("/count-month")
    public ResponseEntity<List<String>> countParticipantMonth() {
        return new ResponseEntity(userEventService.countParticipantMonth(), HttpStatus.OK);
    }

    @GetMapping("/event/{id}")
    public ResponseEntity<List<UserEvent>> getByEvent(@PathVariable("id") Long id) {
        return new ResponseEntity(userEventService.getByEvent(id), HttpStatus.OK);
    }
    
    @GetMapping("/accept/{eventId}/{userId}")
    public UserEvent buttonAccept(@PathVariable("eventId") Long eventId, @PathVariable("userId") Long userId) {
        return userEventService.buttonAccept(eventId, userId);
    }

    @GetMapping("/reject/{eventId}/{userId}")
    public UserEvent buttonReject(@PathVariable("eventId") Long eventId, @PathVariable("userId") Long userId) {
        return userEventService.buttonReject(eventId, userId);
    }

    @PostMapping
    public ResponseEntity<UserEvent> create(@RequestBody UserEventDto userEventDto) {
        return new ResponseEntity(userEventService.create(userEventDto), HttpStatus.OK);
    }
}
