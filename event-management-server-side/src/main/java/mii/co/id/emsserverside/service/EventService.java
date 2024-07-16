package mii.co.id.emsserverside.service;

//<editor-fold defaultstate="collapsed" desc="Import">
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import mii.co.id.emsserverside.model.Event;
import mii.co.id.emsserverside.model.MasterStatus;
import mii.co.id.emsserverside.model.Topic;
import mii.co.id.emsserverside.model.User;
import mii.co.id.emsserverside.model.data.EventDto;
import mii.co.id.emsserverside.repository.EventRepository;
import mii.co.id.emsserverside.repository.MasterStatusRepository;
import mii.co.id.emsserverside.repository.TopicRepository;
import mii.co.id.emsserverside.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
//</editor-fold>

@Service
public class EventService {

    private EventRepository eventRepository;
    private UserRepository userRepository;
    private TopicRepository topicRepository;
    private MasterStatusRepository masterStatusRepository;

    @Autowired
    public EventService(EventRepository eventRepository, UserRepository userRepository, TopicRepository topicRepository, MasterStatusRepository masterStatusRepository) {
        this.eventRepository = eventRepository;
        this.userRepository = userRepository;
        this.topicRepository = topicRepository;
        this.masterStatusRepository = masterStatusRepository;
    }

    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    public List<Object> data(EventDto eventDto) {
        User user = userRepository.findById(eventDto.getUser()).get();
        Topic topic = topicRepository.findById(eventDto.getTopic()).get();
        MasterStatus statusEvent = masterStatusRepository.findById(eventDto.getEventStatus()).get();

        return Arrays.asList(user, topic, statusEvent);
    }

    public Long countEvent() {
        return eventRepository.countEvent();
    }

    public Long countCanceledEvent() {
        return eventRepository.countCanceledEvent();
    }

    public List<Object> topTen() {
        return eventRepository.topTen();
    }

    public Map<String, List<String>> lastSeven() {
        List<String> lastSeven = eventRepository.lastSeven();
        List<String> count = new ArrayList<>();
        List<String> month = new ArrayList<>();
        lastSeven.forEach(data -> {
            List<String> comma = Arrays.asList(data.split(","));
            count.add(comma.get(0));
            month.add(comma.get(1));
        });
        Map<String, List<String>> sevenData = new HashMap<>();
        sevenData.put("count", count);
        sevenData.put("month", month);
        return sevenData;
    }

    public List<Event> getAll() {
        return eventRepository.findAll();
    }

    public List<Event> getByTopic(Long id) {
        return eventRepository.findByTopic_id(id);
    }

    public Event getById(Long id) {
        return eventRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                "Event was not found"));
    }

    public List<Event> getByUser(Long userId) {
        return eventRepository.findByUser_id(userId);
    }
    
    public List<Object> getMyEvent(Long id){
        return  eventRepository.myEvent(id);
    }

    public Event create(EventDto eventDto) {
        List<Object> data = data(eventDto);
        Event event = Event.builder()
                .name(eventDto.getName())
                .eventImage(eventDto.getEventImage())
                .registrationStart(eventDto.getRegistrationStart())
                .registrationEnd(eventDto.getRegistrationEnd())
                .eventStart(eventDto.getEventStart())
                .eventEnd(eventDto.getEventEnd())
                .location(eventDto.getLocation())
                .isDeleted(null)
                .description(eventDto.getDescription())
                .capacity(eventDto.getCapacity())
                .user((User) data.get(0))
                .topic((Topic) data.get(1))
                .eventStatus((MasterStatus) data.get(2))
                .build();
        return eventRepository.save(event);
    }

    public Event update(EventDto eventDto, Long id) {
        getById(id);
        List<Object> data = data(eventDto);
        MasterStatus statusEvent = (MasterStatus) data.get(2);
        LocalDateTime h3 = eventDto.getEventStart().minusDays(3);
        if (statusEvent.getName().equals("Canceled") && h3.isBefore(LocalDateTime.now())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Event Cannot Canceled Because Event Start H-3");
        }
        Event event = Event.builder()
                .id(id)
                .name(eventDto.getName())
                .eventImage(eventDto.getEventImage())
                .registrationStart(eventDto.getRegistrationStart())
                .registrationEnd(eventDto.getRegistrationEnd())
                .eventStart(eventDto.getEventStart())
                .eventEnd(eventDto.getEventEnd())
                .location(eventDto.getLocation())
                .description(eventDto.getDescription())
                .capacity(eventDto.getCapacity())
                .user((User) data.get(0))
                .topic((Topic) data.get(1))
                .eventStatus((MasterStatus) data.get(2))
                .build();
        eventRepository.save(event);
        return event;
    }

    public Event buttonCancelEvent(Long id) {
        Event data = getById(id);
        MasterStatus statusEvent = masterStatusRepository.findById(data.getEventStatus().getId()).get();
        LocalDateTime h3 = data.getEventStart().minusDays(3);
        if (statusEvent.getName().equals("Canceled") && h3.isBefore(LocalDateTime.now())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Event Cannot Canceled Because Event Start H-3");
        }
        MasterStatus statusSet = masterStatusRepository.findById(6L).get();
        Event event = Event.builder()
                .id(id)
                .name(data.getName())
                .eventImage(data.getEventImage())
                .registrationStart(data.getRegistrationStart())
                .registrationEnd(data.getRegistrationEnd())
                .eventStart(data.getEventEnd())
                .eventEnd(data.getEventStart())
                .location(data.getLocation())
                .description(data.getDescription())
                .capacity(data.getCapacity())
                .user(data.getUser())
                .topic(data.getTopic())
                .eventStatus(statusSet)
                .build();
        return eventRepository.save(event);
    }

    public Event delete(Long id) {
        Event event = getById(id);
        eventRepository.deleteById(id);
        return event;
    }
}
