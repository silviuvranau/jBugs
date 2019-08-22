package ro.msg.edu.jbugs.managers.interfaces;

import ro.msg.edu.jbugs.dto.RoleDTO;

import javax.ejb.Remote;
import java.util.List;

@Remote
public interface RoleManagerRemote {
    List<RoleDTO> findAllRoles();
}
