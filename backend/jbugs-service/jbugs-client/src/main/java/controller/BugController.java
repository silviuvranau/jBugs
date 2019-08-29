package controller;

import exceptions.BusinessException;
import ro.msg.edu.jbugs.dto.BugDTO;
import ro.msg.edu.jbugs.dto.UserDTO;
import ro.msg.edu.jbugs.managers.interfaces.BugManagerRemote;
import utils.RightsUtilsClient;

import javax.ejb.EJB;
import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

/**
 * Document me.
 *
 * @author msg systems AG; User Name.
 * @since 19.1.2
 */
@Path("/bugs")
@Produces("application/json")
public class BugController {
    @EJB
    BugManagerRemote bugManagerRemote;

    @EJB
    RightsUtilsClient rightsUtilsClient;

    @GET
    public Response getAllBugs(@CookieParam("username") String username) {
        Response response = rightsUtilsClient.checkUserRights(username, "BUG_MANAGEMENT");
        if(response != null)
            return response;

        List<BugDTO> result = bugManagerRemote.findAllBugs();
        return Response.ok(result).build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_PLAIN)
    @Path("/canDeactivateUser")
    public Response checkIfCanDeactivateUser(@CookieParam("username") String username, UserDTO userDTO) {
        Response response = rightsUtilsClient.checkUserRights(username, "USER_MANAGEMENT");
        if(response != null)
            return response;

        try {
            bugManagerRemote.canDeactivateUser(userDTO);
            return Response
                    .status(Response.Status.OK)
                    .entity("true")
                    .build();
        } catch (BusinessException e) {
            return Response
                    .status(Response.Status.OK)
                    .entity("false")
                    .build();
        }
    }

    @PUT
    public Response modifyBug(@CookieParam("username") String username, @Valid BugDTO bugDTO) {
        Response response = rightsUtilsClient.checkUserRights(username, "BUG_MANAGEMENT");
        if(response != null)
            return response;

        BugDTO result;
        try {
            result = bugManagerRemote.updateBug(bugDTO, username);
        }
        catch (BusinessException e){
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(e.getMessage())
                    .build();
        }

        return Response.ok(result).build();
    }

    @POST
    public Response insertBug(@CookieParam("username") String username, @Valid BugDTO bugDTO) {
        Response response = rightsUtilsClient.checkUserRights(username, "BUG_MANAGEMENT");
        if(response != null)
            return response;
        try {
            BugDTO result = bugManagerRemote.insertBug(bugDTO);
            return Response
                    .status(Response.Status.OK)
                    .entity(result)
                    .build();
        } catch (BusinessException e) {
            e.printStackTrace();
            return Response
                    .status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(e.getMessage())
                    .build();
        }
    }

}
