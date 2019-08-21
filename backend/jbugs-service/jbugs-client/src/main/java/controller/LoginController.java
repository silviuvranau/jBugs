package controller;

import ro.msg.edu.jbugs.dto.CredentialDTO;

import javax.ws.rs.POST;
import javax.ws.rs.Path;

/**
 * Document me.
 *
 * @author msg systems AG; User Name.
 * @since 19.1.2
 */
@Path("/login")
public class LoginController {

    @POST
    public void createCredential(CredentialDTO credentialDTO){
        System.out.println(" "+credentialDTO.getUsername()+" "+credentialDTO.getPassword());
    }

}
