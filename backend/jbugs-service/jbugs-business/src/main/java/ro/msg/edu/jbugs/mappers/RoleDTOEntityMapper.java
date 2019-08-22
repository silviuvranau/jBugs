package ro.msg.edu.jbugs.mappers;

import entity.Role;
import ro.msg.edu.jbugs.dto.RoleDTO;

import java.util.stream.Collectors;

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
        role.setType(roleDTO.getType());

        return role;
    }

    public static RoleDTO getDtoFromRole(Role role) {
        RoleDTO roleDTO = new RoleDTO();
        roleDTO.setId(role.getId());
        roleDTO.setType(role.getType());
        roleDTO.setUsers(role.getUsers().
                stream().
                map(UserDTOEntityMapper::getDtoFromUser).
                collect(Collectors.toSet()));

        return roleDTO;
    }
}
