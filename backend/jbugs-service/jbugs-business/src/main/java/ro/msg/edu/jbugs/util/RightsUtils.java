package ro.msg.edu.jbugs.util;

import exceptions.BusinessException;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ws.rs.core.Response;

/**
 * Document me.
 *
 * @author msg systems AG; User Name.
 * @since 19.1.2
 */

@Stateless
public class RightsUtils {

    @EJB
    PermissionChecker permissionChecker;

    public Response checkUserRights(String username, String requiredPermission) {
        if (username == null) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("User is not logged in !")
                    .build();
        }

        try {
            if (!permissionChecker.checkPermission(username, requiredPermission))
                return Response.status(Response.Status.BAD_REQUEST)
                        .entity("User doesn't have the required permissions " + requiredPermission + " !")
                        .build();
        } catch (BusinessException e) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(e.getMessage())
                    .build();
        }
        return null;
    }
}
