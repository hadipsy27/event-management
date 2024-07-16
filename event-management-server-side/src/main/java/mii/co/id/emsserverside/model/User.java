package mii.co.id.emsserverside.model;

//<editor-fold defaultstate="collapsed" desc="Import">
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
import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;
//</editor-fold>

@Entity
@Table(name = "user")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@SQLDelete(sql = "UPDATE user SET is_deleted = CURRENT_TIMESTAMP WHERE user_id = ?")
@Where(clause = "is_deleted IS NULL")
public class User{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    @Column(name = "full_name", nullable = false)
    private String name;

    @Column(nullable = false)
    private String address;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(name = "phone_number", unique = true, nullable = false)
    private String phoneNumber;

    @Column(name = "company_school_university", nullable = false)
    private String companyUniversity;

    @Column(name = "job_title_years", nullable = false)
    private String jobTitleYears;

    @Column(name = "is_active", nullable = false)
    private Boolean isActive;
    
    @Column(name = "is_deleted")
    private LocalDate isDeleted;

    @ManyToOne(fetch = FetchType.EAGER)
    private Role role;

    @ManyToOne(fetch = FetchType.EAGER)
    private MasterStatus statusUser;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<UserEvent> userEvents;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Event> events;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    private List<Token> tokens;
}
