package mii.co.id.emsserverside.model;

//<editor-fold defaultstate="collapsed" desc="Import">
import com.fasterxml.jackson.annotation.JsonProperty;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import mii.co.id.emsserverside.model.key.RolePrivilegeKey;
//</editor-fold>

@Entity
@Table(name = "role_privilege")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RolePrivilege {

    @EmbeddedId
    private RolePrivilegeKey id;

    @ManyToOne(fetch = FetchType.EAGER)
    @MapsId("roleId")
    @JoinColumn(name = "role_id")
    private Role role;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @ManyToOne(fetch = FetchType.EAGER)
    @MapsId("privilegeId")
    @JoinColumn(name = "privilege_id")
    private Privilege privilege;
}
