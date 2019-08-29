package controller;

import exceptions.BusinessException;
import ro.msg.edu.jbugs.dto.UserDTO;
import ro.msg.edu.jbugs.managers.interfaces.UserManagerRemote;
import utils.RightsUtils;

import javax.ejb.EJB;
import javax.servlet.http.HttpServletRequest;
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

    @Context
    private HttpServletRequest request;

    @EJB
    RightsUtils rightsUtils;

    @GET
    @Path("{userId}")
    public Response getUserById(@PathParam("userId") Integer userId) throws BusinessException {
        UserDTO result =  userManager.findAUser(userId);
        return Response.ok(result).build();
    }

    @GET
    public Response getAllUsers(@CookieParam("username") String username){
        System.out.println("Cookie val" + username);
        List<UserDTO> result =  userManager.findAllUsers();
        return Response.ok(result).build();
    }

    @POST
    public Response createUser(@CookieParam("username") String username, @Valid UserDTO userDTO) {
        Response response = rightsUtils.checkUserRights(username, "USER_MANAGEMENT");
        if(response != null)
            return response;
        UserDTO result =  userManager.insertUser(userDTO);
        return Response.ok(result).build();
    }

    @PUT
    public Response editUser(@CookieParam("username") String username, @Valid UserDTO userDTO) {
        Response response = rightsUtils.checkUserRights(username, "USER_MANAGEMENT");
        if(response != null)
            return response;

        UserDTO result;
        try {
            result = userManager.modifyUser(userDTO);
        }
        catch (BusinessException e){
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(e.getMessage())
                    .build();
        }

        return Response.ok(result).build();
    }

}

