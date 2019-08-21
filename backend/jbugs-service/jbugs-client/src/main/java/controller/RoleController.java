package controller;

import ro.msg.edu.jbugs.dto.RoleDTO;
import ro.msg.edu.jbugs.dto.RolePermissionDTO;
import ro.msg.edu.jbugs.managers.interfaces.RoleManagerRemote;

import javax.ejb.EJB;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

/**
 * Document me.
 *
 * @author msg systems AG; Pricop Stefania.
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
    @Produces(MediaType.TEXT_PLAIN)
    public Response modifyRolePermission(RolePermissionDTO rolePermissionDTO) {
        Integer modifiedRows = roleManagerRemote.modifyRolePermission(rolePermissionDTO.getRoleDTO(), rolePermissionDTO.getPermissionDTO());
        if (modifiedRows == 0) {
            return Response
                    .status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Your request could not be carried out.")
                    .build();
        } else {
            return Response
                    .status(Response.Status.OK)
                    .entity("You request was carried out successfully.")
                    .build();
        }
    }
}
