package mii.co.id.emsclientside.model.data;

//<editor-fold defaultstate="collapsed" desc="Import">
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
public class UserDto {

    private Long id;

    @NotEmpty(message = "Name is required")
    private String name;

    @NotEmpty(message = "Address is required")
    private String address;

    @NotEmpty(message = "Email is required")
    private String email;

    @NotEmpty(message = "Password is required")
    private String password;

    @NotEmpty(message = "Phone Number is required")
    private String phoneNumber;

    @NotEmpty(message = "Job or Years is required")
    private String jobTitleYears;

    @NotEmpty(message = "Company or University is required")
    private String companyUniversity;

    private Long role;

//    @NotNull(message = "Status is required")
    private Long statusUser;
}
