package mii.co.id.emsclientside.model.data;

import javax.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserEventDto {

    @NotEmpty(message = "Event is required")
    private Long eventId;

    @NotEmpty(message = "User is required")
    private Long userId;

    @NotEmpty(message = "Status is required")
    private Long registrationStatusId;
}
