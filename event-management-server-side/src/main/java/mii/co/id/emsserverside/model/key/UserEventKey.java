package mii.co.id.emsserverside.model.key;

//<editor-fold defaultstate="collapsed" desc="Import">
import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
//</editor-fold>

@Embeddable
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserEventKey implements Serializable {

    @Column(name = "event_id")
    private Long eventId;

    @Column(name = "user_id")
    private Long userId;
}
