package controller;

import ro.msg.edu.jbugs.dto.PermissionDTO;
import ro.msg.edu.jbugs.managers.interfaces.PermissionManagerRemote;

import javax.ejb.EJB;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import java.util.List;

/**
 * Document me.
 *
 * @author msg systems AG; User Name.
 * @since 19.1.2
 */
@Path("/permissions")
@Produces("application/json")
public class PermissionController {
    @EJB
    PermissionManagerRemote permissionManagerRemote;

    @GET
    public List<PermissionDTO> getAllPermissions() {
        return permissionManagerRemote.findAllPermissions();
    }
}
