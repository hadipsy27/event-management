package mii.co.id.emsserverside.model.data;

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
public class UserDto {
    
    private Long id;

    private String name;
    
    private String address;
    
    private String email;
    
    private String password;
    
    private String phoneNumber;
    
    private String companyUniversity;
    
    private String jobTitleYears;
    
    private Long role;
    
    private Long statusUser;
}
