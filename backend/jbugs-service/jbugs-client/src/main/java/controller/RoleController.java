package controller;

import exceptions.BusinessException;
import ro.msg.edu.jbugs.dto.RoleDTO;
import ro.msg.edu.jbugs.dto.RolePermissionDTO;
import ro.msg.edu.jbugs.managers.interfaces.RoleManagerRemote;
import utils.RightsUtils;

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

    @EJB
    RightsUtils rightsUtils;

    @GET
    public List<RoleDTO> getAllRoles() {
        return roleManagerRemote.findAllRoles();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_PLAIN)
    public Response modifyRolePermission(@CookieParam("username") String username, RolePermissionDTO rolePermissionDTO) {
        Response response = rightsUtils.checkUserRights(username, "PERMISSION_MANAGEMENT");
        if(response != null)
            return response;
        try {
            roleManagerRemote.modifyRolePermission(rolePermissionDTO.getRoleDTO(), rolePermissionDTO.getPermissionDTO());
            return Response
                    .status(Response.Status.OK)
                    .entity("You request was carried out successfully.")
                    .build();
        } catch (BusinessException e) {
            e.printStackTrace();
            return Response
                    .status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("You request couldn't be carried out successfully.")
                    .build();
        }
    }
}
