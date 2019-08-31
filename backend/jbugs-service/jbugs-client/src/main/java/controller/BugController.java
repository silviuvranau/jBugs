package controller;

import exceptions.BusinessException;
import ro.msg.edu.jbugs.dto.BugAttachmentWrapperDTO;
import ro.msg.edu.jbugs.dto.BugDTO;
import ro.msg.edu.jbugs.dto.UserDTO;
import ro.msg.edu.jbugs.managers.interfaces.BugManagerRemote;
import utils.RightsUtils;

import javax.ejb.EJB;
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
    RightsUtils rightsUtils;

    @GET
    @Path("{bugId}")
    public Response getBugById(@CookieParam("username") String username,
                               @PathParam("bugId") Integer bugId) {
        Response response = rightsUtils.checkUserRights(username, "BUG_MANAGEMENT");
        if (response != null)
            return response;

        BugDTO result;
        try {
            result = bugManagerRemote.findABug(bugId);
        } catch (BusinessException e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        }
        return Response.ok(result).build();
    }

    @GET
    public Response getAllBugs(@CookieParam("username") String username) {
        Response response = rightsUtils.checkUserRights(username, "BUG_MANAGEMENT");
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
        Response response = rightsUtils.checkUserRights(username, "USER_MANAGEMENT");
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
    public Response modifyBug(@CookieParam("username") String username, BugAttachmentWrapperDTO bugAttWrapperDTO) {
        Response response = rightsUtils.checkUserRights(username, "BUG_MANAGEMENT");
        if(response != null)
            return response;

        BugAttachmentWrapperDTO result;
        try {
            result = bugManagerRemote.updateBug(bugAttWrapperDTO, username);
        }
        catch (BusinessException e){
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(e.getMessage())
                    .build();
        }

        return Response.ok(result).build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response insertBug(@CookieParam("username") String username, BugAttachmentWrapperDTO bugAttWrapperDTO) {
        Response response = rightsUtils.checkUserRights(username, "BUG_MANAGEMENT");
        if(response != null)
            return response;
        try {
            BugAttachmentWrapperDTO result = bugManagerRemote.insertBug(bugAttWrapperDTO);
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
