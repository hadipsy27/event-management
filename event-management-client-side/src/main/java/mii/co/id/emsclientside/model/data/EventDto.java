package mii.co.id.emsclientside.model.data;

//<editor-fold defaultstate="collapsed" desc="Import">
import java.time.LocalDate;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
//</editor-fold>

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EventDto {

    private Long id;

    private String name;

    private String eventImage;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate registrationStart;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate registrationEnd;

    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    private LocalDateTime eventStart;

    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    private LocalDateTime eventEnd;

    private String location;
    
    private String description;
    
    private Long capacity;

    private Long user;

    private Long topic; 

    private Long eventStatus;
}
