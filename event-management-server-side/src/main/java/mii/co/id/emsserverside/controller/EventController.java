package mii.co.id.emsserverside.controller;

//<editor-fold defaultstate="collapsed" desc="Import">
import java.util.List;
import java.util.Map;
import mii.co.id.emsserverside.model.Event;
import mii.co.id.emsserverside.model.data.EventDto;
import mii.co.id.emsserverside.service.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
//</editor-fold>

@RestController
@RequestMapping("/event")
public class EventController {

    private EventService eventService;

    @Autowired
    public EventController(EventService eventService) {
        this.eventService = eventService;
    }

    @GetMapping("/count")
    public ResponseEntity<Long> countEvent() {
        return new ResponseEntity(eventService.countEvent(), HttpStatus.OK);
    }

    @GetMapping("/count-canceled")
    public ResponseEntity<Long> countCanceledEvent() {
        return new ResponseEntity(eventService.countCanceledEvent(), HttpStatus.OK);
    }

    @GetMapping("/top-ten")
    public ResponseEntity<List<Object>> topTen() {
        return new ResponseEntity(eventService.topTen(), HttpStatus.OK);
    }

    @GetMapping("/last-seven")
    public ResponseEntity<Map<String, List<String>>> lastSeven() {
        return new ResponseEntity(eventService.lastSeven(), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<Event>> getAll() {
        return new ResponseEntity(eventService.getAll(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<List<Event>> getById(@PathVariable("id") Long id) {
        return new ResponseEntity(eventService.getById(id), HttpStatus.OK);
    }

    @GetMapping("/topic/{id}")
    public ResponseEntity<List<Event>> getByTopic(@PathVariable("id") Long id) {
        return new ResponseEntity(eventService.getByTopic(id), HttpStatus.OK);
    }

    @GetMapping("/user/{id}")
    public ResponseEntity<List<Event>> getByUser(@PathVariable("id") Long id) {
        return new ResponseEntity(eventService.getByUser(id), HttpStatus.OK);
    }

    @GetMapping("/myevent/{id}")
    public ResponseEntity<List<Event>> getMyEvent(@PathVariable("id") Long id) {
        return new ResponseEntity(eventService.getMyEvent(id), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Event> create(@RequestBody EventDto eventDto) {
        return new ResponseEntity(eventService.create(eventDto), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public Event update(@RequestBody EventDto eventDto, @PathVariable("id") Long id) {
        return eventService.update(eventDto, id);
    }

    @DeleteMapping("/{id}")
    public Event delete(@PathVariable("id") Long id) {
        return eventService.delete(id);
    }

    @GetMapping("/cancel-event/{id}")
    public ResponseEntity<Event> cancelEvent(@PathVariable("id") Long id) {
        return new ResponseEntity(eventService.buttonCancelEvent(id), HttpStatus.OK);
    }
}
