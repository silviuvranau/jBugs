package ro.msg.edu.jbugs.mappers;

import entity.Permission;
import ro.msg.edu.jbugs.dto.PermissionDTO;

/**
 * Document me.
 *
 * @author msg systems AG; User Name.
 * @since 19.1.2
 */
public class PermissionDTOEntityMapper {
    public static Permission getPermissionFromDto(PermissionDTO permissionDTO) {
        Permission permission = new Permission();
        permission.setId(permissionDTO.getId());
        permission.setDescription(permissionDTO.getDescription());
        permission.setType(permissionDTO.getType());

//        List<Role> roles = permissionDTO.getRoles().stream().map(RoleDTOEntityMapper::getRoleFromDto).collect(Collectors.toList());
//        permission.setRoles(new HashSet<Role>(roles));

        return permission;
    }

    public static PermissionDTO getDtoFromPermission(Permission permission) {
        PermissionDTO permissionDTO = new PermissionDTO();
        permissionDTO.setId(permission.getId());
        permissionDTO.setDescription(permission.getDescription());
        permissionDTO.setType(permission.getType());

//        List<RoleDTO> rolesDto = permission.getRoles().stream().map(RoleDTOEntityMapper::getDtoFromRole).collect(Collectors.toList());
//        permissionDTO.setRoles(new HashSet<RoleDTO>(rolesDto));

        return permissionDTO;
    }
}
