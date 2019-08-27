package controller;

import configuration.Authentication;
import exceptions.BusinessException;
import io.jsonwebtoken.JwtBuilder;
import ro.msg.edu.jbugs.dto.CredentialDTO;
import ro.msg.edu.jbugs.dto.UserDTO;
import ro.msg.edu.jbugs.managers.interfaces.UserManagerRemote;

import javax.ejb.EJB;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.container.ContainerRequestContext;
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

//    @Context
//    private HttpServletRequest request;

//    @Context
//    SecurityContext securityContext;

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


//        Authentication authentication = Authentication.getAuthentication();
//        authentication.login(credentialDTO.getUsername());
//        ContainerRequestContext containerRequestContext
//        securityContext.
//        HttpSession session = request.getSession();
//        System.out.println("CREATIONTIME" + session.getCreationTime());
//        session.setAttribute("username",credentialDTO.getUsername());
//        System.out.println(session.getAttribute("username"));
        return Response.ok(userDto).cookie(cookie).build();
    }

}
