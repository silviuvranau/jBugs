package controller;

import exceptions.BusinessException;
import ro.msg.edu.jbugs.dto.BugDTO;
import ro.msg.edu.jbugs.dto.UserDTO;
import ro.msg.edu.jbugs.managers.interfaces.BugManagerRemote;

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

    @GET
    public List<BugDTO> getAllBugs() {
        return bugManagerRemote.findAllBugs();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_PLAIN)
    @Path("/canDeactivateUser")
    public Response checkIfCanDeactivateUser(UserDTO userDTO) {

        try {
            bugManagerRemote.canDeactivateUser(userDTO);
            return Response
                    .status(Response.Status.OK)
                    .entity("You request was carried out successfully.")
                    .build();
        } catch (BusinessException e) {
            return Response
                    .status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("There are unclosed bugs!")
                    .build();
        }
    }

    //@POST
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_PLAIN)
    @Path("/{id}")
    public Response modifyBug(@PathParam("id") Integer id, @Valid BugDTO bugDTO) {
        try {
            bugManagerRemote.updateBug(id, bugDTO);
            return Response
                    .status(Response.Status.OK)
                    .entity("You request was carried out successfully.")
                    .build();
        } catch (BusinessException e) {
            e.printStackTrace();
            return Response
                    .status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(e.getMessage())
                    .build();
        }
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_PLAIN)
    public Response insertBug(@Valid BugDTO bugDTO) {
        try {
            BugDTO insertedBug = bugManagerRemote.insertBug(bugDTO);
            return Response
                    .status(Response.Status.OK)
                    .entity("You request was carried out successfully.")
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
