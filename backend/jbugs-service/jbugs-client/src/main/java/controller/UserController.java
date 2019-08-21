package controller;

import exceptions.BusinessException;
import ro.msg.edu.jbugs.dto.CredentialDTO;
import ro.msg.edu.jbugs.dto.UserDTO;
import ro.msg.edu.jbugs.managers.implementations.UserManager;
import ro.msg.edu.jbugs.managers.interfaces.UserManagerRemote;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path("/users")
@Produces("application/json")
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

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public UserDTO createUser(UserDTO userDTO) {
        return userManager.insertUser(userDTO);
    }
    @POST
    @Path("/auth")
    public UserDTO login(CredentialDTO credentialDTO) throws BusinessException {
        return userManager.login(credentialDTO.getUsername(),credentialDTO.getPassword());
    }
}
