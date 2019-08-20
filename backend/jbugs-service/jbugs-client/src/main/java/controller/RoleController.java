package controller;

import ro.msg.edu.jbugs.dto.RoleDTO;
import ro.msg.edu.jbugs.dto.RolePermissionDTO;
import ro.msg.edu.jbugs.managers.interfaces.RoleManagerRemote;

import javax.ejb.EJB;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

/**
 * Document me.
 *
 * @author msg systems AG; User Name.
 * @since 19.1.2
 */
@Path("/roles")
@Produces("application/json")
public class RoleController {
    @EJB
    RoleManagerRemote roleManagerRemote;

    @GET
    public List<RoleDTO> getAllRoles() {
        return roleManagerRemote.findAllRoles();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public void modifyRolePermission(RolePermissionDTO rolePermissionDTO) {
        roleManagerRemote.modifyRolePermission(rolePermissionDTO.getRoleDTO(), rolePermissionDTO.getPermissionDTO());
    }
}
