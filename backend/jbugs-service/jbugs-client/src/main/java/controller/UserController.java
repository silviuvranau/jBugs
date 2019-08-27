package controller;

import exceptions.BusinessException;
import ro.msg.edu.jbugs.dto.UserDTO;
import ro.msg.edu.jbugs.managers.interfaces.UserManagerRemote;
import ro.msg.edu.jbugs.util.PermissionChecker;

import javax.ejb.EJB;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/users")
@Produces("application/json")
public class UserController {

    @EJB
    UserManagerRemote userManager;

    @EJB
    PermissionChecker permissionChecker;

    @Context
    private HttpServletRequest request;

    @GET
    @Path("{userId}")
    public Response getUserById(@PathParam("userId") Integer userId) throws BusinessException {
        UserDTO result = userManager.findAUser(userId);
        return Response.ok(result).build();
    }

    @GET
    public Response getAllUsers() {
        List<UserDTO> result = userManager.findAllUsers();
        return Response.ok(result).build();
    }

    @POST
    public Response createUser(@Valid UserDTO userDTO) {
        Response response = checkUserManagementRights();
        if (response != null)
            return response;
        UserDTO result = userManager.insertUser(userDTO);
        return Response.ok(result).build();
    }

    @PUT
    public Response editUser(@Valid UserDTO userDTO) {
        Response response = checkUserManagementRights();
        if (response != null)
            return response;

        UserDTO result;
        try {
            result = userManager.modifyUser(userDTO);
        } catch (BusinessException e) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(e.getMessage())
                    .build();
        }

        return Response.ok(result).build();
    }

    private Response checkUserManagementRights() {
        HttpSession session = request.getSession();
        String loggedInUsername = (String) session.getAttribute("username");
        if (loggedInUsername == null) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("User is not logged in !")
                    .build();
        }

        try {
            if (!permissionChecker.checkPermission(loggedInUsername, "USER_MANAGEMENT"))
                return Response.status(Response.Status.BAD_REQUEST)
                        .entity("User doesn't have the required permissions (USER_MANAGEMENT) !")
                        .build();
        } catch (BusinessException e) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(e.getMessage())
                    .build();
        }
        return null;
    }


}

