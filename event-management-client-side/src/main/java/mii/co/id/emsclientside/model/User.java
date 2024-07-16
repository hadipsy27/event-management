package mii.co.id.emsclientside.model;

//<editor-fold defaultstate="collapsed" desc="Import">
import java.time.LocalDate;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
//</editor-fold>

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class User {

    private Long id;

    private String name;

    private String address;

    private String email;

    private String password;

    private String phoneNumber;

    private String jobTitleYears;

    private String companyUniversity;

    private Boolean isActive;

    private LocalDate isDeleted;

    private Role role;
    
    private MasterStatus statusUser;

    public User(Long id) {
        this.id = id;
    }
}
