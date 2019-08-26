package controller;

import exceptions.BusinessException;
import ro.msg.edu.jbugs.dto.CredentialDTO;
import ro.msg.edu.jbugs.dto.UserDTO;
import ro.msg.edu.jbugs.managers.interfaces.UserManagerRemote;

import javax.ejb.EJB;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

/**
 * Document me.
 *
 * @author msg systems AG; User Name.
 * @since 19.1.2
 */
@Path("/login")
public class LoginController {

    @EJB
    UserManagerRemote userManager;

    @POST
    public void createCredential(CredentialDTO credentialDTO) {
        System.out.println(" " + credentialDTO.getUsername() + " " + credentialDTO.getPassword());
    }

    @POST
    public Response login(CredentialDTO credentialDTO) throws BusinessException {

        UserDTO userDto = userManager.login(credentialDTO.getUsername(), credentialDTO.getPassword());
        return Response.ok(userDto).build();
    }

}
