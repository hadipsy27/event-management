package mii.co.id.emsserverside.model;

//<editor-fold defaultstate="collapsed" desc="Import">
import com.fasterxml.jackson.annotation.JsonProperty;
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
import lombok.Data;
import lombok.NoArgsConstructor;
//</editor-fold>

@Entity
@Table(name = "master_status")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MasterStatus {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "master_status_id")
    private Long id;

    @Column(nullable = false)
    private String name;

    @ManyToOne(fetch = FetchType.EAGER)
    private StatusType statusType;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @OneToMany(mappedBy = "eventStatus", cascade = CascadeType.ALL)
    private List<Event> events;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @OneToMany(mappedBy = "registrationStatus", cascade = CascadeType.ALL)
    private List<UserEvent> userEvents;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @OneToMany(mappedBy = "statusUser", cascade = CascadeType.ALL)
    private List<User> users;
}
