package controller;

import exceptions.BusinessException;
import ro.msg.edu.jbugs.dto.AttachmentDTO;
import ro.msg.edu.jbugs.managers.interfaces.AttachmentManagerRemote;
import utils.RightsUtils;

import javax.ejb.EJB;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.util.List;

/**
 * Document me.
 *
 * @author msg systems AG; User Name.
 * @since 19.1.2
 */
@Path("/attachments")
@Produces("application/json")
public class AttachmentController {
    @EJB
    AttachmentManagerRemote attachmentManagerRemote;

    @EJB
    RightsUtils rightsUtils;

    @GET
    public Response getAllAttachments(@CookieParam("username") String username) {
        Response response = rightsUtils.checkUserRights(username, "BUG_MANAGEMENT");
        if(response != null)
            return response;

        try {
            List<AttachmentDTO> result = attachmentManagerRemote.getAllAttachments();
            return Response.ok(result).build();
        } catch (BusinessException e) {
            return  Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(e.getMessage())
                    .build();
        }
    }

    @DELETE
    @Path("{attId}")
    public Response deleteAttachment(@CookieParam("username") String username, @PathParam("attId") Integer attachmentID){
        Response response = rightsUtils.checkUserRights(username, "BUG_MANAGEMENT");
        if(response != null)
            return response;
        attachmentManagerRemote.deleteAttachment(attachmentID);
        try {
            return Response.ok(attachmentManagerRemote.getAllAttachments()).build();
        } catch (BusinessException e) {
            return  Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(e.getMessage())
                    .build();
        }
    }
}
