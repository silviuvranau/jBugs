package ro.msg.edu.jbugs.managers.interfaces;

import ro.msg.edu.jbugs.dto.RoleDTO;

import javax.ejb.Remote;
import java.util.List;

/**
 * Document me.
 *
 * @author msg systems AG; User Name.
 * @since 19.1.2
 */
@Remote
public interface RoleManagerRemote {
    List<RoleDTO> findAllRoles();
}
