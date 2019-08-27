package controller;

import exceptions.BusinessException;
import ro.msg.edu.jbugs.dto.CredentialDTO;
import ro.msg.edu.jbugs.dto.UserDTO;
import ro.msg.edu.jbugs.managers.interfaces.UserManagerRemote;

import javax.ejb.EJB;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.*;

/**
 * Document me.
 *
 * @author msg systems AG; User Name.
 * @since 19.1.2
 */
@Path("/login")
@Produces("application/json")
public class LoginController {

    @EJB
    UserManagerRemote userManager;

    @POST
    public Response login(CredentialDTO credentialDTO) {
        UserDTO userDto;
        try {
            userDto = userManager.login(credentialDTO.getUsername(), credentialDTO.getPassword());
        }
        catch (BusinessException e){
            return null;
        }



        NewCookie cookie = new NewCookie("username",credentialDTO.getUsername());

        return Response.ok(userDto).cookie(cookie).build();
    }

}
