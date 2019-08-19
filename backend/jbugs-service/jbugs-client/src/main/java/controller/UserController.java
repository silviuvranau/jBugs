package controller;

import ro.msg.edu.jbugs.dto.UserDTO;
import ro.msg.edu.jbugs.managers.implementations.UserManager;
import ro.msg.edu.jbugs.managers.interfaces.UserManagerRemote;

import javax.ejb.EJB;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import java.util.List;

@Path("/users")
public class UserController {

    @EJB
    UserManagerRemote userManager;

    @GET
    @Path("{userId}")
    public UserDTO getUserById(@PathParam("userId") Integer userId) {
        return userManager.findAUser(userId);
    }

    @GET
    public List<UserDTO> getAllUsers(){
        return userManager.findAllUsers();
    }
}
