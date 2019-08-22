package controller;

import ro.msg.edu.jbugs.dto.UserDTO;
import ro.msg.edu.jbugs.managers.interfaces.UserManagerRemote;

import javax.ejb.EJB;
import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/users")
@Produces("application/json")
public class UserController {

    @EJB
    UserManagerRemote userManager;

    @GET
    @Path("{userId}")
    public Response getUserById(@PathParam("userId") Integer userId) {
        UserDTO result =  userManager.findAUser(userId);
        return Response.ok(result).build();
    }

    @GET
    public Response getAllUsers(){
        List<UserDTO> result =  userManager.findAllUsers();
        return Response.ok(result).build();
    }

    @POST
    public Response createUser(@Valid UserDTO userDTO) {
        UserDTO result =  userManager.insertUser(userDTO);
        return Response.ok(result).build();
    }
}
