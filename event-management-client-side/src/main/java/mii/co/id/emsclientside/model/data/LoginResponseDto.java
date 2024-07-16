package mii.co.id.emsclientside.model.data;

//<editor-fold defaultstate="collapsed" desc="Import">
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
//</editor-fold>

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LoginResponseDto {

    private List<String> authorities;

    private String username;
}
