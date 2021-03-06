package ro.msg.edu.jbugs.dto;

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
    private Set<UserDTO> users;

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

    public Set<UserDTO> getUsers() {
        return users;
    }

    public void setUsers(Set<UserDTO> users) {
        this.users = users;
    }

    @Override
    public String toString() {
        return "RoleDTO{" +
                "id=" + id +
                ", type='" + type + '\'' +
                ", users=" + users +
                '}';
    }
}
