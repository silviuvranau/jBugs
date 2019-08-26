package ro.msg.edu.jbugs.util;

import dao.RoleDao;
import dao.UserDao;
import entity.Permission;
import entity.Role;
import exceptions.BusinessException;
import ro.msg.edu.jbugs.dto.PermissionDTO;
import ro.msg.edu.jbugs.dto.UserDTO;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import java.util.HashSet;
import java.util.Set;

@Stateless
public class PermissionChecker {

    @EJB
    private UserDao userDao;

    /**
     * Check if the username has the required permission type
     * @param username
     * @param requiredPermissionType
     * @return
     */
    public boolean checkPermission(String username, String requiredPermissionType) throws BusinessException {
        Set<Role> roles;
        roles = userDao.findUserByUsername(username).getRoles();

        for(Role r : roles){
            for(Permission p : r.getPermissions()){
                if(p.getType().equals(requiredPermissionType))
                    return true;
            }
        }
        return false;
    }
}
