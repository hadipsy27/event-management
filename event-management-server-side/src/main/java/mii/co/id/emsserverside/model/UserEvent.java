package mii.co.id.emsserverside.model;

//<editor-fold defaultstate="collapsed" desc="Import">
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import mii.co.id.emsserverside.model.key.UserEventKey;
//</editor-fold>

@Entity
@Table(name = "user_event")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserEvent {

    @EmbeddedId
    private UserEventKey id;

    @ManyToOne(fetch = FetchType.EAGER)
    @MapsId("eventId")
    @JoinColumn(name = "event_id")
    private Event event;

    @ManyToOne(fetch = FetchType.EAGER)
    @MapsId("userId")
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.EAGER)
    private MasterStatus registrationStatus;
}
