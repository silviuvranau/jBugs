package controller;

import ro.msg.edu.jbugs.dto.RoleDTO;
import ro.msg.edu.jbugs.managers.interfaces.RoleManagerRemote;

import javax.ejb.EJB;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/roles")
@Produces("application/json")
public class RoleController {
    @EJB
    RoleManagerRemote roleManager;

    @GET
    public Response getAllRoles(){
        List<RoleDTO> result =  roleManager.findAllRoles();
        return Response.ok(result).build();
    }

}
