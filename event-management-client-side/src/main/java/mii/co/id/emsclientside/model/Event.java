package mii.co.id.emsclientside.model;

//<editor-fold defaultstate="collapsed" desc="Import">
import java.time.LocalDate;
import java.time.LocalDateTime;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
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
public class Event {

    private Long id;

    private String name;

    private String eventImage;
    
    private String description;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate registrationStart;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate registrationEnd;

    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    private LocalDateTime eventStart;

    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    private LocalDateTime eventEnd;

    private String location;
            
    private LocalDate isDeleted;
        
    private Long capacity;

    private User user;

    private Topic topic; 

    private MasterStatus eventStatus;
}
