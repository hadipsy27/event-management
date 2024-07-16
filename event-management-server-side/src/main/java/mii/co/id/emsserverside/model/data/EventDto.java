package mii.co.id.emsserverside.model.data;

//<editor-fold defaultstate="collapsed" desc="Import">
import java.time.LocalDate;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
//</editor-fold>

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EventDto {

    private String name;

    private String eventImage;

    private LocalDate registrationStart;

    private LocalDate registrationEnd;

    private LocalDateTime eventStart;

    private LocalDateTime eventEnd;

    private String location;
    
    private String description;
    
    private Long capacity;

    private Long user;

    private Long topic;

    private Long eventStatus;
}
