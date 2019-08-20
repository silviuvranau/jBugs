package ro.msg.edu.jbugs.mappers;

import entity.Role;
import ro.msg.edu.jbugs.dto.RoleDTO;

/**
 * Document me.
 *
 * @author msg systems AG; User Name.
 * @since 19.1.2
 */
public class RoleDTOEntityMapper {
    public static Role getRoleFromDto(RoleDTO roleDTO) {
        Role role = new Role();
        role.setId(roleDTO.getId());
        //role.setPermissions(roleDTO.ge);
        role.setType(roleDTO.getType());
        //role.setUsers(roleDTO.ge);

        return role;
    }

    public static RoleDTO getDtoFromRole(Role role) {
        RoleDTO roleDTO = new RoleDTO();
        roleDTO.setId(role.getId());
        roleDTO.setType(role.getType());

        return roleDTO;
    }
}
