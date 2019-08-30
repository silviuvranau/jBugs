package controller;

import exceptions.BusinessException;
import ro.msg.edu.jbugs.dto.AttachmentDTO;
import ro.msg.edu.jbugs.dto.BugDTO;
import ro.msg.edu.jbugs.managers.interfaces.AttachmentManagerRemote;
import ro.msg.edu.jbugs.managers.interfaces.BugManagerRemote;
import utils.RightsUtils;

import javax.ejb.EJB;
import javax.ws.rs.CookieParam;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
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
}
