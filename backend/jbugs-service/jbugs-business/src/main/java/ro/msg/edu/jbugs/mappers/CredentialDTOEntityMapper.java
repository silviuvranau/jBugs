package ro.msg.edu.jbugs.mappers;

import entity.Credential;
import ro.msg.edu.jbugs.dto.CredentialDTO;

/**
 * Document me.
 *
 * @author msg systems AG; User Name.
 * @since 19.1.2
 */
public class CredentialDTOEntityMapper {
    private CredentialDTOEntityMapper(){}
    public static Credential getCredentialFromDto(CredentialDTO credentialDTO) {
        Credential credential = new Credential();

        credential.setUsername(credentialDTO.getUsername());
        credential.setPassword(credentialDTO.getPassword());

        return credential;
    }

    public static CredentialDTO getDtoFromCredential(Credential credential) {
        CredentialDTO credentialDTO = new CredentialDTO();

        credentialDTO.setUsername(credential.getUsername());
        credentialDTO.setPassword(credential.getPassword());


        return credentialDTO;
    }
}
