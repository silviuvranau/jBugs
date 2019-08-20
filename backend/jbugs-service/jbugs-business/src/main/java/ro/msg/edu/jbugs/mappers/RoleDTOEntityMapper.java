package ro.msg.edu.jbugs.mappers;

import entity.Permission;
import entity.Role;
import entity.User;
import ro.msg.edu.jbugs.dto.PermissionDTO;
import ro.msg.edu.jbugs.dto.RoleDTO;
import ro.msg.edu.jbugs.dto.UserDTO;

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

        List<User> users = roleDTO.getUsers().stream().map(UserDTOEntityMapper::getUserFromUserDto).collect(Collectors.toList());
        role.setUsers(new HashSet<User>(users));

        List<Permission> permissions = roleDTO.getPermissions().stream().map(PermissionDTOEntityMapper::getPermissionFromDto).collect(Collectors.toList());
        role.setPermissions(new HashSet<Permission>(permissions));

        return role;
    }

    public static RoleDTO getDtoFromRole(Role role) {
        RoleDTO roleDTO = new RoleDTO();
        roleDTO.setId(role.getId());
        roleDTO.setType(role.getType());

        List<UserDTO> usersDto = role.getUsers().stream().map(UserDTOEntityMapper::getDtoFromUser).collect(Collectors.toList());
        roleDTO.setUsers(new HashSet<UserDTO>(usersDto));

        List<PermissionDTO> permissionsDto = role.getPermissions().stream().map(PermissionDTOEntityMapper::getDtoFromPermission).collect(Collectors.toList());
        roleDTO.setPermissions(new HashSet<PermissionDTO>(permissionsDto));


        return roleDTO;
    }
}
