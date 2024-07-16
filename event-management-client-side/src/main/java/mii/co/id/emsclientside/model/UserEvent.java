package mii.co.id.emsclientside.model;

//<editor-fold defaultstate="collapsed" desc="Import">
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
//</editor-fold>

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserEvent {

    private Event event;

    private User user;

    private MasterStatus registrationStatus;
    
    
}
