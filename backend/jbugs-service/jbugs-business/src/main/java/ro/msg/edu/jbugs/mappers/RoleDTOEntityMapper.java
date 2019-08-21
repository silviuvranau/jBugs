package ro.msg.edu.jbugs.mappers;

import entity.Permission;
import entity.Role;
import ro.msg.edu.jbugs.dto.PermissionDTO;
import ro.msg.edu.jbugs.dto.RoleDTO;

import java.util.HashSet;
import java.util.List;
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

        List<Permission> permissions = roleDTO.getPermissions().stream().map(PermissionDTOEntityMapper::getPermissionFromDto).collect(Collectors.toList());

        role.setPermissions(new HashSet<Permission>(permissions));
        return role;
    }

    public static RoleDTO getDtoFromRole(Role role) {
        RoleDTO roleDTO = new RoleDTO();
        roleDTO.setId(role.getId());
        roleDTO.setType(role.getType());

        List<PermissionDTO> permissionsDto = role.getPermissions().stream().map(PermissionDTOEntityMapper::getDtoFromPermission).collect(Collectors.toList());
        roleDTO.setPermissions(new HashSet<PermissionDTO>(permissionsDto));

        return roleDTO;
    }


}
