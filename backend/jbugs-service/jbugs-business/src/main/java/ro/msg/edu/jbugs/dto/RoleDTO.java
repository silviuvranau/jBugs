package ro.msg.edu.jbugs.dto;

import entity.Permission;

import java.io.Serializable;
import java.util.Set;

/**
 * Document me.
 *
 * @author msg systems AG; User Name.
 * @since 19.1.2
 */
public class RoleDTO implements Serializable {
    private Integer id;
    private String type;
    private Set<PermissionDTO> permissions;

    public Integer getId() {
        return id;
    }

    public void setId(Integer ID) {
        this.id = ID;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Set<PermissionDTO> getPermissions() {
        return permissions;
    }

    public void setPermissions(Set<PermissionDTO> permissions) {
        this.permissions = permissions;
    }

    @Override
    public String toString() {
        return "RoleDTO{" +
                "id=" + id +
                ", type='" + type + '\'' +
                ", permissions=" + permissions +
                '}';
    }
}
