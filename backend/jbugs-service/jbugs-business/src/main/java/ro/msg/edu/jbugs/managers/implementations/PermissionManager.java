package ro.msg.edu.jbugs.managers.implementations;

import dao.PermissionDao;
import entity.Permission;
import ro.msg.edu.jbugs.dto.PermissionDTO;
import ro.msg.edu.jbugs.interceptors.Interceptor;
import ro.msg.edu.jbugs.managers.interfaces.PermissionManagerRemote;
import ro.msg.edu.jbugs.mappers.PermissionDTOEntityMapper;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.interceptor.Interceptors;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Document me.
 *
 * @author msg systems AG; Pricop Stefania.
 * @since 19.1.2
 */
@Stateless
@Interceptors(Interceptor.class)
public class PermissionManager implements PermissionManagerRemote {
    @EJB
    PermissionDao permissionDao;

    @Override
    public List<PermissionDTO> findAllPermissions() {
        List<Permission> permissions = permissionDao.findAllPermissions();

        return permissions.stream().map(PermissionDTOEntityMapper::getDtoFromPermission).collect(Collectors.toList());
    }
}
