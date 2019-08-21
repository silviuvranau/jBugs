package ro.msg.edu.jbugs.dto;

import java.io.Serializable;

/**
 * Wrapper class for PermissionDTO & RoleDTO.
 *
 * @author msg systems AG; Pricop Stefania.
 * @since 19.1.2
 */
public class RolePermissionDTO implements Serializable {
    private RoleDTO roleDTO;
    private PermissionDTO permissionDTO;

    public RoleDTO getRoleDTO() {
        return roleDTO;
    }

    public void setRoleDTO(RoleDTO roleDTO) {
        this.roleDTO = roleDTO;
    }

    public PermissionDTO getPermissionDTO() {
        return permissionDTO;
    }

    public void setPermissionDTO(PermissionDTO permissionDTO) {
        this.permissionDTO = permissionDTO;
    }
}
