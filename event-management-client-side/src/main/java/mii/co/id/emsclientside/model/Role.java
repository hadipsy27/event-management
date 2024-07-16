package mii.co.id.emsclientside.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Role {

    private Long id;
    private String name;
    private RolePrivilege rolePrivilege;

    public Role(Long id) {
        this.id = id;
    }
}
