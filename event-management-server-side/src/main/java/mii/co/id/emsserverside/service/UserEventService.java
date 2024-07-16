package mii.co.id.emsserverside.service;

//<editor-fold defaultstate="collapsed" desc="Import">
import java.util.List;
import java.util.Optional;
import mii.co.id.emsserverside.model.Event;
import mii.co.id.emsserverside.model.MasterStatus;
import mii.co.id.emsserverside.model.User;
import mii.co.id.emsserverside.model.UserEvent;
import mii.co.id.emsserverside.model.data.UserEventDto;
import mii.co.id.emsserverside.model.key.UserEventKey;
import mii.co.id.emsserverside.repository.EventRepository;
import mii.co.id.emsserverside.repository.MasterStatusRepository;
import mii.co.id.emsserverside.repository.UserEventRepository;
import mii.co.id.emsserverside.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
//</editor-fold>

@Service
public class UserEventService {

    private UserEventRepository userEventRepository;
    private MasterStatusRepository masterStatusRepository;
    private UserRepository userRepository;
    private EventRepository eventRepository;

    @Autowired
    public UserEventService(UserEventRepository userEventRepository, MasterStatusRepository masterStatusRepository, UserRepository userRepository, EventRepository eventRepository) {
        this.userEventRepository = userEventRepository;
        this.masterStatusRepository = masterStatusRepository;
        this.userRepository = userRepository;
        this.eventRepository = eventRepository;
    }

    public Long countParticipant() {
        return userEventRepository.countParticipant();
    }

    public List<String> countParticipantMonth() {
        return userEventRepository.countParticipantMonth();
    }

    public List<UserEvent> getByEvent(Long id) {
        return userEventRepository.findByEvent_id(id);
    }
    
    public List<UserEvent> getAll() {
        return userEventRepository.findAll();
    }

    @Transactional
    public UserEvent create(UserEventDto userEventDto) {
        User user = userRepository.findById(userEventDto.getUserId()).get();
        Event event = eventRepository.findById(userEventDto.getEventId()).get();
        MasterStatus masterStatus = masterStatusRepository.findById(10L).get();
        UserEvent userEvent = UserEvent.builder()
                .id(UserEventKey.builder()
                        .userId(user.getId())
                        .eventId(event.getId())
                        .build())
                .event(event)
                .user(user)
                .registrationStatus(masterStatus)
                .build();
        return userEventRepository.save(userEvent);
    }

    public UserEvent buttonAccept(Long eventId, Long userId) {
        User dataUser = userRepository.findById(userId).get();
        Event dataEvent = eventRepository.findById(eventId).get();
        MasterStatus statusEvent = masterStatusRepository.findById(11L).get();
        UserEvent event = UserEvent.builder()
                .id(UserEventKey.builder()
                        .userId(dataUser.getId())
                        .eventId(dataEvent.getId())
                        .build())
                .registrationStatus(statusEvent)
                .build();
        return userEventRepository.save(event);
    }

    public UserEvent buttonReject(Long eventId, Long userId) {
        User dataUser = userRepository.findById(userId).get();
        Event dataEvent = eventRepository.findById(eventId).get();
        MasterStatus statusEvent = masterStatusRepository.findById(12L).get();
        UserEvent event = UserEvent.builder()
                .id(UserEventKey.builder()
                        .userId(dataUser.getId())
                        .eventId(dataEvent.getId())
                        .build())
                .registrationStatus(statusEvent)
                .build();
        return userEventRepository.save(event);
    }
}
