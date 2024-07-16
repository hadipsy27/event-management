package mii.co.id.emsserverside.model;

//<editor-fold defaultstate="collapsed" desc="Import">
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.*;
import java.time.LocalDateTime;
//</editor-fold>

@Entity
@Table
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Token {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "confirm_date")
    private LocalDateTime confirm;

    @Column(name = "created_date")
    private LocalDateTime created;

    @Column(name = "expired_date")
    private LocalDateTime expired;

    private String tokenCode;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private User user;

}
