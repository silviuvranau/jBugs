package controller;

import exceptions.BusinessException;
import ro.msg.edu.jbugs.dto.CredentialDTO;
import ro.msg.edu.jbugs.dto.UserDTO;
import ro.msg.edu.jbugs.managers.interfaces.UserManagerRemote;

import javax.ejb.EJB;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;

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

    @Context
    private HttpServletRequest request;

    @POST
    public Response login(CredentialDTO credentialDTO) {
        UserDTO userDto;
        try {
            userDto = userManager.login(credentialDTO.getUsername(), credentialDTO.getPassword());
        } catch (BusinessException e) {
            return null;
        }
        HttpSession session = request.getSession();
        session.setAttribute("username", credentialDTO.getUsername());
        System.out.println(session.getAttribute("username"));
        return Response.ok(userDto).build();
    }

}
