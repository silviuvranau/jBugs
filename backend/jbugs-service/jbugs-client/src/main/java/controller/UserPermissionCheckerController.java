package controller;

import exceptions.BusinessException;
import ro.msg.edu.jbugs.dto.UserPermissionCheckerDTO;
import ro.msg.edu.jbugs.util.PermissionChecker;

import javax.ejb.EJB;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * Document me.
 *
 * @author msg systems AG; User Name.
 * @since 19.1.2
 */
@Path("/userPermission")
@Produces("application/json")
public class UserPermissionCheckerController {
    @EJB
    PermissionChecker permissionChecker;

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_PLAIN)
    public Response checkIfUserHasPermission(UserPermissionCheckerDTO userPermissionCheckerDTO) {
        boolean hasPermission;
        try {
            hasPermission = permissionChecker.checkPermission(userPermissionCheckerDTO.getUsername(), userPermissionCheckerDTO.getRequiredPermissionType());
            return Response.ok(hasPermission).build();
        } catch (BusinessException e) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(e.getMessage())
                    .build();
        }
    }
}
