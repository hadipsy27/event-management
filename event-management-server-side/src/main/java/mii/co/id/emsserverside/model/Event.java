package mii.co.id.emsserverside.model;

//<editor-fold defaultstate="collapsed" desc="Import">
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;
//</editor-fold>

@Entity
@Table(name = "event")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@SQLDelete(sql = "UPDATE event set is_deleted = CURRENT_TIMESTAMP WHERE event_id = ? ")
@Where(clause = "is_deleted IS NULL")
public class Event {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "event_id")
    private Long id;

    @Column(name = "event_name", unique = true, nullable = false)
    private String name;

    @Column(name = "event_image", nullable = false)
    private String eventImage;
    
    @Column(nullable = false)
    private String description;

    @Column(name = "registration_start", nullable = false)
    private LocalDate registrationStart;

    @Column(name = "registration_end", nullable = false)
    private LocalDate registrationEnd;

    @Column(name = "event_start", nullable = false)
    private LocalDateTime eventStart;

    @Column(name = "event_end", nullable = false)
    private LocalDateTime eventEnd;

    @Column(nullable = false)
    private String location;
    
    @Column(name = "is_deleted")
    private LocalDate isDeleted;
    
    @Column(nullable = false)
    private Long capacity;

    @ManyToOne(fetch = FetchType.EAGER)
    private User user;

    @ManyToOne(fetch = FetchType.EAGER)
    private Topic topic;

    @ManyToOne(fetch = FetchType.EAGER)
    private MasterStatus eventStatus;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @OneToMany(mappedBy = "event", cascade = CascadeType.ALL)
    private List<UserEvent> userEvents;
}
